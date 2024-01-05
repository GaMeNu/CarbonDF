package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

public class CodeBlockArgs implements toJSONObject {

    // TODO: IMPLEMENT ARGS SMH
    @Override
    public JSONObject toJSON() {
        JSONObject args = new JSONObject();
        args.put("items", new JSONArray());
        return args;
    }
}
