package me.gamenu.carbon.logic.types;

public class Number extends BaseItem {
    double value;
    public Number(String id, double name) {
        super("num");
        this.value = name;
        data.put("name", String.valueOf(name));
    }

    public double getValue() {
        return value;
    }
}
