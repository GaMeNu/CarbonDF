package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONObject;

import java.util.HashMap;

public class CodeArg implements toJSONObject {

    public enum Type{
        ANY,
        PARAM,
        VAR,
        NUM
    }

    private static final HashMap<Type, String> typeMap = new HashMap<>(){{
        put(Type.ANY, "any");
       put(Type.PARAM, "pn_el");
       put(Type.VAR, "var");
       put(Type.NUM, "num");
    }};

    public static String getID(Type type){
        return typeMap.get(type);
    }



    Type type;
    JSONObject data;

    public CodeArg(Type type) {
        this.type = type;
        data = new JSONObject();
    }

    public void dataPut(String key, Object value) {
        data.put(key, value);
    }

    public Object dataGet(String key){
        return data.get(key);
    }


    @Override
    public JSONObject toJSON() {
        return new JSONObject()
                .put("id", getID(type))
                .put("data", data);
    }
}
