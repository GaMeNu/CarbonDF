package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.CarbonTypeEnum;

public enum ArgType implements CarbonTypeEnum {
    ANY("any"),
    PARAM("pn_el"),
    VAR("var"),
    STRING("txt"),
    NUM("num"),

    HINT("hint"),
    TAG("bl_tag");

    private final String id;

    ArgType(String id){
        this.id = id;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getCodeName() {
        return id;
    }
}