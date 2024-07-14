package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.CarbonTypeEnum;
import me.gamenu.carbon.logic.etc.TargetType;
import org.json.JSONObject;

public class GameValue extends CodeArg{

    public enum GameValueType implements CarbonTypeEnum {

        PLAYER_COUNT("Player Count", "player_count", ArgType.NUM),
        ;

        final String id;
        final String codeName;
        final CodeArg returnArg;

        GameValueType(String id, String codeName, CodeArg returnArg) {
            this.id = id;
            this.codeName = codeName;
            this.returnArg = returnArg;
        }

        GameValueType(String id, String codeName, ArgType argType) {
            this(id, codeName, new CodeArg(argType));
        }


        public static GameValueType fromCodeName(String codeName){
            for (GameValueType gvType : GameValueType.values()) {
                if (gvType.codeName.equals(codeName)) return gvType;
            }
            return null;
        }

        @Override
        public String getID() {
            return this.id;
        }

        @Override
        public String getCodeName() {
            return this.codeName;
        }

        public CodeArg getReturnArg() {
            return returnArg;
        }
    }

    TargetType target;

    GameValueType gvType;

    public GameValue(GameValueType gvType, TargetType target) {
        super(ArgType.GAME_VALUE);
        this.gvType = gvType;
        this.target = target;
    }

    public GameValue(GameValue other) {
        super(other);
        this.gvType = other.gvType;
        this.target = other.target;
    }

    public TargetType getTarget() {
        return target;
    }

    public GameValueType getGvType() {
        return gvType;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject res = super.toJSON();
        JSONObject data = new JSONObject()
                .put("type", gvType.getID())
                .put("target", target.getID());
        res.put("data", data);
        return res;
    }
}
