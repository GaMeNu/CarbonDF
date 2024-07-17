package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.blocks.ActionType;
import me.gamenu.carbon.logic.blocks.BlockType;
import me.gamenu.carbon.logic.compile.TranspileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

import static me.gamenu.carbon.logic.compile.TranspileUtils.CBNameToIdentifier;

public class TagUtils {
    public static Map<ActionType, JSONArray> tagMap = new HashMap<>();

    static {
        JSONArray arr;
        arr = TranspileUtils.DBC.getJSONArray("actions");

        for (Object o : arr) {
            JSONObject blockObj = (JSONObject) o;
            String cbName = blockObj.getString("codeblockName");
            String cbID = CBNameToIdentifier.get(cbName);
            ActionType at = ActionType.fromID(blockObj.getString("name"), BlockType.fromID(cbID));
            if (at.getID().equals("dynamic")) at = ActionType.getMatchingDynamicAction(BlockType.fromCodeBlockName(cbName));
            if (at != null) {
                tagMap.put(at, blockObj.getJSONArray("tags"));
            }
        }
    }

    public static Map<ActionType, JSONArray> getTagMap() {
        return tagMap;
    }

    public static String tagCodeName(String id){
        String res = capitilizeWords(id);
        res = res.replaceAll("[^A-Za-z0-9]", "");
        return res;
    }

    private static String capitilizeWords(String in){
        String[] split = in.split(" ");
        String[] resSplit = new String[split.length];
        for (int i = 0; i < split.length; i++){
            String cur = split[i];
            resSplit[i] = cur.substring(0, 1).toUpperCase() + cur.substring(1);
        }
        return String.join(" ", resSplit);
    }


}
