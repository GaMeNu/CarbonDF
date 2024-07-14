package me.gamenu.carbon.logic.exceptions;

import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Recognizer;

public class TypeException extends CarbonTranspileException{
    public TypeException(Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx) {
        super(recognizer, input, ctx);
    }

    public TypeException(Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx, Severity severity) {
        super(recognizer, input, ctx, severity);
    }

    public TypeException(String message, Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx) {
        super(message, recognizer, input, ctx);
    }

    public TypeException(String message, Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx, Severity severity) {
        super(message, recognizer, input, ctx, severity);
    }
}
