package me.gamenu.carbon.logic.blocks;

import org.json.JSONObject;

public class RepeatBlock extends CodeBlock{
    private ActionType subAction;
    public RepeatBlock(ActionType actionType) {
        super(BlockType.REPEAT, actionType);
    }

    public RepeatBlock(ActionType actionType, ActionType subAction){
        super(BlockType.REPEAT, actionType);
        this.subAction = subAction;
    }


    public RepeatBlock setSubAction(ActionType subAction) {
        this.subAction = subAction;
        return this;
    }

    public ActionType getSubAction() {
        return subAction;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject res = super.toJSON();
        if (subAction != null)
            res.put("subAction", subAction.getID());
        return res;
    }
}
