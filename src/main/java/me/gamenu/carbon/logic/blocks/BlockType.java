package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.etc.CarbonTypeEnum;

public enum BlockType implements CarbonTypeEnum {
    FUNC("func", "fun"),
    PROCESS("process", "proc"),
    EVENT_PLAYER("event", "event"),
    EVENT_ENTITY("entity_event", "event"),
    CALL_FUNC("call_func", "CallFunction"),
    START_PROCESS("start_process", "StartProcess"),
    PLAYER_ACTION("player_action", "PlayerAction"),
    SET_VARIABLE("set_var", "SetVariable")
    ;

    private final String id;

    private final String codeName;

    BlockType(String id, String codeName) {
        this.id = id;
        this.codeName = codeName;
    }

    public String getID() {
        return id;
    }

    public String getCodeName() {
        return codeName;
    }

    public BlockType getFromCodeName(String codeName){
        for (BlockType bt :
                BlockType.values()) {
            if (bt.getCodeName().equals(codeName)) return bt;
        }

        return null;
    }
}
