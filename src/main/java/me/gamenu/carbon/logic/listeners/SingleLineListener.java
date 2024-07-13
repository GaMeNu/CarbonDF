package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.VarTable;
import me.gamenu.carbon.logic.blocks.BlocksTable;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.parser.CarbonDFParser;

public class SingleLineListener extends BaseCarbonListener{

    BlocksTable blocksTable;
    VarTable varTable;

    public SingleLineListener(ProgramContext programContext) {
        super(programContext);

        this.blocksTable = programContext.getCurrentDefTable();
        this.varTable = programContext.getVarTable();
    }

    @Override
    public void enterSingle_line(CarbonDFParser.Single_lineContext ctx) {
        super.enterSingle_line(ctx);
        if (ctx.comment() != null) return;
        if (ctx.simple_statement() != null) enterSimple_statement(ctx.simple_statement());
        else if (ctx.compound_statement() != null) enterCompound_statement(ctx.compound_statement());
    }

    @Override
    public void enterSimple_statement(CarbonDFParser.Simple_statementContext ctx) {
        super.enterSimple_statement(ctx);
        if (ctx == null) return;
        FunListener funListener = new FunListener(programContext);
        if (ctx.fun_call() != null){
            ctx.fun_call().enterRule(funListener);
        } else if (ctx.var_define() != null){
            ctx.var_define().enterRule(funListener);
        } else if (ctx.var_assign() != null){
            ctx.var_assign().enterRule(funListener);
        }
    }

    @Override
    public void enterCompound_statement(CarbonDFParser.Compound_statementContext ctx) {
        super.enterCompound_statement(ctx);

        if (ctx.if_statement() != null){
            CompoundListener ifListener = new CompoundListener(programContext);
            ifListener.enterIf_statement(ctx.if_statement());
        } else if (ctx.repeat_statement() != null) {
            CompoundListener rptListener = new CompoundListener(programContext);
            rptListener.enterRepeat_statement(ctx.repeat_statement());
        }
    }
}
