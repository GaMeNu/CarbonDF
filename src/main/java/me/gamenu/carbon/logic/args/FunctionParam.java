package me.gamenu.carbon.logic.args;

public class FunctionParam extends CodeArg{
    public FunctionParam(String name, ArgType type){
        super(ArgType.PARAM);

        putData("name", name);
        putData("type", type.getID());
        putData("plural", false);
        putData("optional", false);
    }

}
