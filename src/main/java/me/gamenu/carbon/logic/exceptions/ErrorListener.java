package me.gamenu.carbon.logic.exceptions;

import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ErrorListener extends BaseErrorListener {

    private static final int ANSI_WHITE = 97;
    private static final int ANSI_RED = 31;
    private static final int ANSI_YELLOW = 93;
    private static final int ANSI_RESET = 0;
    private static final int ANSI_BOLD = 1;

    private static String ANSICode(int... args){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++){
            stringBuilder.append(args[i]);
            if (i < args.length - 1) stringBuilder.append(";");
        }

        return "\u001B[" + stringBuilder + "m";
    }
    private static final String INDENT = "  ";

    public static final ErrorListener INSTANCE = new ErrorListener();

    private boolean hasError;
    private boolean printStackTrace;

    public ErrorListener(){
        super();
        hasError = false;
    }

    public boolean hasError() {
        return hasError;
    }

    public ErrorListener setPrintStackTrace(boolean printStackTrace) {
        this.printStackTrace = printStackTrace;
        return this;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        CarbonDFParser parser = (CarbonDFParser) recognizer;
        StringBuilder stringBuilder = new StringBuilder();
        if (e != null)
            stringBuilder.append(e.getClass().getSimpleName()).append(": ").append(msg).append("\n");
        else
            stringBuilder.append(msg).append("\n");
        if (e != null && e.getOffendingToken() != null){
            stringBuilder.append(INDENT).append("Offending token: \"").append(e.getOffendingToken().getText()).append("\"\n");
        }
        if (e != null && e.getOffendingState() >= 0 && e.getExpectedTokens() != null && e.getCtx() instanceof ParserRuleContext){
            stringBuilder.append(INDENT).append("Expected tokens: ");
            stringBuilder.append(e.getExpectedTokens().toString(recognizer.getVocabulary())).append("\n");
        }

        String lineStr = "line " + line + ":" + charPositionInLine + ": ";

        String text = parser.getTokenStream().getTokenSource().getInputStream().toString().split("\n", -1)[line-1];
        String textTrim = text.trim();

        int errStartPointer = charPositionInLine - (text.length() - textTrim.length() ) + 1;
        int errDispPointer = lineStr.length() + errStartPointer;

        int errEndPointer = -1;
        if (offendingSymbol instanceof CommonToken) {
            errEndPointer = errStartPointer + ((CommonToken) offendingSymbol).getText().length();
        } else if (e != null && e.getOffendingToken() != null) {
            errEndPointer = errStartPointer + e.getOffendingToken().getText().length();
        } else if (e != null) {
            errEndPointer = errStartPointer = e.getCtx().getText().length();
        }

        String textFmt;
        if (errEndPointer != -1) try {
            textFmt = ANSICode(ANSI_WHITE, ANSI_BOLD) + textTrim.substring(0, errStartPointer) + ANSICode(ANSI_RED, ANSI_BOLD) + textTrim.substring(errStartPointer, errEndPointer) + ANSICode(ANSI_WHITE, ANSI_BOLD) + textTrim.substring(errEndPointer) + ANSICode(ANSI_RESET);
        } catch (Exception err) {
            textFmt = ANSICode(ANSI_WHITE, ANSI_BOLD) + textTrim + ANSICode(ANSI_RESET);
        }
        else textFmt = ANSICode(ANSI_WHITE, ANSI_BOLD) + textTrim + ANSICode(ANSI_RESET);

        stringBuilder.append(INDENT).append(ANSICode(ANSI_WHITE)).append(lineStr);
        stringBuilder.append(textFmt).append("\n");
        stringBuilder.append(INDENT).append(" ".repeat(errDispPointer)).append("^ Here").append("\n");

        if (e instanceof CarbonTranspileException) {
            if (((CarbonTranspileException) e).getSeverity().getLevel() >= CarbonTranspileException.Severity.ERROR.getLevel())
                hasError = true;
            handleSeverity((CarbonTranspileException) e, stringBuilder);
        } else {
            hasError = true;
            System.err.println(stringBuilder);
            if (printStackTrace) {
                for (StackTraceElement stElement: e.getStackTrace())
                    System.err.println(stElement);
                System.err.println();
            }
        }
    }

    private void handleSeverity(CarbonTranspileException e, StringBuilder stringBuilder){
        int level = e.getSeverity().getLevel();
        if (level >= CarbonTranspileException.Severity.FATAL.getLevel())
            throw new ParseCancellationException(e);
        if (level >= CarbonTranspileException.Severity.ERROR.getLevel()){
            System.err.println(stringBuilder);
            if (printStackTrace) {
                for (StackTraceElement stElement: e.getStackTrace())
                    System.err.println(stElement);
                System.err.println();
            }
            return;
        }
        if (level >= CarbonTranspileException.Severity.WARN.getLevel()){
            stringBuilder.insert(0, ANSICode(ANSI_YELLOW) + "WARNING: ").append(ANSICode(ANSI_RESET));
            System.err.println(stringBuilder);
            if (printStackTrace) {
                for (StackTraceElement stElement: e.getStackTrace())
                    System.err.println(stElement);
                System.err.println();
            }
            return;
        }
    }



}
