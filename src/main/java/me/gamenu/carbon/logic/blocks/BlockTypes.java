package me.gamenu.carbon.logic.blocks;

import java.util.HashMap;

public class BlockTypes {
    public enum Type {
        FUNC,
        PROC
    }

    private static final HashMap<Type, String> typeMap = new HashMap<>() {{
        put(Type.FUNC, "func");
        put(Type.PROC, "proc");
    }};

    public static String typeID(Type type) {
        return typeMap.get(type);
    }
}
