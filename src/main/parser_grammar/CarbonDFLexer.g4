lexer grammar CarbonDFLexer;

/**
 * CarbonDF lexer
 *
 * based on the Python3 ANTLR Lexer:
 * https://github.com/antlr/grammars-v4/blob/master/python/python3/Python3Lexer.g4
 * Credits:
 *  Python parser: ANTLR
 *  CarbonDF: GaMeNu
 */

tokens { INDENT, DEDENT }

TEXT
 : STRING_LITERAL
 ;

NUMBER: '-'? DIGIT_SEQ ('.' DIGIT_SEQ)?;

FUNDEF_KEYWORD: 'fun';
PROCDEF_KEYWORD: 'proc';
IF_KEYWORD: 'if';

INDENT: '\t' | '    ';


NEWLINE
 : ( '\r'? '\n' | '\r' | '\f'
   )
 ;


METH_PAR_OPEN: '(';
ARG_SEP: ',';
METH_PAR_CLOSE: ')';
DEF_LINE_END: ':';


STRING_LITERAL
 : SHORT_STRING
 ;


SAFE_TEXT
 : SAFE_LETTER+
 ;



fragment SHORT_STRING
 : '\'' ( STRING_ESCAPE_SEQ | ~[\\\r\n\f'] )* '\''
 | '"' ( STRING_ESCAPE_SEQ | ~[\\\r\n\f"] )* '"'
 ;

fragment SAFE_LETTER
 : [A-Za-z1-9_\-]
 ;

fragment STRING_ESCAPE_SEQ
 : '\\' .
 | '\\' NEWLINE
 ;

fragment DIGIT_SEQ: DIGIT+;
fragment DIGIT: [0-9];



SPACES
 : [ \t]+
 ;