package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.blocks.BlockTypes;
import me.gamenu.carbon.logic.blocks.BlocksTable;
import me.gamenu.carbon.logic.blocks.CodeBlock;
import me.gamenu.carbon.logic.blocks.CodeBlockLineStarter;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.CarbonDFParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Compile {

    private static parser.CarbonDFParser fileToBaseParser(String input){
        InputStream inputStream;
        parser.CarbonDFLexer programLexer;
        File file = new File(input);
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            programLexer = new parser.CarbonDFLexer(CharStreams.fromStream(inputStream));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CommonTokenStream tokens = new CommonTokenStream(programLexer);
        return new parser.CarbonDFParser(tokens);

    }

    public static void fromFile(String filepath){
        parser.CarbonDFParser parser = fileToBaseParser(filepath);
        CarbonDFParser.BaseContext base = parser.base();
        BlocksTable table = new BlocksTable();
        ;

        if (base.startdef().def_keyword().FUNDEF_KEYWORD() != null){
            table.addBlock(new CodeBlockLineStarter(BlockTypes.Type.FUNC, null, base.startdef().def_name().getText()));
        } else if (base.startdef().def_keyword().PROCDEF_KEYWORD() != null) {
            table.addBlock(new CodeBlockLineStarter(BlockTypes.Type.PROC, null, base.startdef().def_name().getText()));
        }

        try {
            System.out.println(table.toJSON().toString());
            byte[] compressed = GZipUtils.compress(table.toJSON().toString());
            System.out.println(GZipUtils.bytesToString(compressed));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // H4sIAAAAAAAAA6tWSsrJT84uVrKKrlZKLEoHMqprdZRSEksSlayUSlKLS5R0IEqA3LTSvGQgNzMFyIaI1cbWAgCihkiGQgAAAA==
    }
}
