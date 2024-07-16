package me.gamenu.carbon.logic.listeners;

import me.gamenu.carbon.logic.args.ArgsTable;
import me.gamenu.carbon.logic.args.CodeArg;
import me.gamenu.carbon.parser.CarbonDFParser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class provides the ability to create external "functions" that actually create values.
 * for example: vector(1, 2, 3);
 */
public class InstantiatorTable {

    public class InstantiatorType {
        String name;
        ArgsTable params;
        ArgsTable returns;
        Function<CarbonDFParser.Fun_callContext, CodeArg> fn;

        public InstantiatorType(String name, ArgsTable params, ArgsTable returns, Function<CarbonDFParser.Fun_callContext, CodeArg> fn) {
            this.name = name;
            this.params = params;
            this.returns = returns;
            this.fn = fn;
        }

        public String getName() {
            return name;
        }

        public InstantiatorType setName(String name) {
            this.name = name;
            return this;
        }

        public ArgsTable getParams() {
            return params;
        }

        public InstantiatorType setParams(ArgsTable params) {
            this.params = params;
            return this;
        }

        public ArgsTable getReturns() {
            return returns;
        }

        public InstantiatorType setReturns(ArgsTable returns) {
            this.returns = returns;
            return this;
        }

        public Function<CarbonDFParser.Fun_callContext, CodeArg> getFn() {
            return fn;
        }

        public InstantiatorType setFn(Function<CarbonDFParser.Fun_callContext, CodeArg> fn) {
            this.fn = fn;
            return this;
        }
    }
    public static Map<String, Function<CarbonDFParser.Fun_callContext, CodeArg>> instantiatorMap = new HashMap<>(){{

    }};

    private static CodeArg vector(CarbonDFParser.Fun_callContext ctx){
        return null;
    }
}
