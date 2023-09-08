// Generated from C:/Users/yuvel/Desktop/Studies/Personnil/VSJava JUNK/CarbonDF/src/main/parser_grammar\CarbonDFLexer.g4 by ANTLR 4.12.0
package parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class CarbonDFLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INDENT=1, DEDENT=2, TEXT=3, NUMBER=4, FUNDEF_KEYWORD=5, PROCDEF_KEYWORD=6, 
		IF_KEYWORD=7, NEWLINE=8, METH_PAR_OPEN=9, ARG_SEP=10, METH_PAR_CLOSE=11, 
		DEF_LINE_END=12, STRING_LITERAL=13, SAFE_TEXT=14, SPACES=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"TEXT", "NUMBER", "FUNDEF_KEYWORD", "PROCDEF_KEYWORD", "IF_KEYWORD", 
			"INDENT", "NEWLINE", "METH_PAR_OPEN", "ARG_SEP", "METH_PAR_CLOSE", "DEF_LINE_END", 
			"STRING_LITERAL", "SAFE_TEXT", "SHORT_STRING", "SAFE_LETTER", "STRING_ESCAPE_SEQ", 
			"DIGIT_SEQ", "DIGIT", "SPACES"
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


	public CarbonDFLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CarbonDFLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u000f\u0082\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0001\u0000\u0001\u0000\u0001\u0001\u0003\u0001"+
		"+\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u00010\b\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005C\b"+
		"\u0005\u0001\u0006\u0003\u0006F\b\u0006\u0001\u0006\u0001\u0006\u0003"+
		"\u0006J\b\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0004\fW\b\f\u000b"+
		"\f\f\fX\u0001\r\u0001\r\u0001\r\u0005\r^\b\r\n\r\f\ra\t\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0005\rg\b\r\n\r\f\rj\t\r\u0001\r\u0003\rm\b\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003"+
		"\u000fu\b\u000f\u0001\u0010\u0004\u0010x\b\u0010\u000b\u0010\f\u0010y"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0004\u0012\u007f\b\u0012\u000b\u0012"+
		"\f\u0012\u0080\u0000\u0000\u0013\u0001\u0003\u0003\u0004\u0005\u0005\u0007"+
		"\u0006\t\u0007\u000b\u0001\r\b\u000f\t\u0011\n\u0013\u000b\u0015\f\u0017"+
		"\r\u0019\u000e\u001b\u0000\u001d\u0000\u001f\u0000!\u0000#\u0000%\u000f"+
		"\u0001\u0000\u0005\u0004\u0000\n\n\f\r\'\'\\\\\u0004\u0000\n\n\f\r\"\""+
		"\\\\\u0005\u0000--19AZ__az\u0001\u000009\u0002\u0000\t\t  \u008a\u0000"+
		"\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000"+
		"\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000"+
		"\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r"+
		"\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0001\'\u0001"+
		"\u0000\u0000\u0000\u0003*\u0001\u0000\u0000\u0000\u00051\u0001\u0000\u0000"+
		"\u0000\u00075\u0001\u0000\u0000\u0000\t:\u0001\u0000\u0000\u0000\u000b"+
		"B\u0001\u0000\u0000\u0000\rI\u0001\u0000\u0000\u0000\u000fK\u0001\u0000"+
		"\u0000\u0000\u0011M\u0001\u0000\u0000\u0000\u0013O\u0001\u0000\u0000\u0000"+
		"\u0015Q\u0001\u0000\u0000\u0000\u0017S\u0001\u0000\u0000\u0000\u0019V"+
		"\u0001\u0000\u0000\u0000\u001bl\u0001\u0000\u0000\u0000\u001dn\u0001\u0000"+
		"\u0000\u0000\u001ft\u0001\u0000\u0000\u0000!w\u0001\u0000\u0000\u0000"+
		"#{\u0001\u0000\u0000\u0000%~\u0001\u0000\u0000\u0000\'(\u0003\u0017\u000b"+
		"\u0000(\u0002\u0001\u0000\u0000\u0000)+\u0005-\u0000\u0000*)\u0001\u0000"+
		"\u0000\u0000*+\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000,/\u0003"+
		"!\u0010\u0000-.\u0005.\u0000\u0000.0\u0003!\u0010\u0000/-\u0001\u0000"+
		"\u0000\u0000/0\u0001\u0000\u0000\u00000\u0004\u0001\u0000\u0000\u0000"+
		"12\u0005f\u0000\u000023\u0005u\u0000\u000034\u0005n\u0000\u00004\u0006"+
		"\u0001\u0000\u0000\u000056\u0005p\u0000\u000067\u0005r\u0000\u000078\u0005"+
		"o\u0000\u000089\u0005c\u0000\u00009\b\u0001\u0000\u0000\u0000:;\u0005"+
		"i\u0000\u0000;<\u0005f\u0000\u0000<\n\u0001\u0000\u0000\u0000=C\u0005"+
		"\t\u0000\u0000>?\u0005 \u0000\u0000?@\u0005 \u0000\u0000@A\u0005 \u0000"+
		"\u0000AC\u0005 \u0000\u0000B=\u0001\u0000\u0000\u0000B>\u0001\u0000\u0000"+
		"\u0000C\f\u0001\u0000\u0000\u0000DF\u0005\r\u0000\u0000ED\u0001\u0000"+
		"\u0000\u0000EF\u0001\u0000\u0000\u0000FG\u0001\u0000\u0000\u0000GJ\u0005"+
		"\n\u0000\u0000HJ\u0002\f\r\u0000IE\u0001\u0000\u0000\u0000IH\u0001\u0000"+
		"\u0000\u0000J\u000e\u0001\u0000\u0000\u0000KL\u0005(\u0000\u0000L\u0010"+
		"\u0001\u0000\u0000\u0000MN\u0005,\u0000\u0000N\u0012\u0001\u0000\u0000"+
		"\u0000OP\u0005)\u0000\u0000P\u0014\u0001\u0000\u0000\u0000QR\u0005:\u0000"+
		"\u0000R\u0016\u0001\u0000\u0000\u0000ST\u0003\u001b\r\u0000T\u0018\u0001"+
		"\u0000\u0000\u0000UW\u0003\u001d\u000e\u0000VU\u0001\u0000\u0000\u0000"+
		"WX\u0001\u0000\u0000\u0000XV\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000"+
		"\u0000Y\u001a\u0001\u0000\u0000\u0000Z_\u0005\'\u0000\u0000[^\u0003\u001f"+
		"\u000f\u0000\\^\b\u0000\u0000\u0000][\u0001\u0000\u0000\u0000]\\\u0001"+
		"\u0000\u0000\u0000^a\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000"+
		"_`\u0001\u0000\u0000\u0000`b\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000"+
		"\u0000bm\u0005\'\u0000\u0000ch\u0005\"\u0000\u0000dg\u0003\u001f\u000f"+
		"\u0000eg\b\u0001\u0000\u0000fd\u0001\u0000\u0000\u0000fe\u0001\u0000\u0000"+
		"\u0000gj\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000hi\u0001\u0000"+
		"\u0000\u0000ik\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000km\u0005"+
		"\"\u0000\u0000lZ\u0001\u0000\u0000\u0000lc\u0001\u0000\u0000\u0000m\u001c"+
		"\u0001\u0000\u0000\u0000no\u0007\u0002\u0000\u0000o\u001e\u0001\u0000"+
		"\u0000\u0000pq\u0005\\\u0000\u0000qu\t\u0000\u0000\u0000rs\u0005\\\u0000"+
		"\u0000su\u0003\r\u0006\u0000tp\u0001\u0000\u0000\u0000tr\u0001\u0000\u0000"+
		"\u0000u \u0001\u0000\u0000\u0000vx\u0003#\u0011\u0000wv\u0001\u0000\u0000"+
		"\u0000xy\u0001\u0000\u0000\u0000yw\u0001\u0000\u0000\u0000yz\u0001\u0000"+
		"\u0000\u0000z\"\u0001\u0000\u0000\u0000{|\u0007\u0003\u0000\u0000|$\u0001"+
		"\u0000\u0000\u0000}\u007f\u0007\u0004\u0000\u0000~}\u0001\u0000\u0000"+
		"\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000\u0000"+
		"\u0080\u0081\u0001\u0000\u0000\u0000\u0081&\u0001\u0000\u0000\u0000\u000f"+
		"\u0000*/BEIX]_fhlty\u0080\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}