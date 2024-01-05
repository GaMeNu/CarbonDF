package me.gamenu.carbon.logic.blocks;

import java.util.HashMap;

public class BlockTypes {
    public enum Type {
        FUNC,
        PROCESS,
        EVENT_PLAYER,
        EVENT_ENTITY
    }

    private static final HashMap<Type, String> typeMap = new HashMap<>() {{
        put(Type.FUNC, "func");
        put(Type.PROCESS, "process");
        put(Type.EVENT_PLAYER, "event");
        put(Type.EVENT_ENTITY, "entity_event");
    }};

    public static String typeID(Type type) {
        return typeMap.get(type);
    }
}
