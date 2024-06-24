package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.blocks.BlockType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FunTable implements Map<String, FunTable.FunType> {

    public static class FunType {
        String name;
        BlockType type;
        boolean hidden;
        ArgsTable params;
        ArgsTable returns;

        public FunType(){}

        public FunType(String name, BlockType type, boolean hidden, ArgsTable params, ArgsTable returns) {
            this.name = name;
            this.type = type;
            this.hidden = hidden;
            this.params = params;
            this.returns = returns;
        }



        public BlockType getType() {
            return type;
        }

        public FunType setType(BlockType type) {
            this.type = type;
            return this;
        }

        public String getName() {
            return name;
        }

        public FunType setName(String name) {
            this.name = name;
            return this;
        }

        public boolean isHidden() {
            return hidden;
        }

        public FunType setHidden(boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        public ArgsTable getParams() {
            return params;
        }

        public FunType setParams(ArgsTable params) {
            this.params = params;
            return this;
        }

        public ArgsTable getReturns() {
            return returns;
        }

        public FunType setReturns(ArgsTable returns) {
            this.returns = returns;
            return this;
        }
    }

    HashMap<String, FunType> funTable;

    public FunTable(){
        funTable = new HashMap<>();
    }

    @Override
    public int size() {
        return funTable.size();
    }

    @Override
    public boolean isEmpty() {
        return funTable.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return funTable.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return funTable.containsValue(value);
    }

    @Override
    public FunType get(Object key) {
        return funTable.get(key);
    }

    @Override
    public FunType put(String key, FunType value) {
        return funTable.put(key, value);
    }

    @Override
    public FunType remove(Object key) {
        return funTable.remove(key);
    }

    public void putAll(Map<? extends String, ? extends FunTable.FunType> m) {
        funTable.putAll(m);
    }

    @Override
    public void clear() {
        funTable.clear();
    }

    @Override
    public Set<String> keySet() {
        return funTable.keySet();
    }

    @Override
    public Collection<FunType> values() {
        return funTable.values();
    }

    @Override
    public Set<Entry<String, FunType>> entrySet() {
        return funTable.entrySet();
    }



}
