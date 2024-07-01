package me.gamenu.carbon.logic.args;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VarTable implements Map<String, VarArg> {
    private final HashMap<String, VarArg> varMap;

    public VarTable(){
        varMap = new HashMap<>();
    }



    public HashMap<String, VarArg> getVarMap() {
        return varMap;
    }


    /**
     * Smart putVar.
     * This will take a single VarArg, and automatically create a new map entry for it, with the name of the var as the id.
     * @param varArg varArg to put
     * @return whether the var was set successfully.
     */
    public boolean putVar(VarArg varArg){
        if (varExists(varArg.getName())) return false;
        varMap.put(varArg.getName(), varArg);
        return true;
    }

    public VarScope getVarScope(String name){
        return varMap.get(name).getScope();
    }

    public ArgType getVarType(String name){
        return varMap.get(name).getVarType();
    }

    public boolean varExists(String name){
        return (varMap.get(name) != null);
    }

    public void clearLineScope(){
        varMap.entrySet().removeIf(entry -> entry.getValue().getScope() == VarScope.LINE);
    }

    public void clearLocalScope(){
        varMap.entrySet().removeIf(entry -> entry.getValue().getScope() == VarScope.LOCAL);
    }

    public void setVarValue(String name, CodeArg value){
        varMap.get(name).setValue(value);
    }

    /**
     * Alias to putAll
     * @param other
     * @return
     */
    public VarTable extend(VarTable other){
        this.varMap.putAll(other.varMap);
        return this;
    }

    @Override
    public int size() {
        return varMap.size();
    }

    @Override
    public boolean isEmpty() {
        return varMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return varMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return varMap.containsValue(value);
    }

    @Override
    public VarArg get(Object key) {
        return varMap.get(key);
    }

    @Override
    public VarArg put(String key, VarArg value) {
        return varMap.put(key, value);
    }

    @Override
    public VarArg remove(Object key) {
        return varMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends VarArg> m) {
        varMap.putAll(m);
    }

    @Override
    public void clear() {
        varMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return varMap.keySet();
    }

    @Override
    public Collection<VarArg> values() {
        return varMap.values();
    }

    @Override
    public Set<Entry<String, VarArg>> entrySet() {
        return varMap.entrySet();
    }
}
