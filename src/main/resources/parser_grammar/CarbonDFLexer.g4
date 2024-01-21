lexer grammar CarbonDFLexer;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */

import LineLexer;

tokens { INDENT, DEDENT }


FUNDEF_KEYWORD: 'fun';
PROCDEF_KEYWORD: 'proc';
EVENTDEF_KEYWORD: 'event';

MOD_VISIBLE: 'shown';
MOD_INVISIBLE: 'hidden';

MOD_EXTERN: 'extern';