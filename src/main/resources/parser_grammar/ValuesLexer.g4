lexer grammar ValuesLexer;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */

LIST_OPEN: '[';
LIST_CLOSE: ']';
DICT_OPEN: '{';
DICT_CLOSE: '}';
DICT_KEYVAL: ':';
ARG_SEP: WHITESPACES? ',' WHITESPACES?;

TA_ANY: 'any';
TA_STRING: 'str';
TA_ST: 'txt' | 'styled' | 'styledtext' | 'text';
TA_NUM: 'num';
TA_LOC: 'loc';
TA_VECT: 'vct' | 'vect';
TA_LIST: 'list';
TA_DICT: 'dict';

NUMBER: ('-' | '+')? DIGIT_SEQ ('.' DIGIT_SEQ)?;

STYLED_TEXT
 : TA_ST SHORT_STRING
 ;

STRING_LITERAL
 : SHORT_STRING
 ;

WHITESPACES
 :
 ( NEWLINE | '\t' | ' ')+
 ;

NEWLINE
 : (
   '\n' | '\r' | '\f' | '\r\n'
   )
 ;


ANY_TEXT: '\u0020'..'\u007E';

fragment SHORT_STRING
: '\'' ( STRING_ESCAPE_SEQ | ~[\\\r\n\f'] )* '\''
| '"' ( STRING_ESCAPE_SEQ | ~[\\\r\n\f"] )* '"'
;

fragment STRING_ESCAPE_SEQ
 : '\\' .
 | '\\' NEWLINE
 ;

fragment DIGIT_SEQ
 : [0-9]+
 ;