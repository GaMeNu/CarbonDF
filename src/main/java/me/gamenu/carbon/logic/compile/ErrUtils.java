package me.gamenu.carbon.logic.compile;

import org.antlr.v4.runtime.*;

import java.lang.reflect.InvocationTargetException;

public class ErrUtils {
    public static void throwError(String msg, Parser parser, ParserRuleContext ctx, Class<? extends RecognitionException> errClass){
        RecognitionException err;

        try {
            err = errClass.getConstructor(Recognizer.class, IntStream.class, ParserRuleContext.class).newInstance(parser, parser.getInputStream(), ctx);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        parser.notifyErrorListeners(ctx.getStart(), msg, err);
    }
}
