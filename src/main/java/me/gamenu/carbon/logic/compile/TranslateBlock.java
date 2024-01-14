package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.args.ArgType;
import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.args.CodeArg;
import me.gamenu.carbon.logic.blocks.ActionType;
import me.gamenu.carbon.logic.blocks.BlocksTable;
import me.gamenu.carbon.logic.blocks.CodeBlock;
import me.gamenu.carbon.logic.etc.TargetType;
import me.gamenu.carbon.logic.exceptions.BaseCarbonException;
import me.gamenu.carbon.logic.exceptions.CarbonException;
import me.gamenu.carbon.logic.exceptions.UnrecognizedTokenException;
import me.gamenu.carbon.parser.CarbonDFParser.*;

import java.util.HashMap;

public class TranslateBlock {
    private final VarTable table;

    public TranslateBlock(VarTable table){
        this.table = table;
    }
    public BlocksTable translateBlock(BlockContext blockContext) throws BaseCarbonException {
        BlocksTable codeBlockList = new BlocksTable();
        for (Single_lineContext lineContext: blockContext.single_line()) {
            // We SKIP Empty lines and comments
            if (lineContext.isEmpty() || lineContext.comment() != null) continue;

            if (lineContext.simple_statement() != null) codeBlockList.add(parseSimpleStatement(lineContext.simple_statement()));
        }

        return codeBlockList;
    }

    public CodeBlock parseSimpleStatement(Simple_statementContext ctx) throws BaseCarbonException {
        if (ctx.fun_call() != null) return parseFunCall(ctx.fun_call());
        return null;
    }

    public CodeBlock parseFunCall(Fun_callContext ctx) throws BaseCarbonException {
        String singleFunCallName = ctx.single_fun_call().SAFE_TEXT().getText();
        ActionType action = ActionType.fromCodeName(singleFunCallName);
        if (action == null){
            throw new UnrecognizedTokenException(singleFunCallName);
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

    public ArgsTable funCallParams(Call_paramsContext ctx){
        ArgsTable args = new ArgsTable();
        CodeArg arg;
        for (Call_paramContext param: ctx.call_param()){

            if (param.var_name() != null) {
                args.addAtFirstNull(varArg(param));
            } else if (param.standalone_item() != null) {
                args.addAtFirstNull(standaloneItemArg(param));
            }
        }
        return args;
    }

    private CodeArg newVarArg(String name, VarTable.VarScope scope){
        return new CodeArg(ArgType.VAR)
                .putData("name", name)
                .putData("scope", scope);
    }

    private CodeArg varArg(Call_paramContext ctx) {
        String varName = ctx.var_name().getText();

        // Var already exists in table
        if (this.table.varExists(ctx.var_name().getText())){
            return newVarArg(varName, table.getVarScope(varName));
        }

        // Var does not exist in table and does not have a defined scope
        // Set to default - global
        if (ctx.var_scope() == null) return newVarArg(varName, VarTable.VarScope.GLOBAL);

        // Handle scopes
        if (ctx.var_scope().SCOPE_GLOBAL() != null) return newVarArg(varName, VarTable.VarScope.GLOBAL);
        if (ctx.var_scope().SCOPE_SAVED() != null) return newVarArg(varName, VarTable.VarScope.SAVED);
        if (ctx.var_scope().SCOPE_LOCAL() != null) return newVarArg(varName, VarTable.VarScope.LOCAL);
        if (ctx.var_scope().SCOPE_LINE() != null) return newVarArg(varName, VarTable.VarScope.LINE);

        // Fuck over the user
        // I am done with this shit
        // TODO: fix or throw Warn/Err
        return null;
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
            return new CodeArg(ArgType.STRING).putData("name", itemCtx.string().getText().substring(1, itemCtx.string().getText().length()-1));
        }
        if (itemCtx.NUMBER() != null) {
            return new CodeArg(ArgType.NUM).putData("name", itemCtx.NUMBER().getText());
        }


        return null;
    }

}
