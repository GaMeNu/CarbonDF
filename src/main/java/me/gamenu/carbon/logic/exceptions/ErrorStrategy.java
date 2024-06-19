package me.gamenu.carbon.logic.exceptions;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;

public class ErrorStrategy extends BailErrorStrategy {

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        return super.recoverInline(recognizer);
    }

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        super.recover(recognizer, e);
    }

    @Override
    protected IntervalSet getErrorRecoverySet(Parser recognizer) {
        return super.getErrorRecoverySet(recognizer);
    }
}
