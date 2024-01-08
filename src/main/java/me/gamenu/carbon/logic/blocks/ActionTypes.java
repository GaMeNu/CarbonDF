package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.etc.hasTypeID;

public class ActionTypes {
    public enum Type implements hasTypeID {
        NULL(null),
        EVENT_JOIN("Join"),
        EVENT_ENTITY_DAMAGE_ENTITY("EntityDmgEntity");

        private final String id;

        Type(String id){
            this.id = id;
        }

        public String getID() {
            return id;
        }
    }

}
