package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.etc.TargetType;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONObject;

public class CodeBlock implements toJSONObject {

    public enum Attribute {
        NOT("NOT"),
        LS_CANCEL("LS-CANCEL");
        final String id;
        Attribute(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    BlockType blockType;
    ActionType actionType;
    TargetType targetType;

    ArgsTable args;

    Attribute attribute;


    public CodeBlock(BlockType blockType, ActionType actionType){
        this(blockType, actionType, null);
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

    public ArgsTable getArgs() {
        return args;
    }

    public CodeBlock setArgs(ArgsTable args) {
        this.args = args;
        return this;
    }

    public CodeBlock setAttribute(Attribute attribute) {
        this.attribute = attribute;
        return this;
    }

    public Attribute getAttribute() {
        return attribute;
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
        if (attribute != null){
            block.put("attribute", attribute.getId());
        }
        block.put("args", args.toJSON());
        return block;
    }

    public CodeBlock setBlockType(BlockType blockType) {
        this.blockType = blockType;
        return this;
    }

    public CodeBlock setActionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public CodeBlock setTargetType(TargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public TargetType getTargetType() {
        return targetType;
    }
}
