package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.blocks.BlocksTable;
import me.gamenu.carbon.logic.exceptions.BaseCarbonException;
import me.gamenu.carbon.logic.listeners.ProgramBaseListener;
import me.gamenu.carbon.parser.CarbonDFLexer;
import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.*;
import java.util.ArrayList;

public class Compile {

    /**
     * Gets a file path and opens the file, lets ANTLR parse it, and returns the Parser
     * @param input input filepath
     * @return CarbonDFParser
     */
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

    /**
     * Takes in a single BlocksTable and compiles it to JSON, then GZips the result.
     * @param table BlocksTable
     * @return String
     */
    public static String compiledTable(BlocksTable table){

        System.out.println(table.toJSON().toString());
        byte[] compressed;

        try {
            compressed = GZipUtils.compress(table.toJSON().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return GZipUtils.bytesToString(compressed);
    }

    /**
     * Takes an input filepath and compiles the entire file.
     * @param filepath String
     */
    public static void fromFile(String filepath){
        CarbonDFParser parser = fileToBaseParser(filepath);
        parser.removeErrorListeners();
        parser.addErrorListener(ErrorListener.INSTANCE);

        ProgramContext programContext = new ProgramContext().setParser(parser);

        ProgramBaseListener programBaseListener = new ProgramBaseListener(programContext);
        CarbonDFParser.BaseContext base;

        ArrayList<BlocksTable> tableList;

        try {
            base = parser.base();
            base.enterRule(programBaseListener);
        } catch (ParseCancellationException e){
            throw e;
            // return;
        } catch (BaseCarbonException e){
            System.err.println(e.getMessage());
            return;
        }

        if (ErrorListener.INSTANCE.hasError()) return;

        tableList = programBaseListener.getTableList();

        for (BlocksTable table : tableList){
            System.out.println(compiledTable(table));
        }
    }
}
