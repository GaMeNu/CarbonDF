package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.blocks.BlockTypes;
import me.gamenu.carbon.logic.blocks.BlocksTable;
import me.gamenu.carbon.logic.blocks.DefinitionBlock;
import me.gamenu.carbon.logic.blocks.EventBlock;
import me.gamenu.carbon.parser.CarbonDFLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.io.*;

public class Compile {

    private static CarbonDFParser fileToBaseParser(String input){
        InputStream inputStream;
        CarbonDFLexer programLexer;
        File file = new File(input);
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            programLexer = new CarbonDFLexer(CharStreams.fromStream(inputStream));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CommonTokenStream tokens = new CommonTokenStream(programLexer);
        return new CarbonDFParser(tokens);
    }

    public static BlocksTable singleDefinitionTable(CarbonDFParser.StartdefContext context){
        BlocksTable table = new BlocksTable();
        if (context.def_keyword().FUNDEF_KEYWORD() != null){
            table.addBlock(new DefinitionBlock(BlockTypes.Type.FUNC, null, context.def_name().getText()));
        } else if (context.def_keyword().PROCDEF_KEYWORD() != null) {
            table.addBlock(new DefinitionBlock(BlockTypes.Type.PROCESS, null, context.def_name().getText()));
        } else if (context.def_keyword().EVENTDEF_KEYWORD() != null) {
            table.addBlock(EventBlock.fromID(context.def_name().getText()));
        }

        return table;
    }

    public static void compiledTable(BlocksTable table){

        System.out.println(table.toJSON().toString());
        byte[] compressed;

        try {
            compressed = GZipUtils.compress(table.toJSON().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(GZipUtils.bytesToString(compressed));
    }

    public static void fromFile(String filepath){
        CarbonDFParser parser = fileToBaseParser(filepath);
        CarbonDFParser.BaseContext base = parser.base();

        for (CarbonDFParser.StartdefContext startdef : base.startdef()) {
            compiledTable(singleDefinitionTable(startdef));
        }
    }
}
