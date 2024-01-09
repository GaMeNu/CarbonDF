package me.gamenu.carbon.logic.blocks;

public enum ActionType {
    NULL(null),
    EVENT_JOIN("Join"),
    EVENT_ENTITY_DAMAGE_ENTITY("EntityDmgEntity");

    private final String id;

    ActionType(String id){
        this.id = id;
    }

    public String getID() {
        return id;
    }

}
