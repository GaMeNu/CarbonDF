package me.gamenu.carbon.logic.exceptions;

import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class CarbonTranspileException extends RecognitionException {

    public enum Severity {

        WARN(100),
        ERROR(500),
        FATAL(800);

        final int level;

        Severity(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    Severity severity;


    public CarbonTranspileException(Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx) {
        super(recognizer, input, ctx);
        this.severity = Severity.ERROR;
    }

    public CarbonTranspileException(Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx, Severity severity){
        super(recognizer, input, ctx);
        this.severity = severity;
    }

    public CarbonTranspileException(String message, Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx) {
        super(message, recognizer, input, ctx);
        this.severity = Severity.ERROR;
    }
    public CarbonTranspileException(String message, Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx, Severity severity) {
        super(message, recognizer, input, ctx);
        this.severity = severity;
    }

    public Severity getSeverity() {
        return severity;
    }
}
