// Generated from C:/Users/yuvel/Desktop/Studies/Personnil/VSJava JUNK/CarbonDF/src/main/parser_grammar\CarbonDFParser.g4 by ANTLR 4.12.0
package parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CarbonDFParser}.
 */
public interface CarbonDFParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#base}.
	 * @param ctx the parse tree
	 */
	void enterBase(CarbonDFParser.BaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#base}.
	 * @param ctx the parse tree
	 */
	void exitBase(CarbonDFParser.BaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#codeline}.
	 * @param ctx the parse tree
	 */
	void enterCodeline(CarbonDFParser.CodelineContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#codeline}.
	 * @param ctx the parse tree
	 */
	void exitCodeline(CarbonDFParser.CodelineContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#def}.
	 * @param ctx the parse tree
	 */
	void enterDef(CarbonDFParser.DefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#def}.
	 * @param ctx the parse tree
	 */
	void exitDef(CarbonDFParser.DefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#funcdef}.
	 * @param ctx the parse tree
	 */
	void enterFuncdef(CarbonDFParser.FuncdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#funcdef}.
	 * @param ctx the parse tree
	 */
	void exitFuncdef(CarbonDFParser.FuncdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#procdef}.
	 * @param ctx the parse tree
	 */
	void enterProcdef(CarbonDFParser.ProcdefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#procdef}.
	 * @param ctx the parse tree
	 */
	void exitProcdef(CarbonDFParser.ProcdefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#block_content}.
	 * @param ctx the parse tree
	 */
	void enterBlock_content(CarbonDFParser.Block_contentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#block_content}.
	 * @param ctx the parse tree
	 */
	void exitBlock_content(CarbonDFParser.Block_contentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#block_line}.
	 * @param ctx the parse tree
	 */
	void enterBlock_line(CarbonDFParser.Block_lineContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#block_line}.
	 * @param ctx the parse tree
	 */
	void exitBlock_line(CarbonDFParser.Block_lineContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#any_method}.
	 * @param ctx the parse tree
	 */
	void enterAny_method(CarbonDFParser.Any_methodContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#any_method}.
	 * @param ctx the parse tree
	 */
	void exitAny_method(CarbonDFParser.Any_methodContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#normal_method}.
	 * @param ctx the parse tree
	 */
	void enterNormal_method(CarbonDFParser.Normal_methodContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#normal_method}.
	 * @param ctx the parse tree
	 */
	void exitNormal_method(CarbonDFParser.Normal_methodContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#method_arguments}.
	 * @param ctx the parse tree
	 */
	void enterMethod_arguments(CarbonDFParser.Method_argumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#method_arguments}.
	 * @param ctx the parse tree
	 */
	void exitMethod_arguments(CarbonDFParser.Method_argumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#any_item}.
	 * @param ctx the parse tree
	 */
	void enterAny_item(CarbonDFParser.Any_itemContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#any_item}.
	 * @param ctx the parse tree
	 */
	void exitAny_item(CarbonDFParser.Any_itemContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#simple_item}.
	 * @param ctx the parse tree
	 */
	void enterSimple_item(CarbonDFParser.Simple_itemContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#simple_item}.
	 * @param ctx the parse tree
	 */
	void exitSimple_item(CarbonDFParser.Simple_itemContext ctx);
	/**
	 * Enter a parse tree produced by {@link CarbonDFParser#line_sep}.
	 * @param ctx the parse tree
	 */
	void enterLine_sep(CarbonDFParser.Line_sepContext ctx);
	/**
	 * Exit a parse tree produced by {@link CarbonDFParser#line_sep}.
	 * @param ctx the parse tree
	 */
	void exitLine_sep(CarbonDFParser.Line_sepContext ctx);
}