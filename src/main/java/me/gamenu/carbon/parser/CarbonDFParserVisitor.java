// Generated from C:/Users/yuvel/Desktop/Studies/Personnil/VSJava JUNK/CarbonDF/src/main/parser_grammar\CarbonDFParser.g4 by ANTLR 4.12.0
package parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CarbonDFParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CarbonDFParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#base}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase(CarbonDFParser.BaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#codeline}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCodeline(CarbonDFParser.CodelineContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDef(CarbonDFParser.DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#funcdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncdef(CarbonDFParser.FuncdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#procdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcdef(CarbonDFParser.ProcdefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#block_content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock_content(CarbonDFParser.Block_contentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#block_line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock_line(CarbonDFParser.Block_lineContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#any_method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny_method(CarbonDFParser.Any_methodContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#normal_method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormal_method(CarbonDFParser.Normal_methodContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#method_arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod_arguments(CarbonDFParser.Method_argumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#any_item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAny_item(CarbonDFParser.Any_itemContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#simple_item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_item(CarbonDFParser.Simple_itemContext ctx);
	/**
	 * Visit a parse tree produced by {@link CarbonDFParser#line_sep}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine_sep(CarbonDFParser.Line_sepContext ctx);
}