package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.blocks.BlocksTable;
import me.gamenu.carbon.logic.args.FunTable;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.args.VarTable;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.util.ArrayList;

public class ProgramBaseListener extends BaseCarbonListener {

    ArrayList<BlocksTable> tableList;



    public ProgramBaseListener(ProgramContext programContext) {
        super(programContext);
    }

    public ArrayList<BlocksTable> getTableList() {
        return tableList;
    }

    @Override
    public void enterBase(CarbonDFParser.BaseContext ctx) {
        super.enterBase(ctx);

        // This contains all defs (except extern ones)
        tableList = new ArrayList<>();

        // Initialize vars
        DefListener defListener;
        VarTable sharedTable = new VarTable();
        programContext.setVarTable(sharedTable);

        // Generate references for defs, non-line vars
        ObjectCollectorListener objectCollectorListener = new ObjectCollectorListener(programContext);
        ctx.enterRule(objectCollectorListener);
        FunTable funTable = objectCollectorListener.getFunTable();
        programContext.setFunTable(funTable);

        for (CarbonDFParser.Single_defContext def: ctx.single_def()){
            defListener = new DefListener(programContext);
            def.enterRule(defListener);
            tableList.add(defListener.getDefTable());

        }
    }



    @Override
    public void exitBase(CarbonDFParser.BaseContext ctx) {
        super.exitBase(ctx);
    }
}
