package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BlocksTable implements toJSONObject {
    final ArrayList<CodeBlock> table;

    public BlocksTable(){
        table = new ArrayList<>();
    }

    public BlocksTable add(CodeBlock block){
        table.add(block);
        return this;
    }

    public ArrayList<CodeBlock> list() {
        return table;
    }

    public CodeBlock get(int i){
        return table.get(i);
    }

    /**
     * Basically ArrayList.addAll()
     * @param other table to extend
     * @return this
     */
    public BlocksTable extend(BlocksTable other){
        this.table.addAll(other.table);
        return this;
    }


    @Override
    public JSONObject toJSON() {
        JSONArray blocksArray = new JSONArray();
        for (int i = 0; i < table.size(); i++){
            blocksArray.put(get(i).toJSON());
        }
        JSONObject res = new JSONObject();
        res.put("blocks", blocksArray);
        return res;
    }
}
