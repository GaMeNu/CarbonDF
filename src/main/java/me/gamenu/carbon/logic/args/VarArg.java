package me.gamenu.carbon.logic.args;

import org.json.JSONObject;

public class VarArg extends CodeArg{

    ArgType varType;
    String name;
    VarScope scope;

    public VarArg(String name, VarScope scope) {
        super(ArgType.VAR);
        this.name = name;
        this.scope = scope;
        this.varType = ArgType.ANY;
    }

    public VarArg(String name, VarScope scope, ArgType varType) {
        super(ArgType.VAR);
        this.name = name;
        this.scope = scope;
        this.varType = varType;
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
