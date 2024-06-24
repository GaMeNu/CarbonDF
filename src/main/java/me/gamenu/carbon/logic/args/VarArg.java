package me.gamenu.carbon.logic.args;

import org.json.JSONObject;

public class VarArg extends CodeArg{

    String name;
    VarScope scope;

    ArgType varType;
    boolean isDynamic;

    CodeArg value;

    public VarArg(String name, VarScope scope, boolean isDynamic) {
        super(ArgType.VAR);
        this.name = name;
        this.scope = scope;
        this.varType = ArgType.ANY;
        this.isDynamic = isDynamic;
        this.value = null;
    }

    public VarArg(VarArg other){
        super(ArgType.VAR);
        this.name = other.name;
        this.scope = other.scope;
        this.varType = other.varType;
        this.isDynamic = other.isDynamic;
        this.value = other.value;
    }

    public VarArg(String name, VarScope scope, boolean isDynamic, ArgType varType) {
        super(ArgType.VAR);
        this.name = name;
        this.scope = scope;
        this.varType = varType;
        this.isDynamic = isDynamic;
        this.value = null;
    }


    public VarArg setValue(CodeArg value) {
        this.value = value;
        return this;
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
        return varType;
    }

    public VarArg setVarType(ArgType varType) {
        this.varType = varType;
        return this;
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
