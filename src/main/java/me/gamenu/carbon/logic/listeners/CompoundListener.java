package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.util.Arrays;

public class CompoundListener extends BaseCarbonListener{

    private static final BlockType[] ifTypes = {BlockType.IF_VARIABLE, BlockType.IF_GAME, BlockType.IF_PLAYER, BlockType.IF_ENTITY};
    public CompoundListener(ProgramContext programContext) {
        super(programContext);
    }

    BlocksTable blocksTable = programContext.getCurrentDefTable();

    Bracket.Type bracketType;

    @Override
    public void enterIf_statement(CarbonDFParser.If_statementContext ctx) {
        super.enterIf_statement(ctx);
        CarbonDFParser.Fun_callContext callCtx = ctx.fun_call();

        FunListener funListener = new FunListener(programContext);
        funListener.enterFun_call(callCtx);

        CodeBlock resBlock = blocksTable.get(blocksTable.list().size()-1);

        if (ctx.IFNT_KEYWORD() != null){
            throwError("Alright I know that IFN'T is valid syntax, " +
                    "but please don't ACTUALLY use it, it'll hurt code readability (You do get +10 CDF points tho)",
                    ctx,
                    CarbonTranspileException.class,
                    CarbonTranspileException.Severity.WARN
            );
            resBlock.setAttribute(CodeBlock.Attribute.NOT);
        }

        if (ctx.NOT_KEYWORD() != null){
            if (resBlock.getAttribute() == CodeBlock.Attribute.NOT) resBlock.setAttribute(null);
            else resBlock.setAttribute(CodeBlock.Attribute.NOT);
        }

        BlockType bt = resBlock.getBlockType();

        if (Arrays.stream(ifTypes).noneMatch(bt::equals))
            throwError("Unsupported function call found in IF block.", ctx.fun_call(), CarbonTranspileException.class);

        bracketType = Bracket.Type.NORMAL;
        enterBlock(ctx.block(0));

        if (ctx.ELSE_KEYWORD() != null) {
            blocksTable.add(new CodeBlock(BlockType.ELSE, ActionType.NULL));
            enterBlock(ctx.block(1));
        }
    }

    @Override
    public void enterRepeat_statement(CarbonDFParser.Repeat_statementContext ctx) {
        super.enterRepeat_statement(ctx);
        CarbonDFParser.Fun_callContext callCtx = ctx.fun_call();

        String repeatTypeText = ctx.repeat_type().getText();
        ActionType rt = ActionType.fromCodeName(repeatTypeText);

        if (rt == null) {
            throwError("Could not identify repeat type.", ctx.repeat_type(), CarbonTranspileException.class);
            return;
        }

        if (rt == ActionType.REPEAT_WHILE && ctx.fun_call() == null) {
            throwError("Repeat while requires a secondary function call of one of the IF actions", ctx, CarbonTranspileException.class);
            return;
        }

        if (rt != ActionType.REPEAT_WHILE && ctx.fun_call() != null) {
            throwError("Only repeat while may contain a secondary function call", ctx.fun_call(), CarbonTranspileException.class);
            return;
        }

        if (ctx.NOT_KEYWORD() != null && rt != ActionType.REPEAT_WHILE) throwError("NOT keyword in repeat only applies to the WHILE action.", ctx, CarbonTranspileException.class);

        CodeBlock repeatBlock = new CodeBlock(BlockType.REPEAT, rt);

        CarbonDFParser.Call_paramsContext paramsCtx;

        if (rt == ActionType.REPEAT_WHILE){

            repeatBlock = repeatWhileCase(ctx, repeatBlock);
        } else if (ctx.call_params() != null){
            paramsCtx = ctx.call_params();
            ParamListener paramListener = new ParamListener(programContext);

            ArgsTable resTable = new ArgsTable();

            for (CarbonDFParser.Call_paramContext paramCtx : paramsCtx.call_param()){
                paramCtx.enterRule(paramListener);
                resTable.addAtFirstNull(paramListener.getCodeArg());
            }

            repeatBlock.setArgs(resTable);
        }

        blocksTable.add(repeatBlock);

        bracketType = Bracket.Type.REPEAT;
        enterBlock(ctx.block());

    }

    private CodeBlock repeatWhileCase(CarbonDFParser.Repeat_statementContext ctx, CodeBlock repeatBlock) {
        if (ctx.NOT_KEYWORD() != null) repeatBlock.setAttribute(CodeBlock.Attribute.NOT);
        if (ctx.fun_call() == null) {
            throwError("Repeat-while action requires a function call.", ctx, CarbonTranspileException.class);
            return null;
        }

        CodeBlock resBlock = handleFunCall(ctx.fun_call());
        if (resBlock == null) return null;
        repeatBlock.setSubAction(resBlock.getActionType());
        repeatBlock.setArgs(resBlock.getArgs());
        return repeatBlock;
    }


    @Override
    public void enterBlock(CarbonDFParser.BlockContext ctx) {
        super.enterBlock(ctx);
        blocksTable.add(new Bracket(Bracket.Direction.OPEN, bracketType));

        SingleLineListener lineListener = new SingleLineListener(programContext);

        for (CarbonDFParser.Single_lineContext lineCtx: ctx.single_line()){
            lineCtx.enterRule(lineListener);
        }

        exitBlock(ctx);
    }

    @Override
    public void exitBlock(CarbonDFParser.BlockContext ctx) {
        super.exitBlock(ctx);

        blocksTable.add(new Bracket(Bracket.Direction.CLOSE, bracketType));
    }

    public CodeBlock handleFunCall(CarbonDFParser.Fun_callContext ctx) {
        super.enterFun_call(ctx);

        FunListener funListener = new FunListener(programContext);
        funListener.enterFun_call(ctx);

        programContext.getCurrentDefTable().list().remove(programContext.getCurrentDefTable().list().size()-1);

        CodeBlock resBlock = funListener.getBlock();

        if (Arrays.stream(ifTypes).noneMatch(resBlock.getBlockType()::equals)) {
            throwError("Internal function call may only be of one of the IF types", ctx, CarbonTranspileException.class);
            return null;
        }
        return resBlock;
    }
}
