package me.gamenu.carbon.logic.types;

public class Number extends BaseValue {

    public Number(String id, double value) {
        super(ValueType.NUM);
        this.value = value;
    }

    public Double getValue() {
        return (double) this.value;
    }
}
