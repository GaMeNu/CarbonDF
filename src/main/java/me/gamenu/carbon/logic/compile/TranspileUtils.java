package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.args.ArgType;
import me.gamenu.carbon.logic.exceptions.UnknownSymbolException;
import me.gamenu.carbon.parser.CarbonDFParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import static me.gamenu.carbon.parser.CarbonDFLexer.*;

public class TranspileUtils {
    public static ArgType annotationToArgType(CarbonDFParser.Type_annotationsContext ctx) throws UnknownSymbolException {
        return switch (((TerminalNode) ctx.getChild(0)).getSymbol().getType()) {
            case TA_ANY -> ArgType.ANY;
            case TA_VAR -> ArgType.VAR;
            case TA_NUM -> ArgType.NUM;
            case TA_STRING -> ArgType.STRING;
            case TA_ST -> ArgType.STYLED_TEXT;
            default -> throw new UnknownSymbolException(ctx.getText());
        };
    }
}
