package me.gamenu.carbon.logic;
import me.gamenu.carbon.logic.compile.Compile;
import parser.CarbonDFLexer;
import parser.CarbonDFParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        Compile.fromFile("src/main/java/me/gamenu/carbon/logic/test.df");
    }
}
