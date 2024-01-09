package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.args.ArgType;
import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.args.FunctionParam;
import me.gamenu.carbon.logic.blocks.*;
import me.gamenu.carbon.logic.exceptions.BaseCarbonException;
import me.gamenu.carbon.logic.exceptions.UnrecognizedTokenException;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.util.ArrayList;

public class Translator {
    public static ArrayList<BlocksTable> translate(CarbonDFParser.BaseContext base) throws BaseCarbonException {
        ArrayList<BlocksTable> tableList = new ArrayList<>();

        for (CarbonDFParser.StartdefContext startdef : base.startdef()) {
            tableList.add(singleDefinitionTable(startdef));
        }

        return tableList;
    }

    public static BlocksTable singleDefinitionTable(CarbonDFParser.StartdefContext context) throws BaseCarbonException {
        BlocksTable table = new BlocksTable();
        CodeBlock newBlock;
        if (context.def_keyword().FUNDEF_KEYWORD() != null){
            newBlock = new DefinitionBlock(BlockType.FUNC, null, context.def_name().getText());
            newBlock.setArgs(functionParams(context));

        } else if (context.def_keyword().PROCDEF_KEYWORD() != null) {
            newBlock = new DefinitionBlock(BlockType.PROCESS, null, context.def_name().getText());
        } else if (context.def_keyword().EVENTDEF_KEYWORD() != null) {
            newBlock = EventBlock.fromID(context.def_name().getText());
        } else {
            throw new UnrecognizedTokenException(context.def_keyword().getText());
        }

        table.addBlock(newBlock);
        return table;
    }

    public static ArgsTable functionParams(CarbonDFParser.StartdefContext functionContext){

        ArgsTable args = new ArgsTable();
        if (functionContext.def_params() == null) return args;

        for (CarbonDFParser.Def_paramContext paramContext :
                functionContext.def_params().def_param()) {
            ArgType type;

            CarbonDFParser.Type_annotationsContext typeContext = paramContext.type_annotations();
            if (typeContext == null) type = ArgType.ANY;
            else if (typeContext.TA_ANY() != null) type = ArgType.ANY;
            else if (typeContext.TA_NUM() != null) type = ArgType.NUM;
            else type = ArgType.ANY;

            args.add(new FunctionParam(paramContext.SAFE_TEXT().getText(), type));
        }
        return args;
    }
}
