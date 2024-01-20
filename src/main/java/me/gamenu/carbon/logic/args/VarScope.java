package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.CarbonTypeEnum;

public enum VarScope implements CarbonTypeEnum {
    GLOBAL("unsaved", "global"),
    SAVED("saved", "saved"),
    LOCAL("local", "local"),
    LINE("line", "line");

    private final String id;
    private final String codeName;

    VarScope(String id, String codeName){
        this.id = id;
        this.codeName = codeName;
    }
    public static VarScope fromID(String id){
        for (VarScope scope :
                VarScope.values()) {
            if (scope.id.equals(id)) return scope;
        }
        return null;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String getCodeName() {
        return this.codeName;
    }
}
