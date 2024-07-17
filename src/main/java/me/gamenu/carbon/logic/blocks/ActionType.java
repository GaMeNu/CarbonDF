package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.compile.TranspileUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionType {

    private static final HashMap<String, ActionType> actionTypes = new HashMap<>();

    static {
        JSONArray actions;

        actions = TranspileUtils.DBC.getJSONArray("actions");
        actionTypes.put("NULL", new ActionType(null, null, null));

        for (Object o: actions){
            JSONObject action = (JSONObject) o;
            String id = action.getString("name");
            String codeblockName = action.getString("codeblockName");
            BlockType blockType = BlockType.fromCodeBlockName(codeblockName);
            boolean cancellable = action.optBoolean("cancellable");
            String codeName = nameToCodeName(id, blockType);
            actionTypes.put(id, new ActionType(id, codeName, blockType, cancellable));
        }
    }

    private static String nameToCodeName(String id, BlockType blockType){
        if (blockType.getCodeName().equals("event")) return id;
        if (id.equals("dynamic")) return null;

        return switch (id) {
            case "+" -> "add";
            case "-" -> "sub";
            case "x" -> "mult";
            case "/" -> "div";
            default -> id.toLowerCase().charAt(0) + id.substring(1);
        };
    }

    private static final ArrayList<ActionType> dynamics = new ArrayList<>(){{
        add(new ActionType("dynamic", null, BlockType.fromID("func")));
        add(new ActionType("dynamic", null, BlockType.fromID("call_func")));
        add(new ActionType("dynamic", null, BlockType.fromID("process")));
        add(new ActionType("dynamic", null, BlockType.fromID("start_process")));
    }};


    private static final HashMap<String, ArgsTable> baseFunsReturns = new HashMap<>() {{
        put("add", new ArgsTable().addAtFirstNull(new CodeArg(ArgType.NUM)));
        put("sub", new ArgsTable().addAtFirstNull(new CodeArg(ArgType.NUM)));
        put("mult", new ArgsTable().addAtFirstNull(new CodeArg(ArgType.NUM)));
        put("div", new ArgsTable().addAtFirstNull(new CodeArg(ArgType.NUM)));
    }};

    public static HashMap<String, ArgsTable> getBaseFunsReturns() {
        return baseFunsReturns;
    }

    private final String id;
    private final String codeName;
    private final BlockType blockType;
    private final boolean cancellable;

    ActionType(String id, String codeName, BlockType blockType){
        this(id, codeName, blockType, false);
    }

    ActionType(String id, String codeName, BlockType blockType, boolean cancellable){
        this.id = id;
        this.codeName = codeName;
        this.blockType = blockType;
        this.cancellable = cancellable;
    }
    public String getID() {
        return id;
    }

    public String getCodeName(){
        return codeName;
    }

    public static ActionType fromCodeName(String codeName) {
        for (ActionType at : ActionType.actionTypes.values()) {
            if (at.getCodeName() == null) continue;
            if (at.getCodeName().equals(codeName)) return at;
        }
        return null;
    }
    public static ActionType fromID(String id) {
        return actionTypes.get(id);
    }

    public BlockType getBlock() {
        return blockType;
    }

    public boolean equals(ActionType other){
        // We ASSUME that future maintainers aren't dumb
        // and understand that IDs are *UNIQUE*
        // ...turns out IDs weren't unique. - 2024/07/17
        return this.id.equals(other.id) && this.blockType.equals(other.blockType);
    }

    /**
     * This here bc am dumb.
     * @param type type of dynamic to return
     * @return matching dynamic
     */
    public static ActionType getMatchingDynamicAction(BlockType type){
        for (ActionType dynamic: dynamics) {
            if (dynamic.getBlock().equals(type)) return dynamic;
        }
        return null;
    }

    public boolean isCancellable() {
        return cancellable;
    }
}
