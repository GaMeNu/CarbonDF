package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.exceptions.BaseCarbonException;
import me.gamenu.carbon.logic.exceptions.CarbonException;
import me.gamenu.carbon.logic.exceptions.UnknownSymbolException;
import me.gamenu.carbon.logic.exceptions.UnrecognizedTokenException;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.util.ArrayList;
import java.util.HashMap;

public class TranslateDefinition {

    public static ArrayList<BlocksTable> translate(CarbonDFParser.BaseContext base) throws BaseCarbonException {
        ArrayList<BlocksTable> tableList = new ArrayList<>();


        TranslateDefinition translator = new TranslateDefinition();

        translator.generateFunTable(base);

        for (int i=0; i < base.single_def().size(); i++) {
            // Do not compile _ definitions
            if (base.single_def().get(i).startdef().def_name().getText().equals("_")) continue;

            BlocksTable bt = translator.singleDefinitionTable(base.single_def(i).startdef());

            TranslateBlock blockTranslator = new TranslateBlock(translator.varTable, translator.funTable);

            if (base.single_def(i).defblock() == null) continue;

            bt.extend(blockTranslator.translateBlock(base.single_def(i).defblock()));
            tableList.add(bt);
            translator.varTable.clearLineScope();
        }

        return tableList;
    }

    final VarTable varTable;
    final HashMap<String, BlockType> funTable;

    public TranslateDefinition(){
        this.varTable = new VarTable();
        this.funTable = new HashMap<>();
    }

    public void generateFunTable(CarbonDFParser.BaseContext base) throws BaseCarbonException {
        for (CarbonDFParser.Single_defContext def: base.single_def()) {
            boolean isExtern = getExternModifier(def.startdef());
            if (!isExtern && def.defblock() == null) throw new CarbonException("Non-external functions must have a function body");
            if (def.startdef().def_keyword().FUNDEF_KEYWORD() != null) {
                funTable.put(def.startdef().def_name().getText(), BlockType.FUNC);
            } else if (def.startdef().def_keyword().PROCDEF_KEYWORD() != null){
                funTable.put(def.startdef().def_name().getText(), BlockType.PROCESS);
            }
        }
    }

    public BlocksTable singleDefinitionTable(CarbonDFParser.StartdefContext context) throws BaseCarbonException {
        BlocksTable table = new BlocksTable();
        CodeBlock newBlock;

        boolean hidden = getVisModifier(context);

        if (context.def_keyword().FUNDEF_KEYWORD() != null){
            newBlock = new DefinitionBlock(BlockType.FUNC, null, context.def_name().getText());
            newBlock.setArgs(functionParams(context, hidden));

        } else if (context.def_keyword().PROCDEF_KEYWORD() != null) {
            newBlock = new DefinitionBlock(BlockType.PROCESS, null, context.def_name().getText());
            newBlock.setArgs(processParams(hidden));
        } else if (context.def_keyword().EVENTDEF_KEYWORD() != null) {
            newBlock = EventBlock.fromID(context.def_name().getText());
        } else {
            throw new UnrecognizedTokenException(context.def_keyword().getText());
        }

        table.add(newBlock);

        return table;
    }

    private static boolean getVisModifier(CarbonDFParser.StartdefContext context){
        if (context.vis_modifier() == null) return false;
        return (context.vis_modifier().MOD_INVISIBLE() != null);
    }

    private static boolean getExternModifier(CarbonDFParser.StartdefContext context){
        return (context.extern_modifier() != null);
    }

    private static BlockTag getHiddenTag(boolean isHidden){
        if (isHidden){
            return new BlockTag("Is Hidden", BlockType.PROCESS, ActionType.DYNAMIC, "True");
        } else {
            return new BlockTag("Is Hidden", BlockType.PROCESS, ActionType.DYNAMIC, "False");
        }

    }

    public ArgsTable functionParams(CarbonDFParser.StartdefContext functionContext, boolean isHidden) throws UnknownSymbolException {

        ArgsTable args = new ArgsTable();
        args.set(25, new CodeArg(ArgType.HINT).putData("id", "function"));
        args.set(26, getHiddenTag(isHidden));

        // THIS CHECK IS IMPORTANT!
        // ANTLR is being a dick, and we have to check if the def params even exist.
        // Otherwise, it'll throw NullPointerException when we try to iterate over it smh
        if (functionContext.def_params() == null) return args;

        for (CarbonDFParser.Def_paramContext paramContext : functionContext.def_params().def_param()) {
            ArgType type;

            CarbonDFParser.Type_annotationsContext typeContext = paramContext.type_annotations();

            // Can I PLEASE use switch-case here?
            // WHY ANTLR WHY
            if (typeContext == null) type = ArgType.ANY;
            else type = TranspileUtils.annotationToArgType(typeContext);


            // args.addAtFirstNull(new FunctionParam(paramContext.SAFE_TEXT().getText(), type));
            // this.varTable.putVar(paramContext.SAFE_TEXT().getText(), VarScope.LINE);
        }

        return args;
    }

    public static ArgsTable processParams(boolean isHidden){
        ArgsTable args = new ArgsTable();

        args.set(26, getHiddenTag(isHidden));
        return args;
    }


}
