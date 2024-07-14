package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.args.VarTable;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.logic.exceptions.InvalidNameException;
import me.gamenu.carbon.logic.exceptions.TypeException;
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
        if (!funReturnVars.getArgDataList().isEmpty())
            block.getArgs().insertExtend(funReturnVars);
        if (ctx.target() != null){
            if (blockType != BlockType.PLAYER_ACTION
                    && blockType != BlockType.ENTITY_ACTION
                    && blockType != BlockType.IF_PLAYER
                    && blockType != BlockType.IF_ENTITY)
                throwError("Block target unsupported for block type.", ctx.single_fun_call(), CarbonTranspileException.class);

            if (ctx.target().target_any() == null) {

                if (ctx.target().target_player() != null && (blockType != BlockType.PLAYER_ACTION && blockType != BlockType.IF_PLAYER))
                    throwError("Block target unsupported for block type.", ctx.single_fun_call(), CarbonTranspileException.class);

                if (ctx.target().target_entity() != null && (blockType != BlockType.ENTITY_ACTION && blockType != BlockType.IF_ENTITY))
                    throwError("Block target unsupported for block type.", ctx.single_fun_call(), CarbonTranspileException.class);
            }

            block.setTargetType(targetToType(ctx.target()));
            System.out.println("TARGET TYPE: "+block.getTargetType());

        }
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
        ArgType type;
        boolean isDynamic;
        boolean isConstant = ctx.CONSTANT_KEYWORD() != null;

        ArrayList<CarbonDFParser.Var_nameContext> nameList = new ArrayList<>();

        // Prepare name list, do some checks
        for (CarbonDFParser.Var_define_nameContext nameCtx : ctx.var_define_name()){
            varName = nameCtx.var_name().getText();

            if (varTable.varExists(varName)) throwError("Variable \"" + varName + "\" was redefined", nameCtx, CarbonTranspileException.class);

            if (nameCtx.type_annotations() != null) {
                isDynamic = false;
                type = annotationToArgType(nameCtx.type_annotations());
            } else {
                isDynamic = true;
                type = ArgType.ANY;
            }

            VarArg resVar = new VarArg(varName, scope, isDynamic, new CodeArg(type)).setConstant(isConstant);
            varTable.putVar(resVar);

            nameList.add(nameCtx.var_name());
        }

        if (!ctx.var_value().isEmpty())
            assignVars(ctx, nameList, ctx.var_value());
        else if (isConstant)
            throwError("Final variable(s) were declared without a value, and cannot be re-assigned.", ctx, CarbonTranspileException.class, CarbonTranspileException.Severity.WARN);

    }

    private CodeArg standaloneToCodeArg(CarbonDFParser.Standalone_itemContext itemCtx) {
        ParamListener paramListener = new ParamListener(programContext);
        paramListener.enterStandalone_item(itemCtx);
        return paramListener.getCodeArg();
    }

    @Override
    public void enterVar_assign(CarbonDFParser.Var_assignContext ctx) {
        super.enterVar_assign(ctx);

        for (CarbonDFParser.Var_nameContext vnCtx : ctx.var_name()){
            String varName = vnCtx.getText();
            if (varTable.get(varName).isConstant()){
                throwError("Constant variable cannot be re-assigned after declaration.", ctx, CarbonTranspileException.class);
            }
        }
        assignVars(ctx, ctx.var_name(), ctx.var_value());

    }

    private static boolean matchVarType(VarArg var, CodeArg val){
        ArgType varType = var.getVarType();
        if (val instanceof VarArg) val = ((VarArg) val).getValue();
        if (var.isDynamic()) return true;
        if (varType == ArgType.ANY) return true;
        if (varType == val.getType()) return true;
        if (val instanceof GameValue){
            return varType == ((GameValue) val).getGvType().getReturnArg().getType();
        }
        return false;
    }

    private void assignVars(ParserRuleContext ctx, List<CarbonDFParser.Var_nameContext> varNames, List<CarbonDFParser.Var_valueContext> varValues){

        if (varValues.size() == 1 && varValues.get(0).fun_call() != null) {
            assignFunCall(ctx, varNames, varValues.get(0).fun_call());
            return;
        }

        if (varNames.size() > varValues.size()) {
            throwError("Not enough values to assign (expected "+varNames.size()+", received "+varValues.size()+")", ctx, CarbonTranspileException.class);
            return;
        }
        if (varNames.size() < varValues.size()) {
            throwError("Too many values to assign (expected "+varNames.size()+", received " + varValues.size() +")", ctx, CarbonTranspileException.class);
            return;
        }

        for (int i = 0; i < varNames.size(); i++){
            CarbonDFParser.Var_nameContext nameCtx = varNames.get(i);
            CarbonDFParser.Var_valueContext valCtx = varValues.get(i);

            String varName = nameCtx.getText();

            if (!varTable.varExists(varName))
                throwError("Could not identify variable \"" + varName + "\", are you sure it is defined?", ctx, InvalidNameException.class);
            System.out.println(valCtx.getText());
            if (valCtx.var_name() != null) {
                assignVarName(ctx, nameCtx, valCtx.var_name());
            } else if (valCtx.any_item().complex_item() != null) {
                assignComplex(ctx, nameCtx, valCtx.any_item().complex_item());
            } else if (valCtx.any_item().standalone_item() != null) {
                assignStandalone(ctx, nameCtx, valCtx.any_item().standalone_item());
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

        if (!matchVarType(varTable.get(varCtx.getText()), valArg))
            throwError("Assigned type \"" + valArg.getType() + "\" does not match defined type \"" + varArg.getVarType() + "\" for statically-typed variable \"" + varArg.getName() + "\"", ctx, TypeException.class);

        varArg.setValue(valArg);
        varTable.putVar(varTable.get(varArg.getName()).setValue(valArg));

        ArgsTable resTable = new ArgsTable()
                .addAtFirstNull(varArg)
                .addAtFirstNull(valArg);
        blocksTable.add(new CodeBlock(BlockType.SET_VARIABLE, ActionType.SIMPLE_ASSIGN).setArgs(resTable));
    }


    private void assignComplex(ParserRuleContext ctx, CarbonDFParser.Var_nameContext varCtx, CarbonDFParser.Complex_itemContext complexCtx) {
        String varName = varCtx.getText();
        newComplex(ctx, varName, 0, complexCtx);
    }

    private void newComplex(ParserRuleContext ctx, String varName, int depth, CarbonDFParser.Complex_itemContext itemCtx){
        depth += 1;

        // Put new var for generation
        if (depth > 1){
            if (varTable.varExists(varName)) {
                throwError("Used reserved value for complex item generation (+5 CDF points for figuring it out)", itemCtx, CarbonTranspileException.class);
                return;
            }
            varTable.putVar(new VarArg(varName, VarScope.LINE, true));
        }

        if (itemCtx.list() != null){
            varTable.get(varName).setValue(new CodeArg(ArgType.LIST));
            newList(ctx, varName, depth, itemCtx.list());
        } else if (itemCtx.dict() != null) {
            varTable.get(varName).setValue(new CodeArg(ArgType.DICT));
            newDict(ctx, varName, depth, itemCtx.dict());
        } else {
            throwError("How did we get here...? (+10 CDF points for breaking the transpiler)", ctx, CarbonTranspileException.class);
            return;
        }

        // Remove var
        if (depth > 1){
            varTable.remove(varName);
        }
    }

    private void newList(ParserRuleContext ctx, String varName, int depth, CarbonDFParser.ListContext listCtx){
        // Create the new ArgsTable and add the first var
        ArgsTable resArgs = new ArgsTable();
        resArgs.addAtFirstNull(varTable.get(varName));

        for (int i = 0; i < listCtx.any_item().size(); i++) {
            CarbonDFParser.Any_itemContext curItem = listCtx.any_item(i);
            if (curItem.standalone_item() != null){
                resArgs.addAtFirstNull(standaloneToCodeArg(curItem.standalone_item()));
            } else if (curItem.var_name() != null) {
                String newVar = curItem.var_name().getText();
                if (!varTable.varExists(newVar)) {
                    throwError("Could not identify variable \"" + newVar + "\", are you sure it is defined?", curItem, InvalidNameException.class);
                    return;
                }
                if (varTable.get(newVar).getValue() == null)
                    throwError("Variable was never assigned a value", curItem, CarbonTranspileException.class, CarbonTranspileException.Severity.WARN);
                resArgs.addAtFirstNull(varTable.get(curItem.var_name().getText()));
            } else if (curItem.complex_item() != null){
                String newName = varName + "@" + i;
                newComplex(ctx, newName, depth, curItem.complex_item());
                resArgs.addAtFirstNull(new VarArg(newName, VarScope.LINE, true));
            }
        }

        CodeBlock newBlock = new CodeBlock(BlockType.SET_VARIABLE, ActionType.CREATE_LIST)
                .setArgs(resArgs);

        blocksTable.add(newBlock);

    }

    private void newDict(ParserRuleContext ctx, String varName, int depth, CarbonDFParser.DictContext dictCtx){

        ArrayList<String> registeredKeys = new ArrayList<>();

        String nameK = varName + ".k";
        String nameV = varName + ".v";

        varTable.putVar(new VarArg(nameK, VarScope.LINE, false).setValue(new CodeArg(ArgType.LIST)));
        varTable.putVar(new VarArg(nameV, VarScope.LINE, false).setValue(new CodeArg(ArgType.LIST)));

        ArgsTable resKeys = new ArgsTable()
                .addAtFirstNull(varTable.get(nameK));
        ArgsTable resVals = new ArgsTable()
                .addAtFirstNull(varTable.get(nameV));

        for (int i = 0; i < dictCtx.dict_pair().size(); i++) {
            CarbonDFParser.Dict_pairContext curPair = dictCtx.dict_pair(i);
            CarbonDFParser.Simple_stringContext keyCtx = curPair.key().simple_string();
            CarbonDFParser.Any_itemContext itemCtx = curPair.any_item();
            String keyStr = keyCtx.getText().substring(1, keyCtx.getText().length()-1);

            // Check if key exists
            if (registeredKeys.contains(keyStr)) {
                throwError("Dictionaries cannot contain the same key twice when defined", keyCtx, CarbonTranspileException.class);
                return;
            }

            // Add key to keys lists
            registeredKeys.add(keyStr);
            resKeys.addAtFirstNull(new CodeArg(ArgType.STRING).setArgName(keyStr));

            if (itemCtx.standalone_item() != null){
                resVals.addAtFirstNull(standaloneToCodeArg(itemCtx.standalone_item()));
            } else if (itemCtx.var_name() != null) {
                String newVar = itemCtx.var_name().getText();
                if (!varTable.varExists(newVar)){
                    throwError("Could not identify variable \"" + newVar + "\", are you sure it is defined?", itemCtx, InvalidNameException.class);
                    return;
                }

                if (varTable.get(newVar).getValue() == null){
                    throwError("Variable was never assigned a value", itemCtx, CarbonTranspileException.class, CarbonTranspileException.Severity.WARN);
                }

                resVals.addAtFirstNull(varTable.get(newVar));
            } else if (itemCtx.complex_item() != null){
                String newName = varName + "@" + keyStr;
                newComplex(ctx, newName, depth, itemCtx.complex_item());
                resVals.addAtFirstNull(new VarArg(newName, VarScope.LINE, true));
            }

        }

        ArgsTable resArgs = new ArgsTable()
                .addAtFirstNull(varTable.get(varName));

        // Space-saving measure, no need to create the list blocks if the dict is empty anyway :shrug:
        if (!dictCtx.dict_pair().isEmpty()){
            CodeBlock keysBlock = new CodeBlock(BlockType.SET_VARIABLE, ActionType.CREATE_LIST)
                    .setArgs(resKeys);

            CodeBlock valsBlock = new CodeBlock(BlockType.SET_VARIABLE, ActionType.CREATE_LIST)
                    .setArgs(resVals);

            blocksTable
                    .add(keysBlock)
                    .add(valsBlock);

            resArgs
                    .addAtFirstNull(varTable.get(nameK))
                    .addAtFirstNull(varTable.get(nameV));

        }

        CodeBlock dictBlock = new CodeBlock(BlockType.SET_VARIABLE, ActionType.CREATE_DICT)
                .setArgs(resArgs);


        blocksTable.add(dictBlock);

        varTable.remove(nameK);
        varTable.remove(nameV);
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

        if (!matchVarType(var, val))
            throwError("Assigned type \"" + val.getType() + "\" does not match defined type \"" + var.getVarType() + "\" for statically-typed variable \"" + var.getName() + "\"", ctx, TypeException.class);

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

            System.out.println(varName);

            if (!varTable.varExists(varName)) {
                throwError("Could not identify variable \"" + varName + "\", are you sure it is defined?", ctx, InvalidNameException.class);
                return;
            }

            VarArg var = varTable.get(varName);
            ArgType retType = args.get(i).getType();

            // Base functions and defined functions have a different standard.
            // This is the very bad patch.
            // DO NOT TOUCH UNLESS THIS BREAKS.
            if (retType == ArgType.PARAM){
                retType = ((VarArg)((FunctionParam) args.get(i)).getInternalArg()).getVarType();
            }

            if (!var.isDynamic()
                    && var.getVarType() != ArgType.ANY
                    && var.getVarType() != retType)
                throwError(String.format("Assigned type %s does not match defined type %s for statically-typed variable %s", retType, var.getVarType(), var.getName()), nameCtx, TypeException.class);

            var.setValue(new CodeArg(retType));

            System.out.println(varTable.get(varName));
            resTable.addAtFirstNull(varTable.get(varName));
            System.out.println(resTable.getArgDataList());

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
