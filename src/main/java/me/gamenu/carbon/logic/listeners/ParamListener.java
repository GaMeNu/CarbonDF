package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.BlockType;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.etc.TargetType;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.logic.exceptions.InvalidNameException;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.util.ArrayList;
import java.util.Map;

import static me.gamenu.carbon.logic.compile.TranspileUtils.targetToType;

public class ParamListener extends BaseCarbonListener {

    CodeArg codeArg;
    VarTable varTable;
    BlockType blockType;

    InstantiatorTable.InstantiatorType instType;

    private Map<String, InstantiatorTable.InstantiatorType> instTable = InstantiatorTable.getInstantiatorMap();

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

        if (ctx.simple_item() != null) {
            codeArg = getSimpleItem(ctx.simple_item());
        } else {
            enterInstantiator(ctx.instantiator());
        }

    }
    public CodeArg getSimpleItem(CarbonDFParser.Simple_itemContext simpleCtx) {
        if (simpleCtx.number() != null){
            return new CodeArg(ArgType.NUM).setArgName(simpleCtx.number().getText());
        }
        if (simpleCtx.simple_string() != null) {
            String stringString = simpleCtx.simple_string().getText();
            return new CodeArg(ArgType.STRING).setArgName(stringString.substring(1, stringString.length() - 1));
        }
        if (simpleCtx.styled_text() != null){
            String stringString = simpleCtx.styled_text().getText();
            return new CodeArg(ArgType.STYLED_TEXT).setArgName(stringString.substring(1, stringString.length()-1));
        }

        if (simpleCtx.game_value() != null){
            TargetType valTarget = targetToType(simpleCtx.game_value().target());
            String valName = simpleCtx.game_value().var_name().getText();
            GameValue.GameValueType gvType = GameValue.GameValueType.fromCodeName(valName);
            return new GameValue(gvType, valTarget);
        }
        return null;
    }

    @Override
    public void enterInstantiator(CarbonDFParser.InstantiatorContext ctx) {
        super.enterInstantiator(ctx);

        String instName;
        if (ctx.type_annotations() != null) instName = ctx.type_annotations().getText();
        else instName = ctx.SAFE_TEXT().getText();

        instType = instTable.get(instName);
        if (instType == null){
            throwError("Invalid instantiator name \"" + instName + "\"", ctx, InvalidNameException.class);
            return;
        }


        if (ctx.inst_params() != null) {
            enterInst_params(ctx.inst_params());
        }
    }

    @Override
    public void enterInst_params(CarbonDFParser.Inst_paramsContext ctx) {
        super.enterInst_params(ctx);

        ArrayList<ArgsTable> instParamsLs = instType.getParamOptions();

        ArgsTable instArgs = new ArgsTable();
        for (CarbonDFParser.Inst_paramContext paramCtx: ctx.inst_param()){
            instArgs.addAtFirstNull(getSimpleItem(paramCtx.simple_item()));
        }

        boolean res = false;
        for (int i = 0; i < instParamsLs.size() && !res; i++)
            res = matchForArgsOption(ctx, instArgs, instType, i);

        if (!res && !instArgs.getArgDataList().isEmpty()){
            StringBuilder sb = new StringBuilder();
            sb.append(instArgs.get(0).getType());
            for (int i = 1; i < instArgs.getArgDataList().size(); i++){
                sb.append(", ").append(instArgs.get(i).getType());
            }


            throwError("Could not identify instantiator.", ctx.getParent(), CarbonTranspileException.class);
        }
    }

    private boolean matchForArgsOption(CarbonDFParser.Inst_paramsContext ctx, ArgsTable instArgs, InstantiatorTable.InstantiatorType instType, int index) {
        ArgsTable instParams = instType.getParamOptions().get(index);

        if (!instArgs.matchSizes(instParams)) return false;

        int match = instArgs.matchParams(instParams);

        if (match >= 0) return false;

        codeArg = instType.getArgSetter(index).apply(instArgs);

        return true;
    }
}
