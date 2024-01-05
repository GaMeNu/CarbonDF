lexer grammar CarbonDFLexer;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */

import ValuesLexer;

tokens { INDENT, DEDENT }


FUNDEF_KEYWORD: 'fun';
PROCDEF_KEYWORD: 'proc';
EVENTDEF_KEYWORD: 'event';
IF_KEYWORD: 'if';
ELSE_KEYWORD: 'else';


COMMENT_SINGLE_LINE: '//';
COMMENT_MULTI_LINE_OPEN: '/*';
COMMENT_MULTI_LINE_CLOSE: '*/';

TA_ANY: 'any';
TA_STRING: 'str';
TA_ST: 'txt' | 'styled' | 'styledtext' | 'text';
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

SAFE_TEXT: [A-Za-z_] [A-Za-z0-9\-_]* ;