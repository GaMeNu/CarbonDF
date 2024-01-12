package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.blocks.ActionType;
import me.gamenu.carbon.logic.blocks.BlocksTable;
import me.gamenu.carbon.logic.blocks.CodeBlock;
import me.gamenu.carbon.logic.etc.TargetType;
import me.gamenu.carbon.logic.exceptions.BaseCarbonException;
import me.gamenu.carbon.logic.exceptions.CarbonException;
import me.gamenu.carbon.logic.exceptions.UnrecognizedTokenException;
import me.gamenu.carbon.parser.CarbonDFParser.*;

public class TranslateBlock {
    public static BlocksTable translateBlock(BlockContext blockContext) throws BaseCarbonException {
        BlocksTable codeBlockList = new BlocksTable();
        for (Single_lineContext lineContext: blockContext.single_line()) {
            // We SKIP Empty lines and comments
            if (lineContext.isEmpty() || lineContext.comment() != null) continue;

            if (lineContext.simple_statement() != null) codeBlockList.add(parseSimpleStatement(lineContext.simple_statement()));
        }

        return codeBlockList;
    }

    public static CodeBlock parseSimpleStatement(Simple_statementContext ctx) throws BaseCarbonException {
        if (ctx.fun_call() != null) return parseFunCall(ctx.fun_call());
        return null;
    }

    public static CodeBlock parseFunCall(Fun_callContext ctx) throws BaseCarbonException {
        String singleFunCallName = ctx.single_fun_call().SAFE_TEXT().getText();
        ActionType action = ActionType.fromCodeName(singleFunCallName);
        if (action == null){
            throw new UnrecognizedTokenException(singleFunCallName);
        }

        TargetType targetType = null;

        if (ctx.fun_call_chain() != null){
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

    public static ArgsTable funCallParams(Call_paramsContext ctx){
        ArgsTable args = new ArgsTable();
        // TODO args
        return args;
    }
}
