package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.hasTypeID;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONObject;

public class CodeArg implements toJSONObject {

    public enum Type implements hasTypeID {
        ANY("any"),
        PARAM("pn_el"),
        VAR("var"),
        NUM("num");

        private final String id;

        Type(String id){
            this.id = id;
        }

        @Override
        public String getID() {
            return id;
        }
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
                .put("id", type.getID())
                .put("data", data);
    }
}
