package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.BlockType;
import me.gamenu.carbon.logic.blocks.EventBlock;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.compile.TranspileUtils;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.logic.exceptions.ParamAmbiguityException;
import me.gamenu.carbon.logic.exceptions.UnknownEventException;
import me.gamenu.carbon.logic.exceptions.UnrecognizedTokenException;
import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;

import static me.gamenu.carbon.parser.CarbonDFParser.*;

public class ObjectCollectorListener extends BaseCarbonListener{

    VarTable varTable;

    FunTable funTable;

    FunTable.FunType curType;

    ArgsTable curParams;
    boolean ret;

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
            if (curType.getType() != BlockType.fromID("func") && curType.getType() != BlockType.fromID("process")) continue;
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
        Modifiers modifiers = Modifiers.generateModifiers(ctx);
        curParams = new ArgsTable();
        curType = new FunTable.FunType();
        curType.setName(name);
        curType.setHidden(modifiers.isHidden());
        try {
            switch (((TerminalNode) ctx.def_keyword().getChild(0)).getSymbol().getType()) {
                case FUNDEF_KEYWORD -> curType.setType(BlockType.fromID("func"));
                case PROCDEF_KEYWORD -> curType.setType(BlockType.fromID("process"));
                case EVENTDEF_KEYWORD -> curType.setType(EventBlock.fromID(name).getBlockType());
                default -> throw new ParseCancellationException(new UnrecognizedTokenException(ctx.def_keyword().getText()));
            }
        } catch (UnknownEventException e){
            throwError(e.getMessage(), ctx.def_name(), CarbonTranspileException.class);
        }

        if (curType.getType() != BlockType.fromID("func") && curType.getType() != BlockType.fromID("process")) return;
        if (curType.getType() == BlockType.fromID("func")) {


            enterDef_params(ctx.def_params());
            ArrayList<CodeArg> argDataList = curParams.getArgDataList();
            for (int i = 1; i < argDataList.size(); i++) {
                FunctionParam param = (FunctionParam) argDataList.get(i);
                FunctionParam prevParam = (FunctionParam) argDataList.get(i-1);
                if (prevParam.isOptional() && !param.isOptional())
                    throwError("Required parameter after optional parameter", ctx.def_params().def_param(i), ParamAmbiguityException.class);

                if (prevParam.isPlural() && prevParam.getParamType() == param.getParamType())
                    throwError("Parameter after plural parameter of the same type", ctx.def_params().def_param(i), ParamAmbiguityException.class);

            }

            curType.setParams(curParams);

            if (ctx.ret_params() == null) return;

            curParams = new ArgsTable();
            enterRet_params(ctx.ret_params());

            if (curParams == null) return;

            ArgsTable returns = new ArgsTable();
            for (CodeArg arg : curParams.getArgDataList()){
                FunctionParam param = (FunctionParam) arg;
                returns.addAtFirstNull(new FunctionParam(param.getName(),
                        new VarArg(param.getName(), VarScope.LINE, false, param.getInternalArg())
                ));
            }

            curType.setReturns(returns);

        }
    }

    @Override
    public void enterDef_params(Def_paramsContext ctx) {
        super.enterDef_params(ctx);
        if (ctx ==  null) return;
        ret = false;

        curParams = new ArgsTable();

        for (Def_paramContext paramCtx: ctx.def_param()){
            enterDef_param(paramCtx);
        }
    }

    @Override
    public void enterRet_params(Ret_paramsContext ctx) {
        super.enterRet_params(ctx);
        if (ctx == null) return;
        ret = true;

        curParams = new ArgsTable();

        for (Def_paramContext paramCtx: ctx.def_param()){
            enterDef_param(paramCtx);
        }
    }

    @Override
    public void enterDef_param(Def_paramContext ctx) {
        super.enterDef_param(ctx);

        FunctionParam newParam;
        if (ctx.type_annotations() != null) {
            newParam = new FunctionParam(ctx.SAFE_TEXT().getText(),
                    new CodeArg(TranspileUtils.annotationToArgType(ctx.type_annotations()))
            );
        }
        else {
            newParam = new FunctionParam(ctx.SAFE_TEXT().getText(), new CodeArg(ArgType.ANY));
        }

        if (ctx.param_options() != null){
            if (ret){
                throwError("Return variables cannot be optional or plural", ctx.param_options(), CarbonTranspileException.class);
                return;
            }
            if (ctx.param_options().PARAM_PLURAL(0) != null){
                newParam.setPlural(true);
            }

            if (ctx.param_options().PARAM_OPTIONAL(0) != null) {
                newParam.setOptional(true);
            }
        }

        if (varTable.varExists(newParam.getName())) throwError("Variable \"" + newParam.getName() + "\" was redefined", ctx, CarbonTranspileException.class);
        curParams.addAtFirstNull(newParam);
    }

}
