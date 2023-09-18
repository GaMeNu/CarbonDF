package me.gamenu.carbon.logic.blocks;

public abstract class BaseBlock {
    BlockType type;

    BaseBlock next;

    public BaseBlock(BlockType type) {
        this.type = type;
        next = null;
    }

    public BaseBlock getNext(){
        return next;
    }

    public void setNext(BaseBlock block){
        this.next = block;
    }

    public BaseBlock getLast(){
        BaseBlock current;
        current = this;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }

    public BlockType getType(){
        return this.type;
    }
}
