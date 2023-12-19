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

    public void addBlock(CodeBlock block){
        table.add(block);
    }

    public CodeBlock getBlock(int i){
        return table.get(i);
    }


    @Override
    public JSONObject toJSON() {
        JSONArray blocksArray = new JSONArray();
        for (int i = 0; i < table.size(); i++){
            blocksArray.put(getBlock(i).toJSON());
        }
        JSONObject res = new JSONObject();
        res.put("blocks", blocksArray);
        return res;
    }
}
