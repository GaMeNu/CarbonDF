package me.gamenu.carbon.logic.blocks;

public class CarbonFunction extends BaseBlock {
    String data;

    public CarbonFunction(String name) {
        super(BlockType.FUNC);
        data = name;
    }

    @Override
    public String toString() {
        return "Function " + data + "()";
    }

    public String getName(){
        return data;
    }
}
