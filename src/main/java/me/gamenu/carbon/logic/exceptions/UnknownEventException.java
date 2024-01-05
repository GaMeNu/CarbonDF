package me.gamenu.carbon.logic.exceptions;

public class UnknownEventException extends Exception{

    public UnknownEventException(String eventID){
        super("Event \"" + eventID + "\" does not exist");
    }

}
