package me.gamenu.carbon.logic.exceptions;

import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ErrorListener extends BaseErrorListener {
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static final ErrorListener INSTANCE = new ErrorListener();

    private boolean hasError;

    public ErrorListener(){
        super();
        hasError = false;
    }

    public boolean hasError() {
        return hasError;
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
            stringBuilder.append("  Offending token: \"").append(e.getOffendingToken().getText()).append("\"\n");
        }
        if (e != null && e.getOffendingState() >= 0 && e.getExpectedTokens() != null && e.getCtx() instanceof ParserRuleContext){
            stringBuilder.append("  Expected tokens: ");
            stringBuilder.append(e.getExpectedTokens().toString(recognizer.getVocabulary())).append("\n");
        }
        stringBuilder.append("  line ").append(line).append(":").append(charPositionInLine).append(" at \"");
        String text = parser.getTokenStream().getTokenSource().getInputStream().toString().split("\n", -1)[line-1].trim();
        stringBuilder.append(text).append("\"");

        hasError = true;
        if (e instanceof CarbonTranspileException)
            handleSeverity((CarbonTranspileException) e, stringBuilder);
    }

    private void handleSeverity(CarbonTranspileException e, StringBuilder stringBuilder){
        int level = e.getSeverity().getLevel();
        if (level >= CarbonTranspileException.Severity.FATAL.getLevel())
            throw new ParseCancellationException(e);
        if (level >= CarbonTranspileException.Severity.ERROR.getLevel()){
            System.err.println(stringBuilder);
            return;
        }
        if (level >= CarbonTranspileException.Severity.WARN.getLevel()){
            stringBuilder.insert(0, ANSI_YELLOW + "WARNING: ").append(ANSI_RESET);
            System.err.println(stringBuilder);
            return;
        }
    }



}
