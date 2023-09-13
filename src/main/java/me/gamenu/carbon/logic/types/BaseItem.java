package me.gamenu.carbon.logic.types;

import java.util.HashMap;

public abstract class BaseItem {
    String id;
    HashMap<String, Object> data;
    public BaseItem(String id) {
        this.id = id;
        this.data = new HashMap<>();
    }
}
