package me.gamenu.carbon.logic.blocks;

import java.util.HashMap;

public class ActionTypes {
    public enum Type {
        NULL,
        EVENT_JOIN,
        EVENT_ENTITY_DAMAGE_ENTITY
    }

    public static final HashMap<Type, String> typeMap = new HashMap<>(){{
        put(Type.NULL, null);
        put(Type.EVENT_JOIN, "Join");
        put(Type.EVENT_ENTITY_DAMAGE_ENTITY, "EntityDmgEntity");
    }};

    public static String typeID(ActionTypes.Type type) {
        return typeMap.get(type);
    }

}
