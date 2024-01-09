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
