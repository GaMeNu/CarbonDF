package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.hasTypeID;

public enum ArgType implements hasTypeID {
    ANY("any"),
    PARAM("pn_el"),
    VAR("var"),
    NUM("num");

    private final String id;

    ArgType(String id){
        this.id = id;
    }

    @Override
    public String getID() {
        return id;
    }
}