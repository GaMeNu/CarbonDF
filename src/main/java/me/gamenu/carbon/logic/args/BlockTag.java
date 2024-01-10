package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.blocks.ActionType;
import me.gamenu.carbon.logic.blocks.BlockType;

public class BlockTag extends CodeArg{

    public BlockTag(String tag, BlockType block, ActionType action, String option){
        super(ArgType.TAG);
        putData("option", option);
        putData("tag", tag);
        putData("block", block.getID());
        putData("action", action.getID());
    }
}
