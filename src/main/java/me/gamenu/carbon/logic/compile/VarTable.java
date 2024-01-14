package me.gamenu.carbon.logic.compile;
import me.gamenu.carbon.logic.etc.CarbonTypeEnum;

import java.util.HashMap;

public class VarTable {
    public enum VarScope implements CarbonTypeEnum {
        GLOBAL("global", "global"),
        SAVED("saved", "saved"),
        LOCAL("local", "local"),
        LINE("line", "line");

        private final String id;
        private final String codeName;

        VarScope(String id, String codeName){
            this.id = id;
            this.codeName = codeName;
        }

        @Override
        public String getID() {
            return null;
        }

        @Override
        public String getCodeName() {
            return null;
        }
    }
    private final HashMap<String, VarScope> varMap;

    public VarTable(){
        varMap = new HashMap<>();
    }

    public VarTable putVar(String name, VarScope scope) {
        varMap.put(name, scope);
        return this;
    }

    public VarScope getVarScope(String name){
        return varMap.get(name);
    }

    public boolean varExists(String name){
        return (getVarScope(name) != null);
    }
}
