package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.*;

import java.util.HashMap;

public enum ActionType {

    // IDs are UNIQUE! Make sure to not fuck it up!
    NULL(null, null, null),
    EVENT_JOIN("Join", "Join", BlockType.EVENT_PLAYER),
    EVENT_LEFT_CLICK("LeftClick", "LeftClick", BlockType.EVENT_PLAYER, true),
    EVENT_ENTITY_DAMAGE_ENTITY("EntityDmgEntity", "EntityDmgEntity", BlockType.EVENT_ENTITY),
    DYNAMIC_FUNC("dynamic", null, BlockType.FUNC),
    DYNAMIC_CALL_FUNC("dynamic", null, BlockType.CALL_FUNC),
    DYNAMIC_PROC("dynamic",null, BlockType.PROCESS),
    DYNAMIC_START_PROC("dynamic", null, BlockType.START_PROCESS),
    SEND_MESSAGE("SendMessage", "sendMessage", BlockType.PLAYER_ACTION),
    SIMPLE_ASSIGN("=", "assign", BlockType.SET_VARIABLE),
    CREATE_LIST("CreateList", "createList", BlockType.SET_VARIABLE),
    CREATE_DICT("CreateDict", "createDict", BlockType.SET_VARIABLE),
    ADD_NUMBERS("+", "add", BlockType.SET_VARIABLE),
    SUBTRACT_NUMBERS("-", "sub", BlockType.SET_VARIABLE),
    MULTIPLY_NUMBERS("x", "mult", BlockType.SET_VARIABLE),
    DIVIDE_NUMBERS("/", "div", BlockType.SET_VARIABLE),
    SELECT_RANDOM_PLAYER("RandomPlayer", "randomPlayer", BlockType.SELECT_OBJECT),
    IF_EQUALS("=", "equals", BlockType.IF_VARIABLE),
    REPEAT_FOREVER("Forever", "forever", BlockType.REPEAT),
    REPEAT_WHILE("While", "while", BlockType.REPEAT),
    REPEAT_MULTIPLE("Multiple", "multiple", BlockType.REPEAT),
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
    private final boolean cancellable;

    ActionType(String id, String codeName, BlockType blockType){
        this(id, codeName, blockType, false);
    }
    ActionType(String id, String codeName, BlockType blockType, boolean cancellable){
        this.id = id;
        this.codeName = codeName;
        this.blockType = blockType;
        this.cancellable = cancellable;
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
    public static ActionType fromID(String id, BlockType bt) {
        for (ActionType at : ActionType.values()) {
            if (at.getID() == null) continue;
            if (at.getID().equals(id) && at.getBlock() == bt) return at;
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

    /**
     * This here bc am dumb.
     * @param type
     * @return
     */
    public static ActionType getMatchingDynamicAction(BlockType type){
        for (ActionType at: ActionType.values()) {
            if (at.getID() == null || at.getBlock() == null) continue;
            if (at.getID().equals("dynamic") && at.getBlock().equals(type)) return at;
        }
        return null;
    }

    public boolean isCancellable() {
        return cancellable;
    }
}
