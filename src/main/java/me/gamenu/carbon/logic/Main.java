package me.gamenu.carbon.logic;
import me.gamenu.carbon.logic.blocks.CarbonFunction;
import me.gamenu.carbon.logic.compile.Compile;
import parser.CarbonDFLexer;
import parser.CarbonDFParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.*;

public class Main {
    public static CarbonDFParser fileToBaseParser(String input){
        InputStream inputStream;
        parser.CarbonDFLexer programLexer;
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

    public static void main(String[] args) {
        CarbonDFParser parser = fileToBaseParser("src/main/java/me/gamenu/carbon/logic/test.cadf");
        CarbonFunction fn = new CarbonFunction(parser.base().startdef().def_name().getText());
        Compile.toJson(fn);
    }
}
