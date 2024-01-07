package me.gamenu.carbon.logic.exceptions;


public class UnknownEventException extends BaseCarbonException{

    public UnknownEventException(String eventID){
        super("Event \"" + eventID + "\" does not exist");
    }

}
