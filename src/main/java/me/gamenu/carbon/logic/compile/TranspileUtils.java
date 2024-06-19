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

    public static ArgType anyItemToArgType(CarbonDFParser.Any_itemContext ctx){
        if (ctx.standalone_item() != null) return standaloneToArgType(ctx.standalone_item());
        else if (ctx.complex_item() != null) return complexToArgType(ctx.complex_item());
        return null;
    }

    public static ArgType standaloneToArgType(CarbonDFParser.Standalone_itemContext ctx){
        if (ctx.simple_item().number() != null) return ArgType.NUM;
        if (ctx.simple_item().string() != null) return ArgType.STRING;
        if (ctx.simple_item().styled_text() != null) return ArgType.STYLED_TEXT;
        return null;
    }

    public static ArgType complexToArgType(CarbonDFParser.Complex_itemContext ctx){
        if (ctx.list() != null) return ArgType.LIST;
        if (ctx.dict() != null) return ArgType.DICT;
        return null;
    }

}
