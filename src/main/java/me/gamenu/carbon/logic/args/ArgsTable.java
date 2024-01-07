package me.gamenu.carbon.logic.args;

import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArgsTable implements toJSONObject {

    private final int MAX_ARG_COUNT = 27;

    ArrayList<CodeArg> argList;

    private static <T> void addItemToList(List<? super T> arrayList, int index, T item){
        if (index > arrayList.size()){
            for (int i = arrayList.size(); i < index; i++){
                arrayList.add(null);
                System.out.println("Added null");
            }
        }
        arrayList.add(index, item);
    }

    public ArgsTable(){
        argList = new ArrayList<>();
    }

    public void set(int slot, CodeArg codeArg){
        argList.set(slot, codeArg);
    }

    public void add(CodeArg codeArg){
        argList.add(codeArg);
    }

    public void add (int slot, CodeArg codeArg){
        addItemToList(argList, slot, codeArg);
    }

    public CodeArg get(int slot){
        return argList.get(slot);
    }

    public ArrayList<CodeArg> getArgDataList(){
        return new ArrayList<>(argList);
    }

    @Override
    public JSONObject toJSON() {
        JSONArray argArr = new JSONArray();

        for(int i = 0; i < argList.size(); i++){
            if (argList.get(i) != null) {
                argArr.put(new JSONObject()
                        .put("item", argList.get(i).toJSON())
                        .put("slot", i)
                );
            }
        }

        JSONObject args = new JSONObject();
        args.put("items", argArr);
        return args;
    }
}
