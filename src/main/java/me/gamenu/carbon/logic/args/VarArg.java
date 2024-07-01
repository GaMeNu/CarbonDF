package me.gamenu.carbon.logic.args;

import org.json.JSONObject;

public class VarArg extends CodeArg{

    String name;
    VarScope scope;

    boolean isDynamic;

    CodeArg value;

    public VarArg(String name, VarScope scope, boolean isDynamic) {
        super(ArgType.VAR);
        this.name = name;
        this.scope = scope;
        this.isDynamic = isDynamic;
        this.value = new CodeArg(ArgType.ANY);
    }

    public VarArg(VarArg other){
        super(ArgType.VAR);
        this.name = other.name;
        this.scope = other.scope;
        this.isDynamic = other.isDynamic;
        this.value = other.value;
    }

    public VarArg(String name, VarScope scope, boolean isDynamic, ArgType varType) {
        super(ArgType.VAR);
        this.name = name;
        this.scope = scope;
        this.isDynamic = isDynamic;
        this.value = new CodeArg(varType);
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
