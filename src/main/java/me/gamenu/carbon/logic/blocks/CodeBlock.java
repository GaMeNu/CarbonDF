package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.etc.TargetType;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONObject;

public class CodeBlock implements toJSONObject {

    BlockType blockType;
    ActionType actionType;
    TargetType targetType;
    ArgsTable args;

    public CodeBlock(BlockType blockType, ActionType actionType){
        this.blockType = blockType;
        this.actionType = actionType;
        this.targetType = null;
        this.args = new ArgsTable();
    }

    public CodeBlock(BlockType blockType, ActionType actionType, TargetType targetType){
        this.blockType = blockType;
        this.actionType = actionType;
        this.targetType = targetType;
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
        if (targetType != null){
            block.put("target", targetType.getID());
        }
        block.put("args", args.toJSON());
        return block;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }
}
