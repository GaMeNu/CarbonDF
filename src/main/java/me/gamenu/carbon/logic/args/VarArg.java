package me.gamenu.carbon.logic.args;

public class VarArg extends CodeArg{

    public VarArg(String name, VarScope scope) {
        super(ArgType.VAR);
        putData("name", name);
        putData("scope", scope.getID());
    }

    public String getName() {
        return (String) getData("name");
    }

    public VarScope getScope() {
        return VarScope.fromID((String) getData("scope"));
    }
}
