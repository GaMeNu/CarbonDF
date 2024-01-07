lexer grammar LineLexer;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */
import ValuesLexer;

IF_KEYWORD: 'if';
ELSE_KEYWORD: 'else';

COMMENT_SINGLE_LINE: '//' SAFE_TEXT LINE_END;
COMMENT_MULTI_LINE_OPEN: '/*';
COMMENT_MULTI_LINE_CLOSE: '*/';

TA_ANY: 'any';
TA_STRING: 'str';
TA_ST: 'txt';
TA_NUM: 'num';
TA_LOC: 'loc';
TA_VECT: 'vct' | 'vect';
TA_LIST: 'list';
TA_DICT: 'dict';

PAR_OPEN: '(';
PAR_CLOSE: ')';
VAR_ASSIGN: '=';
VAR_INCREMENT: '+=';
VAR_DECREMENT: '-=';
OP_ADD: '+';
OP_SUB: '-';
OP_MULT: '*';
OP_DIV: '/';
LINE_END: ';';