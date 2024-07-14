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
    ENTITY_ACTION("entity_action", "EntityAction"),
    SET_VARIABLE("set_var", "SetVariable"),
    IF_VARIABLE("if_var", "IfVariable"),
    IF_GAME("if_game", "IfGame"),
    IF_PLAYER("if_player", "IfPlayer"),
    IF_ENTITY("if_entity", "IfEntity"),
    ELSE("else", "Else"),
    REPEAT("repeat","Repeat"),
    BRACKET("bracket", null),
    CONTROL("control", "Control")
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

    public static BlockType fromCodeName(String codeName){
        for (BlockType bt : BlockType.values()) {
            if (bt.getCodeName().equals(codeName)) return bt;
        }

        return null;
    }
}
