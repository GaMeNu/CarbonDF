package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.blocks.BlockTypes;
import me.gamenu.carbon.logic.blocks.BlocksTable;
import me.gamenu.carbon.logic.blocks.CodeBlockLineStarter;
import me.gamenu.carbon.parser.CarbonDFLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.io.*;
import java.util.zip.ZipException;

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
            table.addBlock(new CodeBlockLineStarter(BlockTypes.Type.FUNC, null, context.def_name().getText()));
        } else if (context.def_keyword().PROCDEF_KEYWORD() != null) {
            table.addBlock(new CodeBlockLineStarter(BlockTypes.Type.PROCESS, null, context.def_name().getText()));
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

        System.out.println(base.startdef().getText());



        try {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // H4sIAAAAAAAAA6tWSsrJT84uVrKKrlZKLEoHMqprdZRSEksSlayUSlKLS5R0IEqA3LTSvGQgNzMFyIaI1cbWAgCihkiGQgAAAA==
    }
}
