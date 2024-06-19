package me.gamenu.carbon.logic.blocks;

import me.gamenu.carbon.logic.args.ArgType;
import me.gamenu.carbon.logic.args.CodeArg;
import me.gamenu.carbon.logic.args.FunctionParam;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONObject;

import java.util.ArrayList;

public class DefinitionBlock extends CodeBlock implements toJSONObject {
    String name;
    public DefinitionBlock(BlockType blockType, ActionType actionType, String name) {
        super(blockType, actionType);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ArgType> getParamTypes(){

        ArrayList<ArgType> res = new ArrayList<>();

        FunctionParam param;

        for (CodeArg arg : args.getArgDataList()){
            if (arg.getType() != ArgType.PARAM) continue;
            param = (FunctionParam) arg;
            res.add(param.getParamType());
        }

        return res;
    }

    @Override
    public JSONObject toJSON() {
        return super.toJSON().put("data", name);
    }
}
