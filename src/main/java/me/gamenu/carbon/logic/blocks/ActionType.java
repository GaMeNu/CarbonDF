package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.ArgType;
import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.args.CodeArg;

import java.util.HashMap;

public enum ActionType {

    // IDs are UNIQUE! Make sure to not fuck it up!
    NULL(null, null, null),
    EVENT_JOIN("Join", "Join", BlockType.EVENT_PLAYER),
    EVENT_ENTITY_DAMAGE_ENTITY("EntityDmgEntity", "EntityDmgEntity", BlockType.EVENT_ENTITY),
    DYNAMIC("dynamic","null", null),
    SEND_MESSAGE("SendMessage", "sendMessage", BlockType.PLAYER_ACTION),
    SIMPLE_ASSIGN("=", "assign", BlockType.SET_VARIABLE),
    ADD_NUMBERS("+", "add", BlockType.SET_VARIABLE),
    SUBTRACT_NUMBERS("-", "sub", BlockType.SET_VARIABLE),
    MULTIPLY_NUMBERS("x", "mult", BlockType.SET_VARIABLE),
    DIVIDE_NUMBERS("/", "div", BlockType.SET_VARIABLE),
    RETURN("Return", "return", BlockType.CONTROL)
    ;

    private static final HashMap<String, ArgsTable> baseFunsReturns = new HashMap<>() {{
        put("add", new ArgsTable().addAtFirstNull(new CodeArg(ArgType.NUM)));
        put("sub", new ArgsTable().addAtFirstNull(new CodeArg(ArgType.NUM)));
        put("mult", new ArgsTable().addAtFirstNull(new CodeArg(ArgType.NUM)));
        put("div", new ArgsTable().addAtFirstNull(new CodeArg(ArgType.NUM)));
    }};

    public static HashMap<String, ArgsTable> getBaseFunsReturns() {
        return baseFunsReturns;
    }

    private final String id;
    private final String codeName;
    private final BlockType blockType;

    ActionType(String id, String codeName, BlockType blockType){
        this.id = id;
        this.codeName = codeName;
        this.blockType = blockType;
    }

    public String getID() {
        return id;
    }

    public String getCodeName(){
        return codeName;
    }

    public static ActionType fromCodeName(String codeName) {
        for (ActionType at :
                ActionType.values()) {
            if (at.getCodeName() == null) continue;
            if (at.getCodeName().equals(codeName)) return at;
        }
        return null;
    }

    public BlockType getBlock() {
        return blockType;
    }

    public boolean equals(ActionType other){
        // We ASSUME that future maintainers aren't dumb
        // and understand that IDs are *UNIQUE*
        return this.id.equals(other.id);
    }

}
