package me.gamenu.carbon.logic.blocks;

public enum ActionType {

    // IDs are UNIQUE! Make sure to not fuck it up!
    NULL(null, null, null),
    EVENT_JOIN("Join", "Join", BlockType.EVENT_PLAYER),
    EVENT_ENTITY_DAMAGE_ENTITY("EntityDmgEntity", "EntityDmgEntity", BlockType.EVENT_ENTITY),
    DYNAMIC("dynamic","null", null),
    SEND_MESSAGE("SendMessage", "sendMessage", BlockType.PLAYER_ACTION);


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
