package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.util.Arrays;

public class IfListener extends BaseCarbonListener{

    private static BlockType[] ifTypes = {BlockType.IF_VARIABLE, BlockType.IF_GAME, BlockType.IF_PLAYER, BlockType.IF_ENTITY};
    public IfListener(ProgramContext programContext) {
        super(programContext);
    }

    BlocksTable blocksTable = programContext.getCurrentDefTable();

    @Override
    public void enterIf_statement(CarbonDFParser.If_statementContext ctx) {
        super.enterIf_statement(ctx);
        CarbonDFParser.Fun_callContext callCtx = ctx.fun_call();

        FunListener funListener = new FunListener(programContext);
        funListener.enterFun_call(callCtx);

        BlockType bt = blocksTable.get(blocksTable.list().size()-1).getBlockType();

        if (Arrays.stream(ifTypes).noneMatch(bt::equals))
            throwError("Unsupported function call found in IF block.", ctx.fun_call(), CarbonTranspileException.class);

        enterBlock(ctx.block(0));

        if (ctx.ELSE_KEYWORD() != null) {
            blocksTable.add(new CodeBlock(BlockType.ELSE, ActionType.NULL));
            enterBlock(ctx.block(1));
        }
    }

    @Override
    public void enterBlock(CarbonDFParser.BlockContext ctx) {
        super.enterBlock(ctx);
        blocksTable.add(new Bracket(Bracket.Direction.OPEN, Bracket.Type.NORMAL));

        SingleLineListener lineListener = new SingleLineListener(programContext);

        for (CarbonDFParser.Single_lineContext lineCtx: ctx.single_line()){
            lineCtx.enterRule(lineListener);
        }

        exitBlock(ctx);
    }

    @Override
    public void exitBlock(CarbonDFParser.BlockContext ctx) {
        super.exitBlock(ctx);

        blocksTable.add(new Bracket(Bracket.Direction.CLOSE, Bracket.Type.NORMAL));
    }
}
