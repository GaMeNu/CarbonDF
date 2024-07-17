package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.ArgType;
import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.args.CodeArg;
import me.gamenu.carbon.logic.args.FunctionParam;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class provides the ability to create external "functions" that actually create values.
 * for example: vector(1, 2, 3);
 */
public class InstantiatorTable {

    public static class InstantiatorType {
        String name;
        ArgType type;
        ArrayList<ArgsTable> paramOptions;
        ArrayList<Function<ArgsTable, CodeArg>> argSetters;

        public InstantiatorType(String name, ArgType type, ArrayList<ArgsTable> paramOptions, ArrayList<Function<ArgsTable, CodeArg>> argSetters) {
            this.name = name;
            this.type = type;
            this.paramOptions = paramOptions;
            this.argSetters = argSetters;
        }

        public String getName() {
            return name;
        }

        public InstantiatorType setName(String name) {
            this.name = name;
            return this;
        }

        public ArgType getType() {
            return type;
        }

        public InstantiatorType setType(ArgType type) {
            this.type = type;
            return this;
        }

        public ArrayList<ArgsTable> getParamOptions() {
            return paramOptions;
        }

        public InstantiatorType setParamOptions(ArrayList<ArgsTable> paramOptions) {
            this.paramOptions = paramOptions;
            return this;
        }

        public ArgsTable getDefaultParams(){
            return paramOptions.get(0);
        }

        public Function<ArgsTable, CodeArg> getArgSetter(int index) {
            return argSetters.get(index);
        }
    }
    private static final Map<String, InstantiatorType> instantiatorMap = new HashMap<>(){{
        ArrayList<ArgsTable> paramsLs;
        ArrayList<Function<ArgsTable, CodeArg>> argSetters;

        paramsLs = new ArrayList<>();
        argSetters = new ArrayList<>();
        paramsLs.add(new ArgsTable()
                .addAtFirstNull(new FunctionParam("x", new CodeArg(ArgType.NUM)))
                .addAtFirstNull(new FunctionParam("y", new CodeArg(ArgType.NUM)))
                .addAtFirstNull(new FunctionParam("z", new CodeArg(ArgType.NUM)))
        );
        argSetters.add(argsTable -> {
            CodeArg res = new CodeArg(ArgType.VECTOR);

            res.putData("x", numArgToActualNum(argsTable.get(0)));
            res.putData("y", numArgToActualNum(argsTable.get(1)));
            res.putData("z", numArgToActualNum(argsTable.get(2)));
            return res;
        });
        put("vec", new InstantiatorType("vec", ArgType.VECTOR, paramsLs, argSetters));

        paramsLs = new ArrayList<>();
        argSetters = new ArrayList<>();

        paramsLs.add(new ArgsTable()
                .addAtFirstNull(new FunctionParam("x", new CodeArg(ArgType.NUM)))
                .addAtFirstNull(new FunctionParam("y", new CodeArg(ArgType.NUM)))
                .addAtFirstNull(new FunctionParam("z", new CodeArg(ArgType.NUM)))
                .addAtFirstNull(new FunctionParam("pitch", new CodeArg(ArgType.NUM)))
                .addAtFirstNull(new FunctionParam("yaw", new CodeArg(ArgType.NUM)))
        );
        argSetters.add(argsTable -> {
            CodeArg res = new CodeArg(ArgType.LOCATION);

            JSONObject loc = new JSONObject()
                    .put("x", numArgToActualNum(argsTable.get(0)))
                    .put("y", numArgToActualNum(argsTable.get(1)))
                    .put("z", numArgToActualNum(argsTable.get(2)))
                    .put("pitch", numArgToActualNum(argsTable.get(3)))
                    .put("yaw", numArgToActualNum(argsTable.get(4)));

            res.putData("isBlock", false);
            res.putData("loc", loc);

            return res;
        });

        paramsLs.add(new ArgsTable()
                .addAtFirstNull(new FunctionParam("x", new CodeArg(ArgType.NUM)))
                .addAtFirstNull(new FunctionParam("y", new CodeArg(ArgType.NUM)))
                .addAtFirstNull(new FunctionParam("z", new CodeArg(ArgType.NUM)))
        );
        argSetters.add(argsTable -> {
            CodeArg res = new CodeArg(ArgType.LOCATION);

            JSONObject loc = new JSONObject()
                    .put("x", numArgToActualNum(argsTable.get(0)))
                    .put("y", numArgToActualNum(argsTable.get(1)))
                    .put("z", numArgToActualNum(argsTable.get(2)))
                    .put("pitch", 0)
                    .put("yaw", 0);

            res.putData("isBlock", false);
            res.putData("loc", loc);

            return res;
        });

        put("loc", new InstantiatorType("loc", ArgType.LOCATION, paramsLs, argSetters));

        paramsLs = new ArrayList<>();
        argSetters = new ArrayList<>();

        paramsLs.add(new ArgsTable()
                .addAtFirstNull(new FunctionParam("data", new CodeArg(ArgType.STRING)))
        );
        argSetters.add(argsTable -> {
            CodeArg res = new CodeArg(ArgType.ITEM);
            res.putData("item", stringArgToActualString(argsTable.get(0)));
            return res;
        });

        paramsLs.add(new ArgsTable()
                .addAtFirstNull(new FunctionParam("data", new CodeArg(ArgType.STRING)))
                .addAtFirstNull(new FunctionParam("data", new CodeArg(ArgType.NUM)))

        );
        argSetters.add(argsTable -> {
            CodeArg res = new CodeArg(ArgType.ITEM);

            String id = stringArgToActualString(argsTable.get(0));
            // if (!id.startsWith("minecraft:")) id = "minecraft:" + id;

            String resB = "{" +
                    "id:" + "\"" + id + "\"" + "," +
                    "Count:" + numArgToActualNum(argsTable.get(1)) + "b" +
                    "}";
            res.putData("item", resB);
            return res;
        });

        paramsLs.add(new ArgsTable()
                .addAtFirstNull(new FunctionParam("data", new CodeArg(ArgType.STRING)))
                .addAtFirstNull(new FunctionParam("data", new CodeArg(ArgType.NUM)))
                .addAtFirstNull(new FunctionParam("data", new CodeArg(ArgType.STRING)))
        );
        argSetters.add(argsTable -> {
            CodeArg res = new CodeArg(ArgType.ITEM);
            String id = stringArgToActualString(argsTable.get(0));
            // if (!id.startsWith("minecraft:")) id = "minecraft:" + id;
            String resB = "{" +
                    "id:" + "\"" + id + "\"" + "," +
                    "Count:" + numArgToActualNum(argsTable.get(1)) + "b" + "," +
                    "tag:" + stringArgToActualString(argsTable.get(2)) +
                    "}";
            res.putData("item", resB);
            return res;
        });

        put("item", new InstantiatorType("item", ArgType.ITEM, paramsLs, argSetters));

    }};

    public static Map<String, InstantiatorType> getInstantiatorMap() {
        return instantiatorMap;
    }

    private static Number numArgToActualNum(CodeArg arg){
        if (arg.getType() != ArgType.NUM) return 0;
        String name = (String) arg.getData("name");
        Number res;
        try {
            res = NumberFormat.getInstance().parse(name);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    private static String stringArgToActualString(CodeArg arg){
        if (arg.getType() != ArgType.STRING && arg.getType() != ArgType.STYLED_TEXT) return "";
        return (String) arg.getData("name");
    }

}
