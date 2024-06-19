package me.gamenu.carbon.logic.args;

import org.json.JSONObject;

public class FunctionParam extends CodeArg{

    String name;
    ArgType paramType;
    boolean plural;
    boolean optional;


    public FunctionParam(String name, ArgType type){
        super(ArgType.PARAM);

        this.name = name;
        this.paramType = type;
        this.plural = false;
        this.optional = false;
    }

    public String getName() {
        return name;
    }

    public FunctionParam setName(String name) {
        this.name = name;
        return this;
    }

    public ArgType getParamType() {
        return paramType;
    }

    public FunctionParam setParamType(ArgType paramType) {
        this.paramType = paramType;
        return this;
    }

    public boolean isPlural() {
        return plural;
    }

    public FunctionParam setPlural(boolean plural) {
        this.plural = plural;
        return this;
    }

    public boolean isOptional() {
        return optional;
    }

    public FunctionParam setOptional(boolean optional) {
        this.optional = optional;
        return this;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject res = super.toJSON();
        res.getJSONObject("data")
                .put("name", name)
                .put("type", paramType.getID())
                .put("plural", plural)
                .put("optional", optional);
        return res;
    }
}
