package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.compile.ErrUtils;
import me.gamenu.carbon.logic.compile.ProgramContext;
import me.gamenu.carbon.logic.exceptions.CarbonTranspileException;
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

    void throwError(String msg, ParserRuleContext ctx, Class<? extends RecognitionException> errClass){
        throwError(msg, ctx, errClass, CarbonTranspileException.Severity.ERROR);
    }

    void throwError(String msg,
                    ParserRuleContext ctx,
                    Class<? extends RecognitionException> errClass,
                    CarbonTranspileException.Severity severity){
        ErrUtils.throwError(msg, parser, ctx, errClass, severity);
    }

}
