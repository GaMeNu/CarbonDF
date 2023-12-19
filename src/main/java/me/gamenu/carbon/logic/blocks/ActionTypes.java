package me.gamenu.carbon.logic.blocks;

import java.util.HashMap;

public class ActionTypes {
    public enum Type {
        FUNC
    }

    public static final HashMap<Type, String> typeMap = new HashMap<>(){{
        typeMap.put(Type.FUNC, null);
    }};

    public static String typeID(ActionTypes.Type type) {
        return typeMap.get(type);
    }

}
