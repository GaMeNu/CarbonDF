package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class ErrorListener extends BaseErrorListener {
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

        System.err.println(stringBuilder);
        // if (e == null)
            // throw new ParseCancellationException(stringBuilder.toString());
    }



}
