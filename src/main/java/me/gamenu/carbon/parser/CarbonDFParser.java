// Generated from C:/Users/yuvel/Desktop/Studies/Personnil/VSJava JUNK/CarbonDF/src/main/parser_grammar\CarbonDFParser.g4 by ANTLR 4.12.0
package parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class CarbonDFParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INDENT=1, DEDENT=2, TEXT=3, NUMBER=4, FUNDEF_KEYWORD=5, PROCDEF_KEYWORD=6, 
		IF_KEYWORD=7, NEWLINE=8, METH_PAR_OPEN=9, ARG_SEP=10, METH_PAR_CLOSE=11, 
		DEF_LINE_END=12, STRING_LITERAL=13, SAFE_TEXT=14, SPACES=15;
	public static final int
		RULE_base = 0, RULE_codeline = 1, RULE_def = 2, RULE_funcdef = 3, RULE_procdef = 4, 
		RULE_block_content = 5, RULE_block_line = 6, RULE_any_method = 7, RULE_normal_method = 8, 
		RULE_method_arguments = 9, RULE_any_item = 10, RULE_simple_item = 11, 
		RULE_line_sep = 12;
	private static String[] makeRuleNames() {
		return new String[] {
			"base", "codeline", "def", "funcdef", "procdef", "block_content", "block_line", 
			"any_method", "normal_method", "method_arguments", "any_item", "simple_item", 
			"line_sep"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'fun'", "'proc'", "'if'", null, "'('", 
			"','", "')'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INDENT", "DEDENT", "TEXT", "NUMBER", "FUNDEF_KEYWORD", "PROCDEF_KEYWORD", 
			"IF_KEYWORD", "NEWLINE", "METH_PAR_OPEN", "ARG_SEP", "METH_PAR_CLOSE", 
			"DEF_LINE_END", "STRING_LITERAL", "SAFE_TEXT", "SPACES"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CarbonDFParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CarbonDFParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BaseContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CarbonDFParser.EOF, 0); }
		public List<CodelineContext> codeline() {
			return getRuleContexts(CodelineContext.class);
		}
		public CodelineContext codeline(int i) {
			return getRuleContext(CodelineContext.class,i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(CarbonDFParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(CarbonDFParser.NEWLINE, i);
		}
		public BaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_base; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterBase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitBase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitBase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseContext base() throws RecognitionException {
		BaseContext _localctx = new BaseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_base);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					setState(28);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case FUNDEF_KEYWORD:
					case PROCDEF_KEYWORD:
						{
						setState(26);
						codeline();
						}
						break;
					case NEWLINE:
						{
						setState(27);
						match(NEWLINE);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(32);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(33);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CodelineContext extends ParserRuleContext {
		public DefContext def() {
			return getRuleContext(DefContext.class,0);
		}
		public CodelineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_codeline; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterCodeline(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitCodeline(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitCodeline(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodelineContext codeline() throws RecognitionException {
		CodelineContext _localctx = new CodelineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_codeline);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			def();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefContext extends ParserRuleContext {
		public Block_contentContext block_content() {
			return getRuleContext(Block_contentContext.class,0);
		}
		public FuncdefContext funcdef() {
			return getRuleContext(FuncdefContext.class,0);
		}
		public ProcdefContext procdef() {
			return getRuleContext(ProcdefContext.class,0);
		}
		public DefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefContext def() throws RecognitionException {
		DefContext _localctx = new DefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUNDEF_KEYWORD:
				{
				setState(37);
				funcdef();
				}
				break;
			case PROCDEF_KEYWORD:
				{
				setState(38);
				procdef();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(41);
			block_content();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FuncdefContext extends ParserRuleContext {
		public TerminalNode FUNDEF_KEYWORD() { return getToken(CarbonDFParser.FUNDEF_KEYWORD, 0); }
		public TerminalNode SPACES() { return getToken(CarbonDFParser.SPACES, 0); }
		public TerminalNode SAFE_TEXT() { return getToken(CarbonDFParser.SAFE_TEXT, 0); }
		public TerminalNode DEF_LINE_END() { return getToken(CarbonDFParser.DEF_LINE_END, 0); }
		public TerminalNode NEWLINE() { return getToken(CarbonDFParser.NEWLINE, 0); }
		public FuncdefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcdef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterFuncdef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitFuncdef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitFuncdef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncdefContext funcdef() throws RecognitionException {
		FuncdefContext _localctx = new FuncdefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_funcdef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(FUNDEF_KEYWORD);
			setState(44);
			match(SPACES);
			setState(45);
			match(SAFE_TEXT);
			setState(46);
			match(DEF_LINE_END);
			setState(47);
			match(NEWLINE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProcdefContext extends ParserRuleContext {
		public TerminalNode PROCDEF_KEYWORD() { return getToken(CarbonDFParser.PROCDEF_KEYWORD, 0); }
		public TerminalNode SPACES() { return getToken(CarbonDFParser.SPACES, 0); }
		public TerminalNode SAFE_TEXT() { return getToken(CarbonDFParser.SAFE_TEXT, 0); }
		public TerminalNode DEF_LINE_END() { return getToken(CarbonDFParser.DEF_LINE_END, 0); }
		public TerminalNode NEWLINE() { return getToken(CarbonDFParser.NEWLINE, 0); }
		public ProcdefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procdef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterProcdef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitProcdef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitProcdef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcdefContext procdef() throws RecognitionException {
		ProcdefContext _localctx = new ProcdefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_procdef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(PROCDEF_KEYWORD);
			setState(50);
			match(SPACES);
			setState(51);
			match(SAFE_TEXT);
			setState(52);
			match(DEF_LINE_END);
			setState(53);
			match(NEWLINE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Block_contentContext extends ParserRuleContext {
		public List<Block_lineContext> block_line() {
			return getRuleContexts(Block_lineContext.class);
		}
		public Block_lineContext block_line(int i) {
			return getRuleContext(Block_lineContext.class,i);
		}
		public Block_contentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterBlock_content(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitBlock_content(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitBlock_content(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Block_contentContext block_content() throws RecognitionException {
		Block_contentContext _localctx = new Block_contentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_block_content);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(55);
				block_line();
				}
				}
				setState(58); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==INDENT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Block_lineContext extends ParserRuleContext {
		public TerminalNode INDENT() { return getToken(CarbonDFParser.INDENT, 0); }
		public Line_sepContext line_sep() {
			return getRuleContext(Line_sepContext.class,0);
		}
		public Any_methodContext any_method() {
			return getRuleContext(Any_methodContext.class,0);
		}
		public Block_lineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterBlock_line(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitBlock_line(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitBlock_line(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Block_lineContext block_line() throws RecognitionException {
		Block_lineContext _localctx = new Block_lineContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_block_line);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(INDENT);
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SAFE_TEXT) {
				{
				setState(61);
				any_method();
				}
			}

			setState(64);
			line_sep();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Any_methodContext extends ParserRuleContext {
		public Normal_methodContext normal_method() {
			return getRuleContext(Normal_methodContext.class,0);
		}
		public Any_methodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_any_method; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterAny_method(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitAny_method(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitAny_method(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Any_methodContext any_method() throws RecognitionException {
		Any_methodContext _localctx = new Any_methodContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_any_method);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			normal_method();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Normal_methodContext extends ParserRuleContext {
		public TerminalNode SAFE_TEXT() { return getToken(CarbonDFParser.SAFE_TEXT, 0); }
		public TerminalNode METH_PAR_OPEN() { return getToken(CarbonDFParser.METH_PAR_OPEN, 0); }
		public TerminalNode METH_PAR_CLOSE() { return getToken(CarbonDFParser.METH_PAR_CLOSE, 0); }
		public Method_argumentsContext method_arguments() {
			return getRuleContext(Method_argumentsContext.class,0);
		}
		public Normal_methodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_normal_method; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterNormal_method(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitNormal_method(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitNormal_method(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Normal_methodContext normal_method() throws RecognitionException {
		Normal_methodContext _localctx = new Normal_methodContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_normal_method);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(SAFE_TEXT);
			setState(69);
			match(METH_PAR_OPEN);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TEXT || _la==NUMBER) {
				{
				setState(70);
				method_arguments();
				}
			}

			setState(73);
			match(METH_PAR_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Method_argumentsContext extends ParserRuleContext {
		public List<Any_itemContext> any_item() {
			return getRuleContexts(Any_itemContext.class);
		}
		public Any_itemContext any_item(int i) {
			return getRuleContext(Any_itemContext.class,i);
		}
		public List<TerminalNode> ARG_SEP() { return getTokens(CarbonDFParser.ARG_SEP); }
		public TerminalNode ARG_SEP(int i) {
			return getToken(CarbonDFParser.ARG_SEP, i);
		}
		public List<TerminalNode> SPACES() { return getTokens(CarbonDFParser.SPACES); }
		public TerminalNode SPACES(int i) {
			return getToken(CarbonDFParser.SPACES, i);
		}
		public Method_argumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_arguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterMethod_arguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitMethod_arguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitMethod_arguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Method_argumentsContext method_arguments() throws RecognitionException {
		Method_argumentsContext _localctx = new Method_argumentsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_method_arguments);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			any_item();
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ARG_SEP || _la==SPACES) {
				{
				{
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SPACES) {
					{
					setState(76);
					match(SPACES);
					}
				}

				setState(79);
				match(ARG_SEP);
				setState(83);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1+1 ) {
						{
						{
						setState(80);
						match(SPACES);
						}
						} 
					}
					setState(85);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				}
				setState(86);
				any_item();
				}
				}
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Any_itemContext extends ParserRuleContext {
		public Simple_itemContext simple_item() {
			return getRuleContext(Simple_itemContext.class,0);
		}
		public Any_itemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_any_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterAny_item(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitAny_item(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitAny_item(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Any_itemContext any_item() throws RecognitionException {
		Any_itemContext _localctx = new Any_itemContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_any_item);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			simple_item();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Simple_itemContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(CarbonDFParser.TEXT, 0); }
		public TerminalNode NUMBER() { return getToken(CarbonDFParser.NUMBER, 0); }
		public Simple_itemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterSimple_item(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitSimple_item(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitSimple_item(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Simple_itemContext simple_item() throws RecognitionException {
		Simple_itemContext _localctx = new Simple_itemContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_simple_item);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			_la = _input.LA(1);
			if ( !(_la==TEXT || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Line_sepContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CarbonDFParser.EOF, 0); }
		public TerminalNode NEWLINE() { return getToken(CarbonDFParser.NEWLINE, 0); }
		public Line_sepContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line_sep; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).enterLine_sep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CarbonDFParserListener ) ((CarbonDFParserListener)listener).exitLine_sep(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CarbonDFParserVisitor ) return ((CarbonDFParserVisitor<? extends T>)visitor).visitLine_sep(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Line_sepContext line_sep() throws RecognitionException {
		Line_sepContext _localctx = new Line_sepContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_line_sep);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			_la = _input.LA(1);
			if ( !(_la==EOF || _la==NEWLINE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u000fc\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0001\u0000\u0001\u0000\u0005\u0000\u001d\b\u0000\n\u0000\f"+
		"\u0000 \t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0003\u0002(\b\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0004\u00059\b\u0005\u000b\u0005\f\u0005:\u0001\u0006\u0001\u0006"+
		"\u0003\u0006?\b\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0003\bH\b\b\u0001\b\u0001\b\u0001\t\u0001\t"+
		"\u0003\tN\b\t\u0001\t\u0001\t\u0005\tR\b\t\n\t\f\tU\t\t\u0001\t\u0005"+
		"\tX\b\t\n\t\f\t[\t\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\f\u0002\u001eS\u0000\r\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0018\u0000\u0002\u0001\u0000\u0003\u0004\u0001\u0001"+
		"\b\b^\u0000\u001e\u0001\u0000\u0000\u0000\u0002#\u0001\u0000\u0000\u0000"+
		"\u0004\'\u0001\u0000\u0000\u0000\u0006+\u0001\u0000\u0000\u0000\b1\u0001"+
		"\u0000\u0000\u0000\n8\u0001\u0000\u0000\u0000\f<\u0001\u0000\u0000\u0000"+
		"\u000eB\u0001\u0000\u0000\u0000\u0010D\u0001\u0000\u0000\u0000\u0012K"+
		"\u0001\u0000\u0000\u0000\u0014\\\u0001\u0000\u0000\u0000\u0016^\u0001"+
		"\u0000\u0000\u0000\u0018`\u0001\u0000\u0000\u0000\u001a\u001d\u0003\u0002"+
		"\u0001\u0000\u001b\u001d\u0005\b\u0000\u0000\u001c\u001a\u0001\u0000\u0000"+
		"\u0000\u001c\u001b\u0001\u0000\u0000\u0000\u001d \u0001\u0000\u0000\u0000"+
		"\u001e\u001f\u0001\u0000\u0000\u0000\u001e\u001c\u0001\u0000\u0000\u0000"+
		"\u001f!\u0001\u0000\u0000\u0000 \u001e\u0001\u0000\u0000\u0000!\"\u0005"+
		"\u0000\u0000\u0001\"\u0001\u0001\u0000\u0000\u0000#$\u0003\u0004\u0002"+
		"\u0000$\u0003\u0001\u0000\u0000\u0000%(\u0003\u0006\u0003\u0000&(\u0003"+
		"\b\u0004\u0000\'%\u0001\u0000\u0000\u0000\'&\u0001\u0000\u0000\u0000("+
		")\u0001\u0000\u0000\u0000)*\u0003\n\u0005\u0000*\u0005\u0001\u0000\u0000"+
		"\u0000+,\u0005\u0005\u0000\u0000,-\u0005\u000f\u0000\u0000-.\u0005\u000e"+
		"\u0000\u0000./\u0005\f\u0000\u0000/0\u0005\b\u0000\u00000\u0007\u0001"+
		"\u0000\u0000\u000012\u0005\u0006\u0000\u000023\u0005\u000f\u0000\u0000"+
		"34\u0005\u000e\u0000\u000045\u0005\f\u0000\u000056\u0005\b\u0000\u0000"+
		"6\t\u0001\u0000\u0000\u000079\u0003\f\u0006\u000087\u0001\u0000\u0000"+
		"\u00009:\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000:;\u0001\u0000"+
		"\u0000\u0000;\u000b\u0001\u0000\u0000\u0000<>\u0005\u0001\u0000\u0000"+
		"=?\u0003\u000e\u0007\u0000>=\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000"+
		"\u0000?@\u0001\u0000\u0000\u0000@A\u0003\u0018\f\u0000A\r\u0001\u0000"+
		"\u0000\u0000BC\u0003\u0010\b\u0000C\u000f\u0001\u0000\u0000\u0000DE\u0005"+
		"\u000e\u0000\u0000EG\u0005\t\u0000\u0000FH\u0003\u0012\t\u0000GF\u0001"+
		"\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000"+
		"IJ\u0005\u000b\u0000\u0000J\u0011\u0001\u0000\u0000\u0000KY\u0003\u0014"+
		"\n\u0000LN\u0005\u000f\u0000\u0000ML\u0001\u0000\u0000\u0000MN\u0001\u0000"+
		"\u0000\u0000NO\u0001\u0000\u0000\u0000OS\u0005\n\u0000\u0000PR\u0005\u000f"+
		"\u0000\u0000QP\u0001\u0000\u0000\u0000RU\u0001\u0000\u0000\u0000ST\u0001"+
		"\u0000\u0000\u0000SQ\u0001\u0000\u0000\u0000TV\u0001\u0000\u0000\u0000"+
		"US\u0001\u0000\u0000\u0000VX\u0003\u0014\n\u0000WM\u0001\u0000\u0000\u0000"+
		"X[\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000"+
		"\u0000Z\u0013\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000\\]\u0003"+
		"\u0016\u000b\u0000]\u0015\u0001\u0000\u0000\u0000^_\u0007\u0000\u0000"+
		"\u0000_\u0017\u0001\u0000\u0000\u0000`a\u0007\u0001\u0000\u0000a\u0019"+
		"\u0001\u0000\u0000\u0000\t\u001c\u001e\':>GMSY";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}