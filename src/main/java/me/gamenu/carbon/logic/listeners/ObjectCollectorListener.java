package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.BlockType;
import me.gamenu.carbon.logic.blocks.EventBlock;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.compile.TranspileUtils;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.logic.exceptions.UnknownEventException;
import me.gamenu.carbon.logic.exceptions.UnrecognizedTokenException;
import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;

import static me.gamenu.carbon.parser.CarbonDFParser.*;

public class ObjectCollectorListener extends BaseCarbonListener{

    VarTable varTable;

    FunTable funTable;

    FunTable.FunType curType;

    ArgsTable curParams;

    public ObjectCollectorListener(ProgramContext programContext) {
        super(programContext);
        this.varTable = programContext.getVarTable();
    }

    public FunTable getFunTable() {
        return funTable;
    }

    @Override
    public void enterBase(CarbonDFParser.BaseContext ctx) {
        super.enterBase(ctx);
        funTable = new FunTable();
        for (CarbonDFParser.Single_defContext defCtx : ctx.single_def()){
            curType = new FunTable.FunType();
            enterStartdef(defCtx.startdef());
            if (curType.getType() != BlockType.FUNC && curType.getType() != BlockType.PROCESS) continue;
            funTable.put(curType.getName(), curType);
            if (defCtx.defblock() == null) continue;


            // I realized that maybe creating references to practically EVERY variable
            // and letting people call vars before they were even defined is not a great idea lol;

            /*
            for (CarbonDFParser.Single_lineContext lineCtx : defCtx.defblock().single_line()){
                enterSingle_line(lineCtx);
            }
            */
        }
    }

    @Override
    public void enterStartdef(CarbonDFParser.StartdefContext ctx) {
        super.enterStartdef(ctx);
        String name = ctx.def_name().getText();
        curParams = new ArgsTable();
        curType.setName(name);
        curType.setHidden(ctx.vis_modifier() != null);
        try {
            switch (((TerminalNode) ctx.def_keyword().getChild(0)).getSymbol().getType()) {
                case FUNDEF_KEYWORD -> curType.setType(BlockType.FUNC);
                case PROCDEF_KEYWORD -> curType.setType(BlockType.PROCESS);
                case EVENTDEF_KEYWORD -> curType.setType(EventBlock.fromID(name).getBlockType());
                default -> throw new ParseCancellationException(new UnrecognizedTokenException(ctx.def_keyword().getText()));
            }
        } catch (UnknownEventException e){
            throwError(e.getMessage(), ctx, CarbonTranspileException.class);
        }

        if (curType.getType() != BlockType.FUNC && curType.getType() != BlockType.PROCESS) return;
        if (curType.getType() == BlockType.FUNC) {


            enterDef_params(ctx.def_params());

            curType.setParams(curParams);

            enterRet_params(ctx.ret_params());

            if (curParams == null) return;

            ArgsTable returns = new ArgsTable();
            for (CodeArg arg : curParams.getArgDataList()){
                FunctionParam param = (FunctionParam) arg;
                returns.addAtFirstNull(new FunctionParam(param.getName(),
                        new VarArg(param.getName(), VarScope.LINE, false, param.getParamType())
                                .setValue(new CodeArg(param.getParamType()))
                ));
            }

            curType.setReturns(returns);

        }
    }

    @Override
    public void enterDef_params(Def_paramsContext ctx) {
        super.enterDef_params(ctx);
        if (ctx ==  null) return;

        curParams = new ArgsTable();

        for (Def_paramContext paramCtx: ctx.def_param()){
            enterDef_param(paramCtx);
        }
    }

    @Override
    public void enterRet_params(Ret_paramsContext ctx) {
        super.enterRet_params(ctx);
        if (ctx == null) return;

        curParams = new ArgsTable();

        for (Def_paramContext paramCtx: ctx.def_param()){
            enterDef_param(paramCtx);
        }
    }

    @Override
    public void enterDef_param(Def_paramContext ctx) {
        super.enterDef_param(ctx);

        FunctionParam newParam;
        if (ctx.type_annotations() != null)
            newParam = new FunctionParam(ctx.SAFE_TEXT().getText(),
                    new CodeArg(TranspileUtils.annotationToArgType(ctx.type_annotations()))
            );
        else
            newParam = new FunctionParam(ctx.SAFE_TEXT().getText(), new CodeArg(ArgType.ANY));

        if (varTable.varExists(newParam.getName())) throwError("Variable \"" + newParam.getName() + "\" was redefined", ctx, CarbonTranspileException.class);
        curParams.addAtFirstNull(newParam);
    }

}
