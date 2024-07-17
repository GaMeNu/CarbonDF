package me.gamenu.carbon.logic.blocks;

import org.json.JSONObject;

public class Bracket extends CodeBlock{

    public enum Direction{
        OPEN("open"),
        CLOSE("close")
        ;

        private String id;
        Direction(String id){
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public enum Type {
        NORMAL("norm"),
        REPEAT("repeat")
        ;
        private String id;

        Type(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    Direction bracketDirection;
    Type bracketType;

    public Bracket(Direction direction, Type type) {
        super(BlockType.fromID("bracket"), ActionType.fromID("NULL"));
        this.bracketDirection = direction;
        this.bracketType = type;
    }

    @Override
    public JSONObject toJSON() {
        return new JSONObject()
                .put("id", "bracket")
                .put("direct", bracketDirection.getId())
                .put("type", bracketType.getId());
    }
}
