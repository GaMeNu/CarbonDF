package me.gamenu.carbon.logic;
import parser.CarbonDFLexer;
import parser.CarbonDFParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.*;

public class Main {
    public static void fileToBaseParser(String input){
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
        CarbonDFParser parser = new CarbonDFParser(tokens);

    }

    public static void main(String[] args) {
        fileToBaseParser("src/main/java/me/gamenu/carbon/logic/test.cadf");
    }
}
