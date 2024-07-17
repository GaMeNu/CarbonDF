package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.compile.TranspileUtils;
import me.gamenu.carbon.logic.args.VarTable;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import me.gamenu.carbon.logic.exceptions.TypeException;
import me.gamenu.carbon.logic.exceptions.UnknownEventException;
import me.gamenu.carbon.logic.exceptions.UnrecognizedTokenException;
import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.TerminalNode;

import static me.gamenu.carbon.parser.CarbonDFParser.*;

public class DefListener extends BaseCarbonListener {

    Modifiers modifiers;

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

        modifiers = Modifiers.generateModifiers(ctx.startdef());

        programContext.setCurrentDefTable(defTable);
        programContext.setCurrentFunFromName(ctx.startdef().def_name().getText());

        enterStartdef(ctx.startdef());

        if (defBlock == null) return;

        if (ctx.defblock() == null && !modifiers.isExtern()){
            if (defBlock.getBlockType() == BlockType.fromID("func") || defBlock.getBlockType() == BlockType.fromID("process"))
                throwError("Non-external definitions does not contain a body. Please mark your function as \"extern\", or give it a body.", ctx.startdef(), CarbonTranspileException.class);
            else
                throwError("Event is missing a required body.", ctx.startdef(), CarbonTranspileException.class);
        }

        if (ctx.defblock() != null && modifiers.isExtern()){
            throwError("External definitions cannot contain a function body.", ctx.startdef(), CarbonTranspileException.class);
        }
        if (modifiers.isLsCancel()){
            if (defBlock.getBlockType() != BlockType.fromID("event") && defBlock.getBlockType() != BlockType.fromID("entity_event"))
                throwError("Only cancelable events may be marked as LS-CANCEL.", ctx.startdef().def_keyword(), CarbonTranspileException.class);
            else if (!defBlock.getActionType().isCancellable()){
                throwError("Only cancelable events may be marked as LS-CANCEL.", ctx.startdef().def_name(), CarbonTranspileException.class);
            } else {
                defBlock.setAttribute(CodeBlock.Attribute.LS_CANCEL);
            }
        }

        if (!modifiers.isExtern()) {
            defTable.add(defBlock);
            enterDefblock(ctx.defblock());
        }

        exitSingle_def(ctx);

    }

    @Override
    public void exitSingle_def(Single_defContext ctx) {
        super.exitSingle_def(ctx);
        varTable.clearLineScope();
    }

    @Override
    public void enterStartdef(CarbonDFParser.StartdefContext ctx) {
        super.enterStartdef(ctx);


        defName = ctx.def_name().getText();
        try {
            switch (((TerminalNode) ctx.def_keyword().getChild(0)).getSymbol().getType()) {
                case FUNDEF_KEYWORD -> defBlock = new DefinitionBlock(BlockType.fromID("func"), null, defName);
                case PROCDEF_KEYWORD -> defBlock = new DefinitionBlock(BlockType.fromID("process"), null, defName);
                case EVENTDEF_KEYWORD -> defBlock = EventBlock.fromID(defName);
                default -> throw new ParseCancellationException(new UnrecognizedTokenException(ctx.def_keyword().getText()));
            }
        } catch (UnknownEventException e){
            throwError(e.getMessage(), ctx.def_name(), CarbonTranspileException.class);
            return;
        }

        enterDef_params(ctx.def_params());
        if (ctx.ret_params() != null) enterRet_params(ctx.ret_params());

        if (modifiers.isExtern())
            if (defBlock.getBlockType() != BlockType.fromID("func") && defBlock.getBlockType() != BlockType.fromID("process")){
                throwError("Only functions and processes may be marked as external.", ctx, CarbonTranspileException.class);
            }

        if (!defBlock.getArgs().getArgDataList().isEmpty() && defBlock.getBlockType() != BlockType.fromID("func")){
            throwError("Only Function definitions may contain parameters", ctx, CarbonTranspileException.class);
        }

        // We only do this here because otherwise we fail the check above.
        // Great design choice, I know.
        // I agree. -Eztyl
        if (defBlock.getBlockType() == BlockType.fromID("func")) placeFunctionParams();
        else if (defBlock.getBlockType() == BlockType.fromID("process")) placeProcessParams();
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
                    new VarArg(newParam.getName(), VarScope.LINE, false, newParam.getInternalArg())
            );
            if (newParam.isPlural()) varTable.get(newParam.getName()).setValue(new CodeArg(ArgType.LIST));
        }

        defBlock.getArgs().extend(paramOutputBuffer);


    }

    @Override
    public void enterRet_params(Ret_paramsContext ctx) {
        super.enterRet_params(ctx);
        if (ctx == null) return;
        if (defBlock.getBlockType() != BlockType.fromID("func")) throwError("Only function definitions may contain parameters", ctx, CarbonTranspileException.class);

        paramOutputBuffer = new ArgsTable();
        for (Def_paramContext defCtx : ctx.def_param()){
            enterDef_param(defCtx);
        }

        FunctionParam newPar;

        for (int i = 0; i < paramOutputBuffer.getArgDataList().size(); i++){
            FunctionParam currentParam = (FunctionParam) paramOutputBuffer.get(i);
            newPar = new FunctionParam(currentParam.getName(),
                    new VarArg(currentParam.getName(), VarScope.LINE, false, currentParam.getInternalArg())
            );

            defBlock.getArgs().add(i, newPar);

            VarArg curVar = new VarArg(currentParam.getName(), VarScope.LINE, false, currentParam.getInternalArg());


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

        if (ctx.param_options() != null){
            if (ctx.param_options().PARAM_OPTIONAL(0) != null) {
                newParam.setOptional(true);
            }
            if (ctx.param_options().PARAM_PLURAL(0) != null) {
                newParam.setPlural(true);
            }
        }

        if (ctx.standalone_item() != null){
            if (!newParam.isOptional() || newParam.isPlural()) {
                throwError("Only optional and non-plural parameters may have a default value", ctx.standalone_item(), CarbonTranspileException.class);
            }

            CodeArg defVal = standaloneToCodeArg(ctx.standalone_item());
            if (newParam.getParamType() == ArgType.LIST
                    || newParam.getParamType() == ArgType.DICT
                    || newParam.getParamType() == ArgType.VAR)
                throwError("Parameter type does not support default values.", ctx.standalone_item(), TypeException.class);

            if (newParam.getParamType() != defVal.getType()
                    && newParam.getParamType() != ArgType.ANY) {
                throwError("Parameter type " + newParam.getParamType() + " does not match type of default value " + defVal.getType(), ctx.standalone_item(), TypeException.class);

            }

            newParam.setDefaultValue(defVal);
        }
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
        ActionType at = ActionType.getMatchingDynamicAction(defBlock.getBlockType());
        TagType hidden = TagType.getTagType(at, "IsHidden");
        if (!modifiers.isVisible()){
            return new BlockTag(BlockType.fromID("process"), at, hidden, TagOption.getTagOption(hidden, "True"));
        } else {
            return new BlockTag(BlockType.fromID("process"), at, hidden, TagOption.getTagOption(hidden, "False"));
        }
    }

    @Override
    public void enterDefblock(CarbonDFParser.DefblockContext ctx) {
        super.enterDefblock(ctx);
        if (ctx == null) return;
        SingleLineListener singleLineListener = new SingleLineListener(programContext);

        int lineCnt = 0;
        for (CarbonDFParser.Single_lineContext lineCtx : ctx.single_line()) {
            lineCtx.enterRule(singleLineListener);
            if (lineCtx.comment() == null) lineCnt++;
        }

        if (lineCnt == 0) throwError("Definition has an empty body", ctx, CarbonTranspileException.class, CarbonTranspileException.Severity.WARN);
    }

    // This is duped from FunListener#standaloneToCodeArg because I'm lazy
    private CodeArg standaloneToCodeArg(CarbonDFParser.Standalone_itemContext itemCtx) {
        ParamListener paramListener = new ParamListener(programContext);
        paramListener.enterStandalone_item(itemCtx);
        return paramListener.getCodeArg();
    }
}
