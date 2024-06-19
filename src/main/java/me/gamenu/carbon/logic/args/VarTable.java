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

    /**
     * This var will automatically be assigned the ANY type.
     * If type checking is enabled, this will override the type checking
     * @param name String
     * @param scope VarScope
     * @return this
     */
    public VarTable putVar(String name, VarScope scope) {
        varMap.put(name, new VarArg(name, scope, ArgType.ANY));
        return this;
    }

    public VarTable putVar(String name, VarArg var){
        varMap.put(name, var);
        return this;
    }

    public HashMap<String, VarArg> getVarMap() {
        return varMap;
    }

    /**
     * This var will be assigned the given type.
     * If type checking is enabled and the variable gets assigned a value that doesn't match the type,
     * the transpiler will throw an error.
     * @param name String
     * @param scope VarScope
     * @param type ArgType
     * @return this
     */
    public VarTable putVar(String name, VarScope scope, ArgType type) {
        varMap.put(name, new VarArg(name, scope, type));
        return this;
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

    public void setVarType(String name, ArgType type){
        varMap.get(name).setVarType(type);
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
