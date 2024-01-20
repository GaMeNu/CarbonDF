package me.gamenu.carbon.logic.compile;
import me.gamenu.carbon.logic.args.ArgType;
import me.gamenu.carbon.logic.args.VarRecord;
import me.gamenu.carbon.logic.args.VarScope;
import java.util.HashMap;

public class VarTable {
    private final HashMap<String, VarRecord> varMap;

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
        varMap.put(name, new VarRecord(name, scope, ArgType.ANY));
        return this;
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
        varMap.put(name, new VarRecord(name, scope, type));
        return this;
    }

    public VarScope getVarScope(String name){
        return varMap.get(name).scope();
    }

    public ArgType getVarType(String name){
        return varMap.get(name).type();
    }

    public boolean varExists(String name){
        return (varMap.get(name) != null);
    }

    public VarTable extend(VarTable other){
        this.varMap.putAll(other.varMap);
        return this;
    }
}
