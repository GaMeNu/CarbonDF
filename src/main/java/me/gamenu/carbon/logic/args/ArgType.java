package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.CarbonTypeEnum;

import java.util.HashMap;

public enum ArgType implements CarbonTypeEnum {
    ANY("any"),
    PARAM("pn_el"),
    VAR("var"),
    GAME_VALUE("g_val"),

    STRING("txt"),
    STYLED_TEXT("comp"),
    NUM("num"),

    VECTOR("vec"),
    LOCATION("loc"),

    LIST("list"),
    DICT("dict"),

    ITEM("item"),

    HINT("hint"),
    TAG("bl_tag");

    private final String id;

    private static final HashMap<String, ArgType> stringToArgType = new HashMap<>(){{
        put("NUMBER", NUM);
        put("TEXT", STRING);
        put("COMPONENT", STYLED_TEXT);
        put("LOCATION", LOCATION);
        put("VECTOR", VECTOR);
        put("ITEM", ITEM);
        put("LIST", LIST);
        put("DICT", DICT);
    }};

    public static ArgType fromStringName(String name){
        return stringToArgType.get(name);
    }

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