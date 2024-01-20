package me.gamenu.carbon.logic.exceptions;

import me.gamenu.carbon.logic.args.ArgType;

public class TypeMismatchException extends BaseCarbonException{

    public TypeMismatchException(ArgType type1, ArgType type2){
        super("Type mismatch between 2 types: \"" + type1 + "\" and \""+ type2 +"\"");
    }

    public TypeMismatchException(String message) {
        super(message);
    }

    public TypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeMismatchException(Throwable cause) {
        super(cause);
    }

    public TypeMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
