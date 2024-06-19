package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
import org.antlr.v4.runtime.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ErrUtils {
    public static void throwError(String msg, Parser parser, ParserRuleContext ctx, Class<? extends RecognitionException> errClass, CarbonTranspileException.Severity severity){
        RecognitionException err;

        try {
            if (errClass.isAssignableFrom(CarbonTranspileException.class)) {
                err = errClass.getConstructor(Recognizer.class, IntStream.class, ParserRuleContext.class, CarbonTranspileException.Severity.class).newInstance(parser, parser.getInputStream(), ctx, severity);
            }
            else
                err = errClass.getConstructor(Recognizer.class, IntStream.class, ParserRuleContext.class).newInstance(parser, parser.getInputStream(), ctx);


        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        parser.notifyErrorListeners(ctx.getStart(), msg, err);
    }
}
