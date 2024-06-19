package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.compile.ErrUtils;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.parser.CarbonDFParserBaseListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;

public class BaseCarbonListener extends CarbonDFParserBaseListener {
    Parser parser;
    ProgramContext programContext;
    public BaseCarbonListener(ProgramContext programContext){
        this.programContext = programContext;
        this.parser = programContext.getParser();
    }

    protected void throwError(String msg, ParserRuleContext ctx, Class<? extends RecognitionException> errClass){
        ErrUtils.throwError(msg, parser, ctx, errClass);
    }

    void throwError(String msg, Class<? extends RecognitionException> errClass){
        throwError(msg, parser.getContext(), errClass);
    }

}
