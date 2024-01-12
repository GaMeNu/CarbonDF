package me.gamenu.carbon.logic.etc;

public enum TargetType implements CarbonTypeEnum {
    SELECTION("Selection", "selection"),
    DEFAULT("Default", "default");


    private final String id;
    private final String codeName;

    TargetType(String id, String codeName){
        this.id = id;
        this.codeName = codeName;
    }

    @Override
    public String getID() {
        return id;
    }

    public String getCodeName() {
        return codeName;
    }

    public static TargetType fromCodeName(String codeName){
        for (TargetType tt: TargetType.values()){
            if (tt.codeName.equals(codeName)) return tt;
        }
        return null;
    }
}
