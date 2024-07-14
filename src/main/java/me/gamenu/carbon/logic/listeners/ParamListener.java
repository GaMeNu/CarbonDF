package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.BlockType;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.etc.TargetType;
import me.gamenu.carbon.logic.exceptions.InvalidNameException;
import me.gamenu.carbon.parser.CarbonDFParser;

import static me.gamenu.carbon.logic.compile.TranspileUtils.targetToType;

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
        if (!varTable.varExists(ctx.getText())) {
            throwError("Could not identify variable \"" + ctx.getText() + "\". Is it defined?", ctx, InvalidNameException.class);
            return;
        }
        codeArg = new VarArg(ctx.getText(), varTable.getVarScope(ctx.getText()), varTable.get(ctx.getText()).isDynamic(), varTable.get(ctx.getText()).getValue());

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

        if (simpleCtx.game_value() != null){
            TargetType valTarget = targetToType(simpleCtx.game_value().target());
            String valName = simpleCtx.game_value().var_name().getText();
            GameValue.GameValueType gvType = GameValue.GameValueType.fromCodeName(valName);
            codeArg = new GameValue(gvType, valTarget);
        }
    }
}
