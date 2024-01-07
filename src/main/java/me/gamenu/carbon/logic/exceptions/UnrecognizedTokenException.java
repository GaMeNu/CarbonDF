package me.gamenu.carbon.logic.exceptions;

public class UnrecognizedTokenException extends BaseCarbonException{

    public UnrecognizedTokenException(String token){
        super ("Unrecognized token \"" + token + "\"");
    }
}
