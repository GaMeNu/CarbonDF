package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.compile.TranspileUtils;
import me.gamenu.carbon.logic.args.VarTable;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.logic.exceptions.UnknownEventException;
import me.gamenu.carbon.logic.exceptions.UnrecognizedTokenException;
import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;

import static me.gamenu.carbon.parser.CarbonDFParser.*;

public class DefListener extends BaseCarbonListener {

    boolean isExtern;
    boolean isVisible;

    String defName;

    CodeBlock defBlock;

    BlocksTable defTable;

    VarTable varTable;

    ArgsTable paramOutputBuffer;

    public DefListener(ProgramContext programContext) {
        super(programContext);
        this.varTable = programContext.getVarTable();
    }

    public BlocksTable getDefTable() {
        return defTable;
    }

    @Override
    public void enterSingle_def(CarbonDFParser.Single_defContext ctx) {
        super.enterSingle_def(ctx);
        defTable = new BlocksTable();

        programContext.setCurrentDefTable(defTable);
        programContext.setCurrentFunFromName(ctx.startdef().def_name().getText());

        enterStartdef(ctx.startdef());

        if (ctx.defblock() == null && !isExtern){
            if (defBlock.getBlockType() == BlockType.FUNC || defBlock.getBlockType() == BlockType.PROCESS)
                throwError("Non-external definitions does not contain a body. Please mark your function as \"extern\", or give it a body.", ctx.startdef(), CarbonTranspileException.class);
            else
                throwError("Event is missing a required body.", ctx.startdef(), CarbonTranspileException.class);
        }

        if (ctx.defblock() != null && isExtern){
            throwError("External definitions cannot contain a function body.", ctx.startdef(), CarbonTranspileException.class);
        }

        defTable.add(defBlock);

        if (!isExtern) {
            DefblockListener defblockListener = new DefblockListener(programContext);
            ctx.defblock().enterRule(defblockListener);
        }

        exitSingle_def(ctx);

    }

    @Override
    public void exitSingle_def(Single_defContext ctx) {
        varTable.clearLineScope();
        super.exitSingle_def(ctx);
    }

    @Override
    public void enterStartdef(CarbonDFParser.StartdefContext ctx) {
        super.enterStartdef(ctx);

        isExtern = ctx.extern_modifier() != null;
        isVisible = getVisModifier(ctx);
        defName = ctx.def_name().getText();
        try {
            switch (((TerminalNode) ctx.def_keyword().getChild(0)).getSymbol().getType()) {
                case FUNDEF_KEYWORD -> defBlock = new DefinitionBlock(BlockType.FUNC, null, defName);
                case PROCDEF_KEYWORD -> defBlock = new DefinitionBlock(BlockType.PROCESS, null, defName);
                case EVENTDEF_KEYWORD -> defBlock = EventBlock.fromID(defName);
                default -> throw new ParseCancellationException(new UnrecognizedTokenException(ctx.def_keyword().getText()));
            }
        } catch (UnknownEventException e){
            throwError(e.getMessage(), ctx, CarbonTranspileException.class);
        }

        enterDef_params(ctx.def_params());
        if (ctx.ret_params() != null) enterRet_params(ctx.ret_params());

        if (isExtern)
            if (defBlock.getBlockType() != BlockType.FUNC && defBlock.getBlockType() != BlockType.PROCESS){
                throwError("Only functions and processes may be marked as external.", ctx, CarbonTranspileException.class);
            }

        if (!defBlock.getArgs().getArgDataList().isEmpty() && defBlock.getBlockType() != BlockType.FUNC){
            throwError("Only Function definitions may contain parameters", ctx, CarbonTranspileException.class);
        }

        // We only do this here because otherwise we fail the check above.
        // Great design choice, I know.
        // I agree. -Eztyl
        if (defBlock.getBlockType() == BlockType.FUNC) placeFunctionParams();
        else if (defBlock.getBlockType() == BlockType.PROCESS) placeProcessParams();
    }

    private static boolean getVisModifier(CarbonDFParser.StartdefContext context){
        if (context.vis_modifier() == null) return false;
        return (context.vis_modifier().MOD_INVISIBLE() != null);
    }


    @Override
    public void enterDef_params(Def_paramsContext ctx) {
        super.enterDef_params(ctx);
        if (ctx ==  null) return;

        paramOutputBuffer = new ArgsTable();
        for (Def_paramContext paramCtx: ctx.def_param()){
            enterDef_param(paramCtx);
        }

        for (CodeArg arg : paramOutputBuffer.getArgDataList()) {
            FunctionParam newParam = (FunctionParam) arg;
            varTable.putVar(
                    new VarArg(newParam.getName(), VarScope.LINE, false, newParam.getParamType())
                            .setValue(new CodeArg(newParam.getParamType()))
            );
        }

        defBlock.getArgs().extend(paramOutputBuffer);


    }

    @Override
    public void enterRet_params(Ret_paramsContext ctx) {
        super.enterRet_params(ctx);
        if (ctx == null) return;
        if (defBlock.getBlockType() != BlockType.FUNC) throwError("Only function definitions may contain parameters", ctx, CarbonTranspileException.class);

        paramOutputBuffer = new ArgsTable();
        for (Def_paramContext defCtx : ctx.def_param()){
            enterDef_param(defCtx);
        }

        FunctionParam newPar;

        for (int i = 0; i < paramOutputBuffer.getArgDataList().size(); i++){
            FunctionParam currentParam = (FunctionParam) paramOutputBuffer.get(i);
            newPar = new FunctionParam(currentParam.getName(),
                    new VarArg(currentParam.getName(), VarScope.LINE, false, currentParam.getParamType())
            );

            defBlock.getArgs().add(i, newPar);

            VarArg curVar = new VarArg(currentParam.getName(), VarScope.LINE, false, currentParam.getParamType());


            varTable.putVar(curVar);
        }

    }

    @Override
    public void enterDef_param(Def_paramContext ctx) {
        super.enterDef_param(ctx);

        FunctionParam newParam;
        if (ctx.type_annotations() != null)
            newParam = new FunctionParam(ctx.SAFE_TEXT().getText(), new CodeArg(TranspileUtils.annotationToArgType(ctx.type_annotations())));
        else
            newParam = new FunctionParam(ctx.SAFE_TEXT().getText(), new CodeArg(ArgType.ANY));

        if (newParam.getParamType() == ArgType.VAR) throwError("Variable Parameters are not allowed within function arguments. Please write them as return arguments instead.", ctx, CarbonTranspileException.class);
        if (varTable.varExists(newParam.getName())) throwError("Variable \"" + newParam.getName() + "\" was redefined", ctx, CarbonTranspileException.class);

        paramOutputBuffer.addAtFirstNull(newParam);
    }

    private void placeFunctionParams(){
        defBlock.getArgs().set(25, new CodeArg(ArgType.HINT).putData("id", "function"));
        defBlock.getArgs().set(26, getHiddenTag());
    }

    private void placeProcessParams(){
        defBlock.getArgs().set(26, getHiddenTag());
    }

    private BlockTag getHiddenTag() {
        if (!isVisible){
            return new BlockTag("Is Hidden", BlockType.PROCESS, ActionType.DYNAMIC, "True");
        } else {
            return new BlockTag("Is Hidden", BlockType.PROCESS, ActionType.DYNAMIC, "False");
        }
    }
}
