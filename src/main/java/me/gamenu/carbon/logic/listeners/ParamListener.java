package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.ArgType;
import me.gamenu.carbon.logic.args.CodeArg;
import me.gamenu.carbon.logic.args.VarArg;
import me.gamenu.carbon.logic.blocks.BlockType;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.args.VarTable;
import me.gamenu.carbon.logic.exceptions.InvalidNameException;
import me.gamenu.carbon.parser.CarbonDFParser;

public class ParamListener extends BaseCarbonListener {

    CodeArg codeArg;
    VarTable varTable;
    BlockType blockType;

    public ParamListener(ProgramContext programContext) {
        super(programContext);
        this.varTable = programContext.getVarTable();
    }

    public ParamListener setBlockType(BlockType blockType) {
        this.blockType = blockType;
        return this;
    }

    public CodeArg getCodeArg() {
        return codeArg;
    }

    @Override
    public void enterCall_param(CarbonDFParser.Call_paramContext ctx) {
        super.enterCall_param(ctx);
        if (ctx.standalone_item() != null)
            enterStandalone_item(ctx.standalone_item());
        if (ctx.var_name() != null) {
            enterVar_name(ctx.var_name());
        }
    }

    @Override
    public void enterVar_name(CarbonDFParser.Var_nameContext ctx) {
        super.enterVar_name(ctx);
        if (!varTable.varExists(ctx.getText())) throwError("Could not identify variable \"" + ctx.getText() + "\". Is it defined?", ctx, InvalidNameException.class);
        codeArg = new VarArg(ctx.getText(), varTable.getVarScope(ctx.getText()), varTable.get(ctx.getText()).isDynamic(), varTable.getVarType(ctx.getText()));

    }

    @Override
    public void enterStandalone_item(CarbonDFParser.Standalone_itemContext ctx) {
        super.enterStandalone_item(ctx);
        CarbonDFParser.Simple_itemContext simpleCtx = ctx.simple_item();
        if (simpleCtx.number() != null){
            codeArg = new CodeArg(ArgType.NUM).setArgName(simpleCtx.number().getText());
            return;
        }
        if (simpleCtx.simple_string() != null) {
            String stringString = simpleCtx.simple_string().getText();
            codeArg = new CodeArg(ArgType.STRING).setArgName(stringString.substring(1, stringString.length() - 1));
            return;
        }
        if (simpleCtx.styled_text() != null){
            String stringString = simpleCtx.styled_text().getText();
            codeArg = new CodeArg(ArgType.STYLED_TEXT).setArgName(stringString.substring(1, stringString.length()-1));
        }
    }
}
