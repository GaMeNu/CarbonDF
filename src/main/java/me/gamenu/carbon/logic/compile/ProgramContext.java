package me.gamenu.carbon.logic.compile;

import me.gamenu.carbon.logic.args.FunTable;
import me.gamenu.carbon.logic.args.VarTable;
import me.gamenu.carbon.logic.blocks.BlocksTable;
import org.antlr.v4.runtime.Parser;

public class ProgramContext {
    Parser parser;
    FunTable funTable;
    VarTable varTable;
    BlocksTable currentDefTable;

    public ProgramContext(Parser parser, FunTable funTable, VarTable varTable, BlocksTable currentDefTable) {
        this.parser = parser;
        this.funTable = funTable;
        this.varTable = varTable;
        this.currentDefTable = currentDefTable;
    }

    public ProgramContext(){

    }

    public BlocksTable getCurrentDefTable() {
        return currentDefTable;
    }

    public ProgramContext setCurrentDefTable(BlocksTable currentDefTable) {
        this.currentDefTable = currentDefTable;
        return this;
    }

    public Parser getParser() {
        return parser;
    }

    public ProgramContext setParser(Parser parser) {
        this.parser = parser;
        return this;
    }

    public FunTable getFunTable() {
        return funTable;
    }

    public ProgramContext setFunTable(FunTable funTable) {
        this.funTable = funTable;
        return this;
    }

    public VarTable getVarTable() {
        return varTable;
    }

    public ProgramContext setVarTable(VarTable varTable) {
        this.varTable = varTable;
        return this;
    }
}
