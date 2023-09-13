package me.gamenu.carbon.logic.blocks;

public abstract class BaseBlock {
    String id;
    String block;

    public BaseBlock(String block) {
        this.id = "block";
        this.block = block;
    }
}
