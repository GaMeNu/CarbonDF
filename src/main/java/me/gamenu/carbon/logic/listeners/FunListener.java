package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.args.VarTable;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.logic.exceptions.InvalidNameException;
import me.gamenu.carbon.logic.exceptions.UnknownEnumValueException;
import me.gamenu.carbon.parser.CarbonDFParser;

import static me.gamenu.carbon.logic.compile.TranspileUtils.*;

public class FunListener extends BaseCarbonListener {
    VarTable varTable;

    BlocksTable blocksTable;

    CodeBlock block;
    BlockType blockType;
    BlockType requestedBlocktype;
    ActionType actionType;

    public FunListener(ProgramContext programContext) {
        super(programContext);
        this.blocksTable = programContext.getCurrentDefTable();
        this.varTable = programContext.getVarTable();
    }

    public CodeBlock getBlock() {
        return block;
    }

    @Override
    public void enterFun_call(CarbonDFParser.Fun_callContext ctx) {
        super.enterFun_call(ctx);
        if (ctx.fun_call_chain() != null) enterFun_call_chain(ctx.fun_call_chain());
        enterSingle_fun_call(ctx.single_fun_call());
    }


    @Override
    public void enterFun_call_chain(CarbonDFParser.Fun_call_chainContext ctx) {
        super.enterFun_call_chain(ctx);
        String blockName = ctx.single_token_call().getText();
        BlockType requested = BlockType.fromCodeName(blockName);
        if (requested == null)
            throwError("Could not identify BlockType \"" + blockName + "\"", ctx, UnknownEnumValueException.class);
        requestedBlocktype = requested;
    }

    @Override
    public void enterSingle_fun_call(CarbonDFParser.Single_fun_callContext ctx) {
        super.enterSingle_fun_call(ctx);
        String actionName = ctx.SAFE_TEXT().getText();
        actionType = ActionType.fromCodeName(actionName);
        if (actionType != null)
            handleBasicAction(ctx);
        else if (programContext.getFunTable().get(actionName) != null)
            handleDefinitionCall(ctx, actionName);
        else
            throwError("Could not identify action/call \""+actionName+"\"", ctx, UnknownEnumValueException.class);

        if (ctx.call_params() != null)
            enterCall_params(ctx.call_params());

        blocksTable.add(block);

    }

    private void handleBasicAction(CarbonDFParser.Single_fun_callContext ctx) {
        if (requestedBlocktype != null && actionType.getBlock() != requestedBlocktype)
            throwError(String.format("Called action \"%s\" of block type \"%s\", but requested action is of block type \"%s\"", actionType.getCodeName(), actionType.getBlock().getCodeName(), requestedBlocktype.getCodeName()), ctx, InvalidNameException.class);

        blockType = actionType.getBlock();

        if (actionType == ActionType.SIMPLE_ASSIGN)
            throwError("It is generally recommended to avoid using SetVariable::assign(), as it bypasses strong typing.", ctx, CarbonTranspileException.class, CarbonTranspileException.Severity.WARN);

        block = new CodeBlock(blockType, actionType);
    }

    private void handleDefinitionCall(CarbonDFParser.Single_fun_callContext ctx, String callName) {
        switch (programContext.getFunTable().get(callName).getType()){
            case FUNC -> blockType = BlockType.CALL_FUNC;
            case PROCESS -> blockType = BlockType.START_PROCESS;
            default -> throwError("Invalid Block Type "+programContext.getFunTable().get(callName).getType()+" for call name \""+callName+"\" found in fun table.", ctx, CarbonTranspileException.class);
        }

        block = new DefinitionBlock(blockType, null, callName);

    }

    @Override
    public void enterCall_params(CarbonDFParser.Call_paramsContext ctx) {
        super.enterCall_params(ctx);

        ParamListener paramListener = new ParamListener(programContext);
        ArgsTable newArgsTable = new ArgsTable();

        // Create args table for function call
        for (CarbonDFParser.Call_paramContext paramCtx: ctx.call_param()){
            paramCtx.enterRule(paramListener);
            newArgsTable.addAtFirstNull(paramListener.getCodeArg());
        }
        block.getArgs().extend(newArgsTable);
        performValidation(ctx);
    }

    private void performValidation(CarbonDFParser.Call_paramsContext ctx){
        if (block.getBlockType() == BlockType.CALL_FUNC || blockType == BlockType.START_PROCESS) {
            String name = ((DefinitionBlock) block).getName();
            ArgsTable paramTable = programContext.getFunTable().get(name).getParams();
            if (!block.getArgs().matchParams(paramTable))
                throwError("Param types do not match.", ctx, CarbonTranspileException.class);
        }
    }

    @Override
    public void enterVar_define(CarbonDFParser.Var_defineContext ctx) {
        super.enterVar_define(ctx);
        VarScope scope = VarScope.fromCodeName(ctx.var_scope().getText());
        String varName;
        ArgType type;

        for (CarbonDFParser.Var_define_unitContext defCtx : ctx.var_define_unit()){
            varName = defCtx.var_name().getText();

            if (varTable.varExists(varName)) throwError("Var \"" + varName + "\" was redfined", ctx, CarbonTranspileException.class);

            // Set the matching varType
            if (defCtx.type_annotations() != null) type = annotationToArgType(defCtx.type_annotations());
            else type = ArgType.ANY;

            varTable.putVar(varName, scope, type);


            // TODO: Change from simple assign to support functions, and later operators;
            if (defCtx.var_value() != null) {
                if (defCtx.var_value().standalone_item() != null){
                    CodeArg arg2 = itemToCodeArg(defCtx.var_value().standalone_item());
                    if (arg2.getType() != type && type != ArgType.ANY) throwError("Assigned type \"" + arg2.getType() + "\" does not match defined type \"" + varTable.getVarType(varName) + "\" for variable name \"" + varName + "\"", ctx, CarbonTranspileException.class);
                    ArgsTable resTable = new ArgsTable()
                            .addAtFirstNull(new VarArg(varName, scope))
                            .addAtFirstNull(arg2);
                    blocksTable.add(new CodeBlock(BlockType.SET_VARIABLE, ActionType.SIMPLE_ASSIGN).setArgs(resTable));
                }
            }
        }
    }

    private CodeArg itemToCodeArg(CarbonDFParser.Standalone_itemContext itemCtx) {
        ParamListener paramListener = new ParamListener(programContext);
        paramListener.enterStandalone_item(itemCtx);
        return paramListener.getCodeArg();
    }

    @Override
    public void enterVar_assign(CarbonDFParser.Var_assignContext ctx) {
        super.enterVar_assign(ctx);
        String varName;
        ArgType type;
        for (CarbonDFParser.Var_assign_unitContext auCtx : ctx.var_assign_unit()){
            varName = auCtx.var_name().getText();
            if (!varTable.varExists(varName)) throwError("Could not identify variable \"" +auCtx.var_name().getText()+ "\". Is it defined?", ctx, InvalidNameException.class);
            if (auCtx.var_value().standalone_item() != null) {
                type = standaloneToArgType(auCtx.var_value().standalone_item());
                if (type != varTable.getVarType(varName) && varTable.getVarType(varName) != ArgType.ANY) throwError("Assigned type \"" + type + "\" does not match defined type \"" + varTable.getVarType(varName) + "\" for variable name \"" + varName + "\"", ctx, CarbonTranspileException.class);
                CodeArg arg2 = itemToCodeArg(auCtx.var_value().standalone_item());
                ArgsTable resTable = new ArgsTable()
                        .addAtFirstNull(new VarArg(varName, varTable.getVarScope(varName)))
                        .addAtFirstNull(arg2);
                blocksTable.add(new CodeBlock(BlockType.SET_VARIABLE, ActionType.SIMPLE_ASSIGN).setArgs(resTable));
            }
            // TODO: Add check function types


        }
    }

    @Override
    public void enterVar_assign_unit(CarbonDFParser.Var_assign_unitContext ctx) {
        super.enterVar_assign_unit(ctx);

    }

    // TODO: function arguments - vars
    // TODO: var_assign
    // TODO: var_define
}
