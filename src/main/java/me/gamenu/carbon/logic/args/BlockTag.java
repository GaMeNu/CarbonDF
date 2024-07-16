package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.blocks.ActionType;
import me.gamenu.carbon.logic.blocks.BlockType;
import org.json.JSONObject;

public class BlockTag extends CodeArg{
    BlockType blockType;
    ActionType actionType;
    TagType tagType;
    TagOption tagOption;

    public BlockTag(BlockType blockType, ActionType actionType, TagType tagType, TagOption tagOption) {
        super(ArgType.TAG);
        this.blockType = blockType;
        this.actionType = actionType;
        this.tagType = tagType;
        this.tagOption = tagOption;
    }

    public BlockTag(BlockTag other) {
        super(ArgType.TAG);
        this.blockType = other.blockType;
        this.actionType = other.actionType;
        this.tagType = other.tagType;
        this.tagOption = other.tagOption;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public TagType getTagType() {
        return tagType;
    }

    public TagOption getTagOption() {
        return tagOption;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject res = super.toJSON();

        res.getJSONObject("data")
                .put("block", blockType.getID())
                .put("action", actionType.getID())
                .put("tag", tagType.getID())
                .put("option", tagOption.getID());

        return res;
    }

    @Override
    public String toString() {
        return "BlockTag{" +
                "blockType=" + blockType +
                ", actionType=" + actionType +
                ", tagType=" + tagType +
                ", tagOption=" + tagOption +
                '}';
    }
}
