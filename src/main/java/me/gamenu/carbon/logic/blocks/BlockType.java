package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.etc.hasTypeID;

public enum BlockType implements hasTypeID {
    FUNC("func"),
    PROCESS("process"),
    EVENT_PLAYER("event"),
    EVENT_ENTITY("entity_event");

    private final String id;

    BlockType(String id) {
        this.id =id;
    }

    public String getID() {
        return id;
    }
}
