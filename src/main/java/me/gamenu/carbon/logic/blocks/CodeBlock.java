package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONObject;

public class CodeBlock implements toJSONObject {

    BlockType blockType;
    ActionType actionType;

    ArgsTable args;

    public CodeBlock(BlockType blockType, ActionType actionType){
        this.blockType = blockType;
        this.actionType = actionType;
        this.args = new ArgsTable();
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public ActionType getActionType() {
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
        block.put("block", blockType.getID());
        if (actionType != null){
            block.put("action", actionType.getID());
        }
        block.put("args", args.toJSON());
        return block;
    }




}
