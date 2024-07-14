package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.CarbonTypeEnum;

public enum ArgType implements CarbonTypeEnum {
    ANY("any"),
    PARAM("pn_el"),
    VAR("var"),
    GAME_VALUE("g_val"),

    STRING("txt"),
    STYLED_TEXT("comp"),
    NUM("num"),

    LIST("list"),
    DICT("dict"),

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

    public static ArgType fromID(String id){
        for (ArgType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return null;
    }
}