package me.gamenu.carbon.logic.exceptions;

public abstract class BaseCarbonException extends Exception{

    public BaseCarbonException(String message) {
        super(message);
    }

    public BaseCarbonException(String message, Throwable cause){
        super(message, cause);
    }

    public BaseCarbonException(Throwable cause){
        super(cause);
    }

    public BaseCarbonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
