package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.etc.TargetType;
import me.gamenu.carbon.logic.exceptions.*;
import me.gamenu.carbon.parser.CarbonDFParser.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;

import static me.gamenu.carbon.parser.CarbonDFLexer.*;

public class TranslateBlock {
    final VarTable table;
    final HashMap<String, BlockType> funTable;

    public TranslateBlock(VarTable table, HashMap<String, BlockType> funTable){
        this.table = table;
        this.funTable = funTable;
    }

    public BlocksTable translateBlock(BlockContext blockContext) throws BaseCarbonException {
        BlocksTable codeBlockList = new BlocksTable();
        for (Single_lineContext lineContext: blockContext.single_line()) {
            // We SKIP Empty lines and comments
            if (lineContext.isEmpty() || lineContext.comment() != null) continue;

            if (lineContext.simple_statement() != null) codeBlockList.extend(parseSimpleStatement(lineContext.simple_statement()));
        }

        return codeBlockList;
    }

    public BlocksTable parseSimpleStatement(Simple_statementContext ctx) throws BaseCarbonException {
        if (ctx.fun_call() != null) {
            BlocksTable retList = new BlocksTable();
            retList.add(parseFunCall(ctx.fun_call()));
            return retList;
        }
        if (ctx.var_define() != null) return parseVarDefine(ctx.var_define());
        if (ctx.var_assign() != null) return parseVarAssign(ctx.var_assign());
        return null;
    }

    private BlocksTable parseVarDefine(Var_defineContext varDefineContext) throws BaseCarbonException{
        VarScope scope;
        String varName;
        ArgType type;
        VarTable table = new VarTable();

        switch (((TerminalNode) varDefineContext.var_scope().getChild(0)).getSymbol().getType()) {
            case SCOPE_GLOBAL -> scope = VarScope.GLOBAL;
            case SCOPE_SAVED -> scope = VarScope.SAVED;
            case SCOPE_LOCAL -> scope = VarScope.LOCAL;
            case SCOPE_LINE -> scope = VarScope.LINE;
            default -> scope = VarScope.GLOBAL;
        }

        for (Var_assign_unitContext assignUnitCtx :
                varDefineContext.var_assign().var_assign_unit()) {
            varName = assignUnitCtx.var_name().getText();
            if (assignUnitCtx.type_annotations() == null) type = ArgType.ANY;
            else type = TranspileUtils.annotationToArgType(assignUnitCtx.type_annotations());

            table.putVar(varName, scope, type);
        }

        this.table.extend(table);

        return parseVarAssign(varDefineContext.var_assign());
    }

    private ArgType getItemType(Standalone_itemContext ctx){
        if (ctx.simple_item() != null){
            if (ctx.simple_item().NUMBER() != null) return ArgType.NUM;
            else if (ctx.simple_item().string() != null) return ArgType.STRING;
            else if (ctx.simple_item().styled_text() != null) return ArgType.STYLED_TEXT;
        }
        return null;
    }

    private static String trimStringContexts(String string){
        return string.substring(1, string.length() - 1);
    }

    private BlocksTable parseVarAssign(Var_assignContext ctx) throws BaseCarbonException{

        BlocksTable bt = new BlocksTable();
        for (Var_assign_unitContext unitCtx : ctx.var_assign_unit()){
            if (!this.table.varExists(unitCtx.var_name().getText())) throw new UnknownSymbolException(unitCtx.var_name().getText());
            String varName = unitCtx.var_name().getText();
            ArgType typeN = this.table.getVarType(varName);

            int varCode = 4;
            if (unitCtx.var_value() != null) {
                //handle varName
                if (unitCtx.var_value().var_name() != null) {
                    if (!this.table.varExists(varName)) throw new UnknownSymbolException(varName);
                    // Var-to-Var
                    varCode = 1;
                    ArgType typeE = this.table.getVarType(unitCtx.var_value().var_name().getText());
                    if (typeN != ArgType.ANY) {
                        if (typeN != typeE) throw new TypeMismatchException(typeN, typeE);
                    }
                }

                //handle standaloneItem
                else if (unitCtx.var_value().standalone_item() != null) {
                    //Var-to-Standalone
                    varCode = 2;
                    if (getItemType(unitCtx.var_value().standalone_item()) != typeN && typeN != ArgType.ANY)
                        throw new TypeMismatchException(typeN, getItemType(unitCtx.var_value().standalone_item()));
                } else if(unitCtx.var_value().fun_call() != null) {
                    // Var-to-Fun
                    varCode = 3;
                }
                ActionType actionType;

                CodeBlock funRes = null;

                if (varCode == 1 || varCode == 2) {
                    actionType = ActionType.SIMPLE_ASSIGN;
                } else if (varCode == 3){
                    funRes = parseFunCall(unitCtx.var_value().fun_call());
                    actionType = funRes.getActionType();
                } else {
                    actionType = ActionType.NULL;
                }

                CodeBlock cb = new CodeBlock(BlockType.SET_VARIABLE, actionType, null);
                cb.args().addAtFirstNull(new VarArg(varName, this.table.getVarScope(varName)));
                if (varCode == 1) {
                    cb.args().addAtFirstNull(new VarArg(unitCtx.var_value().var_name().getText(), this.table.getVarScope(unitCtx.var_value().var_name().getText())));
                } else if (varCode == 2) {
                    CodeArg ca = new CodeArg(getItemType(unitCtx.var_value().standalone_item()));
                    switch (ca.getType()) {
                        case NUM -> ca.putData("name", unitCtx.var_value().getText());
                        case STRING, STYLED_TEXT -> ca.putData("name", trimStringContexts(unitCtx.var_value().getText()));
                    }
                    cb.args().addAtFirstNull(ca);
                } else if (varCode == 3) {
                    if (actionType == null){
                        String funName = unitCtx.var_value().fun_call().single_fun_call().SAFE_TEXT().getText();
                        if (funTable.get(funName) == null)
                            throw new UnknownSymbolException(funName);
                        DefinitionBlock newBlock = new DefinitionBlock(BlockType.CALL_FUNC, null, funName);
                        newBlock.setArgs(cb.args());
                        cb = newBlock;
                    }
                    cb.args().extend(funRes.args());
                }
                bt.add(cb);
            }

        }
        return bt;
    }

    public CodeBlock parseFunCall(Fun_callContext ctx) throws BaseCarbonException {
        String singleFunCallName = ctx.single_fun_call().SAFE_TEXT().getText();
        ActionType action = ActionType.fromCodeName(singleFunCallName);
        if (action == null){
            if (funTable.get(singleFunCallName) == null)
                throw new UnrecognizedTokenException(singleFunCallName);
            else {
                BlockType type;
                switch (funTable.get(singleFunCallName)){
                    case FUNC -> type = BlockType.CALL_FUNC;
                    case PROCESS -> type = BlockType.START_PROCESS;
                    default -> type = null;
                }

                CodeBlock retBlock = new DefinitionBlock(type, null, singleFunCallName);
                retBlock.setArgs(funCallParams(ctx.single_fun_call().call_params()));
                return retBlock;
            }
        }

        TargetType targetType = null;

        if (ctx.fun_call_chain() != null && !ctx.fun_call_chain().isEmpty()){
            // TODO properly handle this
            if (ctx.fun_call_chain().size() > 1) throw new CarbonException("You cannot currently chain more than 1 call right now!");
            if (ctx.fun_call_chain().get(0).single_token_call() == null) throw new CarbonException("You cannot currently chain function calls!");
            Single_token_callContext tokenCtx = ctx.fun_call_chain(0).single_token_call();
            targetType = TargetType.fromCodeName(tokenCtx.getText());
            if (targetType == null && !(tokenCtx.getText().equals(action.getBlock().getCodeName()))){
                throw new CarbonException("Mismatched tokens");
            }
        }



        CodeBlock retBlock = new CodeBlock(action.getBlock(), action, targetType);
        if(ctx.single_fun_call().call_params() != null) retBlock.setArgs(funCallParams(ctx.single_fun_call().call_params()));
        return retBlock;
    }

    public ArgsTable funCallParams(Call_paramsContext ctx) throws BaseCarbonException {
        ArgsTable args = new ArgsTable();
        CodeArg arg;

        if (ctx == null) return args;

        for (Call_paramContext param: ctx.call_param()){
            if (param.var_name() != null) {
                args.addAtFirstNull(varArg(param));
            } else if (param.standalone_item() != null) {
                args.addAtFirstNull(standaloneItemArg(param));
            }
        }
        return args;
    }

    private CodeArg newVarArg(String name, VarScope scope){
        return new VarArg(name, scope);
    }

    private CodeArg varArg(Call_paramContext ctx) throws UnknownSymbolException {
        String varName = ctx.var_name().getText();

        // Var already exists in table
        if (this.table.varExists(ctx.var_name().getText())){
            return newVarArg(varName, table.getVarScope(varName));
        }

        throw new UnknownSymbolException("Could not recognize symbol \"" + varName + "\"");


    }

    private static final HashMap<String, String> translateEscapeSeqs = new HashMap<>(){{
       put("\\n", "\n");
       put("\\r", "\r");
       put("\\t", "\t");
       put("\\b", "\b");
       put("\\s", " ");
       put("\\\"", "\"");
       put("\\'", "'");
       put("\\\\", "\\");
    }};

    private static CodeArg standaloneItemArg(Call_paramContext ctx){
        if (ctx.standalone_item().simple_item() == null) return null;
        Simple_itemContext itemCtx = ctx.standalone_item().simple_item();

        if (itemCtx.string() != null) {
            if (itemCtx.string().simple_string() != null)
                return new CodeArg(ArgType.STRING)
                    .putData("name", trimStringContexts(itemCtx.string().getText()));
            else if (itemCtx.string().styled_text() != null)
                return new CodeArg(ArgType.STYLED_TEXT)
                        .putData("name", trimStringContexts(itemCtx.string().getText()));
        }
        if (itemCtx.NUMBER() != null) {
            return new CodeArg(ArgType.NUM).putData("name", itemCtx.NUMBER().getText());
        }


        return null;
    }

}
