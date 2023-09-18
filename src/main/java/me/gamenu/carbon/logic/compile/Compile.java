package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.blocks.BaseBlock;
import me.gamenu.carbon.logic.blocks.BlockType;
import me.gamenu.carbon.logic.blocks.CarbonFunction;
import org.json.*;

import java.util.HashMap;

public class Compile {

    private static HashMap<BlockType, String> typeID = new HashMap<>(){{
        put(BlockType.FUNC, "func");
    }};

    public static JSONObject toJson(BaseBlock firstBlock){
        JSONObject compiled = new JSONObject();
        JSONArray blocks = new JSONArray();
        compiled.put("blocks", blocks);
        BaseBlock currentBlock = firstBlock;
        while (currentBlock != null){

            blocks.put(compileSingleBlock(currentBlock));
            currentBlock = currentBlock.getNext();
        }
        System.out.println(compiled);
        return compiled;
    }

    public static JSONObject compileSingleBlock(BaseBlock block){
        JSONObject compiled = new JSONObject();
        compiled.put("id","block");
        compiled.put("block", typeID.get(block.getType()));
        if (block.getType() == BlockType.FUNC) compiled.put("data", ((CarbonFunction) block).getName());
        return compiled;
    }
}
