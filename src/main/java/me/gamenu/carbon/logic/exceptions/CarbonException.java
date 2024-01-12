package me.gamenu.carbon.logic.exceptions;

public class CarbonException extends BaseCarbonException{
    public CarbonException(String message) {
        super(message);
    }

    public CarbonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarbonException(Throwable cause) {
        super(cause);
    }

    public CarbonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
