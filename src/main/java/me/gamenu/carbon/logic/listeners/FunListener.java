package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.args.VarTable;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.logic.exceptions.InvalidNameException;
import me.gamenu.carbon.logic.exceptions.UnknownEnumValueException;
import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

import static me.gamenu.carbon.logic.compile.TranspileUtils.*;

public class FunListener extends BaseCarbonListener {
    VarTable varTable;

    ArgsTable funReturnVars;

    BlocksTable blocksTable;

    CodeBlock block;
    BlockType blockType;
    BlockType requestedBlocktype;
    ActionType actionType;

    public FunListener(ProgramContext programContext) {
        super(programContext);
        this.blocksTable = programContext.getCurrentDefTable();
        this.varTable = programContext.getVarTable();
        this.funReturnVars = new ArgsTable();
    }

    public CodeBlock getBlock() {
        return block;
    }

    @Override
    public void enterFun_call(CarbonDFParser.Fun_callContext ctx) {
        super.enterFun_call(ctx);
        if (ctx.fun_call_chain() != null) enterFun_call_chain(ctx.fun_call_chain());
        enterSingle_fun_call(ctx.single_fun_call());
        blocksTable.add(block);
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
    }

    private void handleBasicAction(CarbonDFParser.Single_fun_callContext ctx) {
        if (requestedBlocktype != null && actionType.getBlock() != requestedBlocktype)
            throwError(String.format("Called action \"%s\" of block type \"%s\", but requested action is of block type \"%s\"", actionType.getCodeName(), actionType.getBlock().getCodeName(), requestedBlocktype.getCodeName()), ctx, InvalidNameException.class);

        blockType = actionType.getBlock();

        if (blockType == BlockType.SET_VARIABLE && funReturnVars.getArgDataList().isEmpty())
            throwError("It is generally recommended to avoid using SetVariable blocks as normal calls, as it bypasses strong typing and is untested behavior", ctx, CarbonTranspileException.class, CarbonTranspileException.Severity.WARN);

        if (ActionType.getBaseFunsReturns().containsKey(actionType.getCodeName()) && funReturnVars.getArgDataList().isEmpty())
            throwError("Function returns " + ActionType.getBaseFunsReturns().get(actionType.getCodeName()).getArgDataList().size() + " value(s) but no variables were given (use the '_' variable to ignore the result)", ctx, CarbonTranspileException.class);

        block = new CodeBlock(blockType, actionType);
    }

    private void handleDefinitionCall(CarbonDFParser.Single_fun_callContext ctx, String callName) {
        FunTable.FunType callType = programContext.getFunTable().get(callName);
        switch (callType.getType()){
            case FUNC -> blockType = BlockType.CALL_FUNC;
            case PROCESS -> blockType = BlockType.START_PROCESS;
            default -> throwError("Invalid Block Type "+callType.getType()+" for call name \""+callName+"\" found in fun table.", ctx, CarbonTranspileException.class);
        }
        if (callType.getReturns().getArgDataList().size() != funReturnVars.getArgDataList().size())
            throwError("Function returns " + callType.getReturns().getArgDataList().size() + " value(s) but " + funReturnVars.getArgDataList().size() + " variables were given (use the '_' variable to ignore the result)", ctx, CarbonTranspileException.class);

        block = new DefinitionBlock(blockType, null, callName);

        FunTable.FunType funType = programContext.getFunTable().get(callName);

        if (!funType.getParams().getArgDataList().isEmpty() && ctx.call_params() == null) {
            throwError("Recieved incorrect amount of parameters (expected " + funType.getParams().getArgDataList().size() + ", recieved 0)", ctx, CarbonTranspileException.class);
        }

    }

    @Override
    public void enterCall_params(CarbonDFParser.Call_paramsContext ctx) {
        super.enterCall_params(ctx);


        if (blockType == BlockType.CALL_FUNC){
            FunTable.FunType funType = programContext.getFunTable().get(((DefinitionBlock)block).getName());

            if (funType.getParams().getArgDataList().size() != ctx.call_param().size()) {
                throwError("Recieved incorrect amount of parameters (expected " + funType.getParams().getArgDataList().size() + ", recieved " + ctx.call_param().size() + ")", ctx, CarbonTranspileException.class);
            }
        }



        if (ctx == null) return;

        ParamListener paramListener = new ParamListener(programContext);
        ArgsTable newArgsTable = new ArgsTable();

        // Create args table for function call
        for (CarbonDFParser.Call_paramContext paramCtx: ctx.call_param()){
            paramCtx.enterRule(paramListener);
            newArgsTable.addAtFirstNull(paramListener.getCodeArg());
        }

        // Special case for Returns in functions
        if (programContext.getCurrentFun() != null && actionType == ActionType.RETURN){
            returnSpecialCase(ctx, newArgsTable);
        } else {
            block.getArgs().extend(newArgsTable);
        }
        performTypeValidation(ctx);
        performValueValidation(ctx);

        if (!funReturnVars.getArgDataList().isEmpty())
            block.getArgs().insertExtend(funReturnVars);
    }

    private void returnSpecialCase(CarbonDFParser.Call_paramsContext ctx, ArgsTable newArgsTable) {
        FunTable.FunType currentFun = programContext.getCurrentFun();

        // Check if the size is 0
        if (!newArgsTable.matchSizes(currentFun.getReturns()) && ctx.call_param() != null){
            throwError("Return base function can only contain either no arguments or the number of return values", ctx, CarbonTranspileException.class);
            return;
        }

        if (newArgsTable.getArgDataList().isEmpty()) {
            return;
        }

        int match = newArgsTable.matchParams(currentFun.getReturns());
        if (match >= 0) {
            FunctionParam param = ((FunctionParam) currentFun.getReturns().get(match));
            ArgType paramType = param.getParamType();
            if (paramType == ArgType.VAR)
                paramType = ((VarArg)param.getInternalArg()).getVarType();

            if (newArgsTable.get(match).getType() == ArgType.VAR)
                throwError(String.format("Variable type %s does not match param type %s", ((VarArg) newArgsTable.get(match)).getVarType(), paramType),
                        ctx.call_param(match), CarbonTranspileException.class);
            else
                throwError(String.format("Value type %s does not match param type %s", (newArgsTable.get(match)).getType(), paramType),
                        ctx.call_param(match), CarbonTranspileException.class);
        }

        for (int i = 0; i < newArgsTable.getArgDataList().size(); i++) {
            FunctionParam retParam = (FunctionParam) currentFun.getReturns().get(i);
            CodeArg resArg = newArgsTable.getArgDataList().get(i);

            VarArg retVar = (VarArg) retParam.getInternalArg();

            blocksTable.add(new CodeBlock(BlockType.SET_VARIABLE, ActionType.SIMPLE_ASSIGN).setArgs(
                    new ArgsTable()
                            .addAtFirstNull(retVar)
                            .addAtFirstNull(resArg)
            ));
        }
    }

    private void performTypeValidation(CarbonDFParser.Call_paramsContext ctx){
        if (block.getBlockType() == BlockType.CALL_FUNC || blockType == BlockType.START_PROCESS) {
            String name = ((DefinitionBlock) block).getName();
            ArgsTable paramTable = programContext.getFunTable().get(name).getParams();

            if (!block.getArgs().matchSizes(paramTable)) return;

            int match = block.getArgs().matchParams(paramTable);

            if (match >= 0)
                if (block.getArgs().get(match).getType() == ArgType.VAR)
                    throwError(String.format("Variable type %s does not match param type %s", ((VarArg) block.getArgs().get(match)).getVarType(), ((FunctionParam) paramTable.get(match)).getParamType()),
                            ctx.call_param(match), CarbonTranspileException.class);
                else
                    throwError(String.format("Value type %s does not match param type %s", (block.getArgs().get(match)).getType(), ((FunctionParam) paramTable.get(match)).getParamType()),
                            ctx.call_param(match), CarbonTranspileException.class);
        }
    }

    private void performValueValidation(CarbonDFParser.Call_paramsContext ctx){
        for (int i = 0; i < block.getArgs().getArgDataList().size(); i++){
            CodeArg arg = block.getArgs().get(i);

            if (arg.getType() != ArgType.VAR)
                return;

            VarArg varArg = varTable.get(((VarArg)arg).getName());

            if (arg.getType() == ArgType.VAR && varArg.getScope() == VarScope.LINE && varArg.getValue() == null)
                throwError("Variable was never assigned a value", ctx.call_param(i), CarbonTranspileException.class, CarbonTranspileException.Severity.WARN);
        }
    }

    @Override
    public void enterVar_define(CarbonDFParser.Var_defineContext ctx) {
        super.enterVar_define(ctx);
        VarScope scope = VarScope.fromCodeName(ctx.var_scope().getText());
        String varName;
        ArgType type = null;
        boolean isDynamic;

        ArrayList<CarbonDFParser.Var_nameContext> nameList = new ArrayList<>();

        // Prepare name list, do some checks
        for (CarbonDFParser.Var_define_nameContext nameCtx : ctx.var_define_name()){
            varName = nameCtx.var_name().getText();

            if (varTable.varExists(varName)) throwError("Variable \"" + varName + "\" was redefined", ctx, CarbonTranspileException.class);

            if (nameCtx.type_annotations() != null) {
                isDynamic = false;
                type = annotationToArgType(nameCtx.type_annotations());
            } else {
                isDynamic = true;
                type = ArgType.ANY;
            }

            VarArg resVar = new VarArg(varName, scope, isDynamic, type);
            varTable.putVar(resVar);

            nameList.add(nameCtx.var_name());
        }

        if (!ctx.var_value().isEmpty())
            assignVars(ctx, nameList, ctx.var_value());

    }

    private CodeArg standaloneToCodeArg(CarbonDFParser.Standalone_itemContext itemCtx) {
        ParamListener paramListener = new ParamListener(programContext);
        paramListener.enterStandalone_item(itemCtx);
        return paramListener.getCodeArg();
    }

    @Override
    public void enterVar_assign(CarbonDFParser.Var_assignContext ctx) {
        super.enterVar_assign(ctx);

        assignVars(ctx, ctx.var_name(), ctx.var_value());

    }

    private void assignVars(ParserRuleContext ctx, List<CarbonDFParser.Var_nameContext> varNames, List<CarbonDFParser.Var_valueContext> varValues){

        if (varValues.size() == 1 && varValues.get(0).fun_call() != null) {
            assignFunCall(ctx, varNames, varValues.get(0).fun_call());
            return;
        }

        if (varNames.size() > varValues.size()) throwError("Not enough values to assign (expected "+varNames.size()+", recieved "+varValues.size()+")", ctx, CarbonTranspileException.class);
        if (varNames.size() < varValues.size()) throwError("Too many values to assign (expected "+varNames.size()+")", ctx, CarbonTranspileException.class);

        for (int i = 0; i < varNames.size(); i++){
            CarbonDFParser.Var_nameContext nameCtx = varNames.get(i);
            CarbonDFParser.Var_valueContext valCtx = varValues.get(i);

            String varName = nameCtx.getText();

            if (!varTable.varExists(varName))
                throwError("Could not identify variable \"" + varName + "\", are you sure it is defined?", ctx, InvalidNameException.class);

            if (valCtx.standalone_item() != null){
                assignStandalone(ctx, nameCtx, valCtx.standalone_item());
            } else if (valCtx.var_name() != null) {
                assignVarName(ctx, nameCtx, valCtx.var_name());
            } else if (valCtx.fun_call() != null) {
                throwError("You may only set the return values of one function call at a time", valCtx, CarbonTranspileException.class);
            } else {
                throwError("How did we get here...? (+10 CDF points for breaking the transpiler)", ctx, CarbonTranspileException.class);
            }
        }
    }

    private void assignStandalone(ParserRuleContext ctx, CarbonDFParser.Var_nameContext varCtx, CarbonDFParser.Standalone_itemContext valCtx){
        String varName = varCtx.getText();

        CodeArg valArg = standaloneToCodeArg(valCtx);

        VarArg varArg = varTable.get(varName);

        if (varTable.getVarType(varName) != valArg.getType() &&
                varTable.getVarType(varName) != ArgType.ANY &&
                !varTable.get(varCtx.getText()).isDynamic())
            throwError("Assigned type \"" + valArg.getType() + "\" does not match defined type \"" + varArg.getVarType() + "\" for statically-typed variable \"" + varArg.getName() + "\"", ctx, CarbonTranspileException.class);

        if (varArg.isDynamic()) {
            varArg.setVarType(valArg.getType());
        }

        varArg.setValue(valArg);
        varTable.putVar(varTable.get(varArg.getName()).setValue(valArg));

        ArgsTable resTable = new ArgsTable()
                .addAtFirstNull(varArg)
                .addAtFirstNull(valArg);
        blocksTable.add(new CodeBlock(BlockType.SET_VARIABLE, ActionType.SIMPLE_ASSIGN).setArgs(resTable));
    }

    private void assignVarName(ParserRuleContext ctx, CarbonDFParser.Var_nameContext varCtx, CarbonDFParser.Var_nameContext valCtx){
        String varName = varCtx.getText();
        String valName = valCtx.getText();

        VarArg var = varTable.get(varName);
        VarArg val = varTable.get(valName);

        if (val == null) {
            throwError("Could not identify \"" + valName + "\", are you sure it is defined?", ctx, InvalidNameException.class);
            return;
        }

        if (var.getVarType() != val.getVarType()
                && var.getVarType() != ArgType.ANY
                && !var.isDynamic())
            throwError("Assigned type \"" + val.getType() + "\" does not match defined type \"" + var.getVarType() + "\" for statically-typed variable \"" + var.getName() + "\"", ctx, CarbonTranspileException.class);

        if (var.isDynamic())
            var.setVarType(val.getVarType());

        var.setValue(val.getValue());

        varTable.putVar(varTable.get(var.getName()).setValue(val.getValue()));

        ArgsTable resTable = new ArgsTable()
                .addAtFirstNull(var)
                .addAtFirstNull(val);
        blocksTable.add(new CodeBlock(BlockType.SET_VARIABLE, ActionType.SIMPLE_ASSIGN).setArgs(resTable));
    }

    private void assignFunCall(ParserRuleContext ctx, List<CarbonDFParser.Var_nameContext> nameCtxLs, CarbonDFParser.Fun_callContext funCtx) {
        String funName = funCtx.single_fun_call().SAFE_TEXT().getText();

        ArgsTable args;

        // Get return args
        if (programContext.getFunTable().get(funName) != null){
            args = programContext.getFunTable().get(funName).getReturns();
        } else if (ActionType.getBaseFunsReturns().get(funName) != null) {
            args = ActionType.getBaseFunsReturns().get(funName);
        } else {
            if (ActionType.fromCodeName(funName) != null)
                throwError("Function " + funName + " does not return anything", ctx, CarbonTranspileException.class);
            else
                throwError("Could not identify function \"" + funName + "\", are you sure it is defined?", ctx, InvalidNameException.class);
            return;
        }

        if (nameCtxLs.size() != args.getArgDataList().size()) throwError(String.format("Amount of variables (%s) does not match function %s amount of return values (%s)", nameCtxLs.size(), funName, args.getArgDataList().size()), ctx, CarbonTranspileException.class);


        ArgsTable resTable = new ArgsTable();

        String varName;
        for (int i = 0; i < nameCtxLs.size(); i++){
            CarbonDFParser.Var_nameContext nameCtx = nameCtxLs.get(i);
            varName = nameCtx.getText();

            if (!varTable.varExists(varName)) {
                throwError("Could not identify variable \"" + varName + "\", are you sure it is defined?", ctx, InvalidNameException.class);
            }

            VarArg var = varTable.get(varName);
            ArgType retType = args.get(i).getType();

            if (var.isDynamic()) {
                var.setVarType(retType);
            }

            var.setValue(new CodeArg(retType));

            if (!var.isDynamic()
                    && var.getVarType() != ArgType.ANY
                    && var.getVarType() != retType)
                throwError(String.format("Assigned type %s does not match defined type %s for statically-typed variable %s", retType, var.getVarType(), var.getName()), nameCtx, CarbonTranspileException.class);

            resTable.addAtFirstNull(varTable.get(varName));

        }

        funReturnVars = resTable;

        enterFun_call(funCtx);

        for (int i = 0; i < nameCtxLs.size(); i++){
            CarbonDFParser.Var_nameContext nameCtx = nameCtxLs.get(i);
            varName = nameCtx.getText();

            varTable.get(varName).setValue(args.get(i));
        }

        funReturnVars = new ArgsTable();
    }
}
