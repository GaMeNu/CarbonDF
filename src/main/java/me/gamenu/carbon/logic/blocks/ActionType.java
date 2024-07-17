package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.*;
import me.gamenu.carbon.logic.compile.TranspileUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionType {

    private static final HashMap<BlockType, HashMap<String, ActionType>> actionTypes = new HashMap<>();

    static {
        JSONArray actions;

        actions = TranspileUtils.DBC.getJSONArray("actions");

        for (BlockType bt: BlockType.getBlockTypes().values()){
            actionTypes.put(bt, new HashMap<>());
        }

        for (Object o: actions){
            JSONObject action = (JSONObject) o;
            String id = action.getString("name");
            String codeblockName = action.getString("codeblockName");
            BlockType blockType = BlockType.fromCodeBlockName(codeblockName);
            boolean cancellable = action.optBoolean("cancellable");
            String codeName = nameToCodeName(id, blockType);
            actionTypes.get(blockType).put(id, new ActionType(id, codeName, blockType, cancellable));
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
            default -> {
                id = id.strip();
                yield id.toLowerCase().charAt(0) + id.substring(1);
            }
        };
    }

    private static final ArrayList<ActionType> dynamics = new ArrayList<>(){{
        add(new ActionType("dynamic", null, BlockType.fromID("func")));
        add(new ActionType("dynamic", null, BlockType.fromID("call_func")));
        add(new ActionType("dynamic", null, BlockType.fromID("process")));
        add(new ActionType("dynamic", null, BlockType.fromID("start_process")));
    }};


    private static final HashMap<String, ArrayList<ArgsTable>> baseFunsReturns = new HashMap<>() {{
        JSONArray actions = TranspileUtils.DBC.getJSONArray("actions");
        for (Object o: actions){
            JSONObject action = (JSONObject) o;
            BlockType blockType = BlockType.fromCodeBlockName(action.getString("codeblockName"));
            if (!blockType.equals(BlockType.fromID("set_var"))) continue;
            ActionType actionType = ActionType.fromID(action.getString("name"), blockType);
            JSONArray returnValues = action.getJSONObject("icon").getJSONArray("returnValues");

            ArrayList<ArgsTable> retOpts = new ArrayList<>();
            for (Object ob: returnValues){
                ArgsTable rets = new ArgsTable();
                JSONObject returnValue = (JSONObject) ob;
                String type = returnValue.optString("type", null);
                if (type == null) continue;
                rets.addAtFirstNull(new CodeArg(ArgType.fromStringName(type)));
                retOpts.add(rets);
            }
            put(actionType.getCodeName(), retOpts);
        }
    }};

    public static HashMap<String, ArrayList<ArgsTable>> getBaseFunsReturns() {
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

    public static ActionType fromCodeName(String codeName, BlockType blockType) {
        for (ActionType at: actionTypes.get(blockType).values()) {
            if (at.getCodeName() == null) continue;
            if (at.getCodeName().equals(codeName)) return at;
        }
        return null;
    }

    public static ActionType fromCodeName(String codeName){
        ActionType res = null;
        for (HashMap<String, ActionType> blockActions: ActionType.actionTypes.values()) {
            for (ActionType at: blockActions.values()) {
                if (at.getCodeName() == null) continue;
                if (at.getCodeName().equals(codeName)) {
                    if (res == null) res = at;
                    else return null;
                }
            }
        }
        return res;
    }

    public static HashMap<String, ActionType> getAllOfBlock(BlockType blockType){
        return actionTypes.get(blockType);
    }
    public static ActionType fromID(String id, BlockType blockType) {
        return actionTypes.get(blockType).get(id);
    }
    /*
    public static ActionType fromID(String id) {
        for (HashMap<String, ActionType> ba: actionTypes.values()) {
            return ba.get(id);
        }
        return null;
    }*/

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
