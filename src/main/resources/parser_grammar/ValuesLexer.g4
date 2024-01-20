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
STRING_DEFINE_1: '"';
STRING_DEFINE_2: '\'';
ARG_SEP: WHITESPACES? ',' WHITESPACES?;

TA_ANY: 'any';
TA_STRING: 'str';
TA_ST: 'stxt';
TA_NUM: 'num';
TA_LOC: 'loc';
TA_VECT: 'vct' | 'vect';
TA_LIST: 'list';
TA_DICT: 'dict';

NUMBER: ('-' | '+')? DIGIT_SEQ ('.' DIGIT_SEQ)?;

WHITESPACES
 :
 ( NEWLINE | '\t' | ' ')+
 ;

NEWLINE
 : (
   '\n' | '\r' | '\f' | '\r\n'
   )
 ;

SAFE_TEXT: [A-Za-z_%] [A-Za-z0-9\-_%]* ;
ANY_TEXT: '\u0020'..'\u007E';


fragment DIGIT_SEQ
 : [0-9]+
 ;


STRING_LITERAL_SIMPLE_1
: (
  STRING_DEFINE_1
   (~([\\\r\n\f'])
   | STRING_ESCAPE_SEQ
   )*
  STRING_DEFINE_1
  )
;

STRING_LITERAL_SIMPLE_2:
(
  STRING_DEFINE_2
   (~([\\\r\n\f'])
   | STRING_ESCAPE_SEQ
   )*
  STRING_DEFINE_2
  )
;


STRING_ESCAPE_SEQ
 : '\\' .
 | '\\' NEWLINE
 ;
