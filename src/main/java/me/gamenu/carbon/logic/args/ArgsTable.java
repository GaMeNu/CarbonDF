package me.gamenu.carbon.logic.args;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArgsTable implements toJSONObject {

    private final int MAX_ARG_COUNT = 27;

    ArrayList<CodeArg> argList;

    /**
     * Adds NULL to a List up to a given finalSize
     * list.size() == finalSize
     * (Last item index in list is finalSize - 1)
     * @param list list to extend
     * @param finalSize final size of the list
     * @param <T> Object
     */
    private static <T> void addNulls(List<? super T> list, int finalSize){
        if (finalSize > list.size()){
            for (int i = list.size(); i < finalSize; i++){
                list.add(null);
            }
        }
    }

    private static <T> void addItemToList(List<? super T> list, int index, T item){
        addNulls(list, index);
        list.add(index, item);
    }


    private static <T> void setItemInList(List<? super T> list, int index, T item){
        addNulls(list, index+1);
        list.set(index, item);
    }

    public ArgsTable() {
        argList = new ArrayList<>();
    }


    /**
     * adds at the first empty (null) place, or a the end of the list if none found
     * @param codeArg CodeArg to add
     * @return this
     */
    public ArgsTable addAtFirstNull(CodeArg codeArg){
        // Iterate over the list
        for (int i = 0; i < argList.size(); i++){

            // If we've found a null value
            // we set the value to the CodeArg and return this.
            if (argList.get(i) == null){
                argList.set(i, codeArg);
                return this;
            }

        }

        argList.add(codeArg);
        return this;

    }

    public ArgsTable set(int slot, CodeArg codeArg){
        setItemInList(argList, slot, codeArg);
        return this;
    }

    public ArgsTable add (int slot, CodeArg codeArg){
        addItemToList(argList, slot, codeArg);
        return this;
    }
    
    public ArgsTable extend(ArgsTable other){
        for (CodeArg item :
                other.argList) {
            if (item == null) continue;
            this.addAtFirstNull(item);
        }
        return this;
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
