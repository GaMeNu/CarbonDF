package me.gamenu.carbon.logic.types;

import java.util.HashMap;

public abstract class BaseValue {


    static HashMap<ValueType, String> typeMap = new HashMap<>(){{
        put(ValueType.NUM, "num");
    }};

    ValueType type;
    Object value;

    public BaseValue(ValueType type) {
        this.type = type;
    }

    public ValueType getType() {
        return type;
    }

    public Object getValue;

}
