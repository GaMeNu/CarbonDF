package me.gamenu.carbon.logic.exceptions;

import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Recognizer;

public class UnknownEnumValueException extends InvalidNameException {
    public UnknownEnumValueException(Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx) {
        super(recognizer, input, ctx);
    }

    public UnknownEnumValueException(String message, Recognizer<?, ?> recognizer, IntStream input, ParserRuleContext ctx) {
        super(message, recognizer, input, ctx);
    }
}
