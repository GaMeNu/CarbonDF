package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONObject;

public class CodeBlock implements toJSONObject {

    BlockTypes.Type blockType;
    ActionTypes.Type actionType;

    ArgsTable args;

    public CodeBlock(BlockTypes.Type blockType, ActionTypes.Type actionType){
        this.blockType = blockType;
        this.actionType = actionType;
        this.args = new ArgsTable();
    }

    public BlockTypes.Type getBlockType() {
        return blockType;
    }

    public ActionTypes.Type getActionType() {
        return actionType;
    }

    public ArgsTable args() {
        return args;
    }

    public void setArgs(ArgsTable args) {
        this.args = args;
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
