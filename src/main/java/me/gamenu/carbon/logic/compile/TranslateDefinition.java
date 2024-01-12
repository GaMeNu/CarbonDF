package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.exceptions.BaseCarbonException;
import me.gamenu.carbon.logic.exceptions.UnrecognizedTokenException;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.util.ArrayList;

import static me.gamenu.carbon.logic.compile.TranslateBlock.translateBlock;

public class TranslateDefinition {
    public static ArrayList<BlocksTable> translate(CarbonDFParser.BaseContext base) throws BaseCarbonException {
        ArrayList<BlocksTable> tableList = new ArrayList<>();

        for (int i=0; i < base.startdef().size(); i++) {
            BlocksTable bt = singleDefinitionTable(base.startdef(i));
            bt.extend(translateBlock(base.block(i)));
            tableList.add(bt);
        }

        return tableList;
    }

    public static BlocksTable singleDefinitionTable(CarbonDFParser.StartdefContext context) throws BaseCarbonException {
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
        if (context.def_modifiers() == null) return false;
        if (context.def_modifiers().vis_modifier() == null) return false;
        return (context.def_modifiers().vis_modifier().MOD_INVISIBLE() != null);
    }

    private static BlockTag getHiddenTag(boolean isHidden){
        if (isHidden){
            return new BlockTag("Is Hidden", BlockType.PROCESS, ActionType.DYNAMIC, "True");
        } else {
            return new BlockTag("Is Hidden", BlockType.PROCESS, ActionType.DYNAMIC, "False");
        }

    }

    public static ArgsTable functionParams(CarbonDFParser.StartdefContext functionContext, boolean isHidden){

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
            else if (typeContext.TA_ANY() != null) type = ArgType.ANY;
            else if (typeContext.TA_NUM() != null) type = ArgType.NUM;
            else type = ArgType.ANY;


            args.addAtFirstNull(new FunctionParam(paramContext.SAFE_TEXT().getText(), type));
        }

        return args;
    }

    public static ArgsTable processParams(boolean isHidden){
        ArgsTable args = new ArgsTable();

        args.set(26, getHiddenTag(isHidden));
        return args;
    }


}
