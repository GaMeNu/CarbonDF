package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.compile.TranspileUtils;
import me.gamenu.carbon.logic.etc.CarbonTypeEnum;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class BlockType implements CarbonTypeEnum {

    private static final HashMap<String, BlockType> blockTypes = new HashMap<>();

    public static String nameToCodeName(String name){
        StringBuilder resB = new StringBuilder();
        String[] resSp = name.split(" ");
        for (String tok: resSp){
            resB.append(tok.toUpperCase().charAt(0)).append(tok.toLowerCase().substring(1));
        }
        return resB.toString();
    }

    static {

        JSONArray codeblocks;

        codeblocks = TranspileUtils.DBC.getJSONArray("codeblocks");

        for (Object o: codeblocks){
            JSONObject codeblock = (JSONObject) o;
            String name = codeblock.getString("name");
            String codeName = nameToCodeName(name);
            String id = codeblock.getString("identifier");

            switch (id) {
                case "entity_event" -> codeName = "event";
                case "event" -> codeName = "event";
                case "func" -> codeName = "fun";
                case "process" -> codeName = "proc";
            }

            blockTypes.put(id, new BlockType(id, name, codeName));

        }

        blockTypes.put("bracket", new BlockType("bracket", null, null));
    }

    private final String id;
    private final String codeBlockName;

    private final String codeName;

    BlockType(String id, String codeBlockName, String codeName) {
        this.id = id;
        this.codeBlockName = codeBlockName;
        this.codeName = codeName;
    }

    public String getID() {
        return id;
    }


    public String getCodeName() {
        return codeName;
    }

    public String getCodeBlockName() {
        return codeBlockName;
    }

    public static HashMap<String, BlockType> getBlockTypes() {
        return blockTypes;
    }

    public static BlockType fromCodeName(String codeName){
        for (BlockType bt : BlockType.getBlockTypes().values()) {
            if (bt.getCodeName() == null) continue;
            if (bt.getCodeName().equals(codeName)) return bt;
        }

        return null;
    }
    public static BlockType fromID(String id){
        return getBlockTypes().get(id);
    }

    public static BlockType fromCodeBlockName(String codeBlockName){
        for (BlockType bt : BlockType.getBlockTypes().values()) {
            if (bt.getCodeBlockName() == null) continue;
            if (bt.getCodeBlockName().equals(codeBlockName)) return bt;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockType blockType)) return false;
        return Objects.equals(id, blockType.id) && Objects.equals(codeName, blockType.codeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codeName);
    }
}
