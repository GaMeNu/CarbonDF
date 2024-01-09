package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONObject;

public class DefinitionBlock extends CodeBlock implements toJSONObject {
    String name;
    public DefinitionBlock(BlockType blockType, ActionType actionType, String name) {
        super(blockType, actionType);
        this.name = name;
    }

    @Override
    public JSONObject toJSON() {
        return super.toJSON().put("data", name);
    }
}
