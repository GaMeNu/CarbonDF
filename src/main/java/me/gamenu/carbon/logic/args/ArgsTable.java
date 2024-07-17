package me.gamenu.carbon.logic.args;
import me.gamenu.carbon.logic.etc.toJSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArgsTable implements toJSONObject {

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

    public ArgsTable insertExtend(ArgsTable other){
        argList.addAll(0, other.getArgDataList());
        return this;
    }

    public CodeArg get(int slot){
        return argList.get(slot);
    }

    public boolean matchSizes(ArgsTable paramsTable){
        return paramsTable.getArgDataList().size() == getArgDataList().size();
    }

    /**
     * Validates types of this args table against a params table.
     * @param paramsTable an ArgsTable filled with FunctionParams.
     * @return the index of the first non-matching param (-1 if none found)
     */
    public int matchParams(ArgsTable paramsTable) {
        for (int i = 0; i < argList.size(); i++) {
            FunctionParam otherArg = ((FunctionParam) paramsTable.get(i));
            CodeArg thisArg = get(i);
            if (!matchSingleParam(thisArg, otherArg)) return i;
        }
        return -1;
    }

    private static boolean matchSingleParam(CodeArg thisArg, FunctionParam toMatch) {
        // Fix for Game Values:
        if (thisArg instanceof GameValue) thisArg = ((GameValue) thisArg).getGvType().getReturnArg();

        ArgType thisType = thisArg.getType();
        ArgType paramType = toMatch.getParamType();

        if (paramType == ArgType.ANY) return true;
        if (paramType != ArgType.VAR) {
            if (thisType != ArgType.VAR) {
                return thisType == paramType;
            }
            return ((VarArg) thisArg).getVarType() == paramType;
        }

        if (thisType != ArgType.VAR) {
            return thisType == ((VarArg) toMatch.getInternalArg()).getVarType();
        }

        return ((VarArg) thisArg).getVarType() == ((VarArg) toMatch.getInternalArg()).getVarType();
    }

    public boolean matchTypes(ArrayList<ArgType> other){
        if (getArgDataList().size() != other.size()) return false;
        for (int i = 0; i < other.size(); i++) {
            if (getArgDataList().get(i).getType() != other.get(i) && other.get(i) != ArgType.ANY) return false;
        }
        return true;
    }

    public ArrayList<CodeArg> getArgDataList(){
        return new ArrayList<>(argList);
    }

    @Override
    public JSONObject toJSON() {
        JSONArray argArr = new JSONArray();

        for(int i = 0; i < argList.size(); i++){
            if (argList.get(i) != null) {
                CodeArg arg = argList.get(i);
                argArr.put(new JSONObject()
                        .put("item", arg.toJSON())
                        .put("slot", i)
                );
            }
        }

        JSONObject args = new JSONObject();
        args.put("items", argArr);
        return args;
    }
}
