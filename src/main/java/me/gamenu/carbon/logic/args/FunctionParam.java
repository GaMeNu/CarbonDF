package me.gamenu.carbon.logic.args;

public class FunctionParam extends CodeArg{
    public FunctionParam(String name, Type type){
        super(Type.PARAM);

        dataPut("name", name);
        dataPut("type", type.getID());
        dataPut("plural", false);
        dataPut("optional", false);
    }

}
