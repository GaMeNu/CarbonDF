package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.CodeBlockArgs;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

public class CodeBlock implements toJSONObject {

    BlockTypes.Type blockType;
    ActionTypes.Type actionType;

    CodeBlockArgs args;

    public CodeBlock(BlockTypes.Type blockType, ActionTypes.Type actionType){
        this.blockType = blockType;
        this.actionType = actionType;
        this.args = new CodeBlockArgs();
    }

    public JSONObject toJSON(){
        JSONObject block = new JSONObject();
        block.put("id", "block");
        block.put("block", BlockTypes.typeID(blockType));
        if (actionType != null){
            block.put("action", ActionTypes.typeID(actionType));
        }
        block.put("args", args.toJSON());
        return block;
    }




}
