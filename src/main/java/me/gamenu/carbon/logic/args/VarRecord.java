package me.gamenu.carbon.logic.args;

public record VarRecord(String name, VarScope scope, ArgType type) {
    public VarRecord{
        if (type == null) type = ArgType.ANY;
    }
}
