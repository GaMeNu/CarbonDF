package me.gamenu.carbon.logic.args;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONObject;

public class CodeArg implements toJSONObject {

    ArgType type;
    JSONObject data;

    public CodeArg(ArgType type) {
        this.type = type;
        data = new JSONObject();
    }

    public CodeArg putData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Object getData(String key){
        return data.get(key);
    }

    public ArgType getType() {
        return type;
    }

    @Override
    public JSONObject toJSON() {
        return new JSONObject()
                .put("id", type.getID())
                .put("data", data);
    }
}
