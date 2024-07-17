package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.compile.TranspileUtils;
import me.gamenu.carbon.logic.etc.CarbonTypeEnum;
import me.gamenu.carbon.logic.etc.TargetType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class GameValue extends CodeArg{

    public static class GameValueType implements CarbonTypeEnum {

        public static final HashMap<String, GameValueType> gameValueTypes = new HashMap<>();


        static {
            JSONArray gameValues = TranspileUtils.DBC.getJSONArray("gameValues");
            for (Object o: gameValues){
                JSONObject gv = (JSONObject) o;
                JSONObject icon = gv.getJSONObject("icon");
                String name = verifyColorsRemoved(icon.getString("name"));
                String codeName = nameToCodeName(name);
                ArgType type = ArgType.fromStringName(icon.getString("returnType"));
                gameValueTypes.put(codeName, new GameValueType(name, codeName, type));
            }
        }

        private static String verifyColorsRemoved(String name){
            if (name.charAt(0) == '§') return name.substring(2);
            return name;
        }

        private static String nameToCodeName(String name){
            return String.join("_", name.toLowerCase().split(" "));
        }

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
            for (GameValueType gvType : gameValueTypes.values()) {
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
