package me.gamenu.carbon.logic.args;

import org.json.JSONObject;

public class FunctionParam extends CodeArg{

    String name;

    CodeArg internalArg;

    boolean plural;
    boolean optional;


    public FunctionParam(String name, CodeArg arg){
        super(ArgType.PARAM);

        this.name = name;
        this.internalArg = arg;
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
        return internalArg.getType();
    }

    public CodeArg getInternalArg() {
        return internalArg;
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
                .put("type", internalArg.getType().getID())
                .put("plural", plural)
                .put("optional", optional);
        return res;
    }
}
