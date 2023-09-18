package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.types.BaseValue;

public class SetVar extends BaseBlock {



    String name;
    BaseValue value;
    VarScope scope;

    public SetVar(String name, BaseValue value, VarScope scope) {
        super(BlockType.SET_VAR);
        this.name = name;
        this.value = value;
        this.scope = scope;
    }

    public SetVar(String name, BaseValue value) {
        super(BlockType.SET_VAR);
        this.name = name;
        this.value = value;
        this.scope = VarScope.GAME;
    }

    public String getName() {
        return name;
    }

    public BaseValue getValue() {
        return value;
    }

    public VarScope getScope() {
        return scope;
    }
}
