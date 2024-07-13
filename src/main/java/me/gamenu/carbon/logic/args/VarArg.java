package me.gamenu.carbon.logic.args;

import org.json.JSONObject;

public class VarArg extends CodeArg{

    String name;
    VarScope scope;

    boolean isDynamic;

    boolean isConstant;
    CodeArg value;

    public VarArg(String name, VarScope scope, boolean isDynamic) {
        this(name, scope, isDynamic, ArgType.ANY);
    }

    public VarArg(VarArg other){
        this(other.name, other.scope, other.isDynamic, other.value);
        this.isConstant = other.isConstant;
    }

    public VarArg(String name, VarScope scope, boolean isDynamic, ArgType varType) {
        this(name, scope, isDynamic, new CodeArg(varType));
    }

    public VarArg(String name, VarScope scope, boolean isDynamic, CodeArg value) {
        super(ArgType.VAR);
        this.name = name;
        this.scope = scope;
        this.isDynamic = isDynamic;
        this.value = value;
    }

    public VarArg setValue(CodeArg value) {
        this.value = value;
        return this;
    }

    public VarArg setConstant(boolean constant) {
        isConstant = constant;
        return this;
    }

    public boolean isConstant() {
        return isConstant;
    }

    public CodeArg getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public VarScope getScope() {
        return scope;
    }

    public ArgType getVarType() {
        if (value == null) return ArgType.ANY;
        return value.getType();
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public VarArg setName(String name) {
        this.name = name;
        return this;
    }

    public VarArg setScope(VarScope scope) {
        this.scope = scope;
        return this;
    }

    @Override
    public JSONObject toJSON() {
        this.putData("name", name).putData("scope", scope.getID());
        return super.toJSON();
    }
}
