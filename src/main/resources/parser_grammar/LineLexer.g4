lexer grammar LineLexer;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */
import ValuesLexer;

IF_KEYWORD: 'if';
ELSE_KEYWORD: 'else';

COMMENT_SINGLE_LINE_OPEN: '//' -> mode(CommentMode);
COMMENT_MULTI_LINE_OPEN: '/*';
COMMENT_MULTI_LINE_CLOSE: '*/';

SCOPE_SAVED: 'saved';
SCOPE_GLOBAL: 'global';
SCOPE_LOCAL: 'local';
SCOPE_LINE: 'line';

PAR_OPEN: '(';
PAR_CLOSE: ')';
OP_VAR_ASSIGN: '=';
OP_VAR_INCREMENT: '+=';
OP_VAR_DECREMENT: '-=';
OP_ADD: '+';
OP_SUB: '-';
OP_MULT: '*';
OP_DIV: '/';
LINE_END: ';';
CALL_CHAIN_DIV: '.';
SCOPE_OPEN: '<';
SCOPE_CLOSE: '>';

COMMENT_MULTI_LINE:
 COMMENT_MULTI_LINE_OPEN (
  ANY_TEXT
  | SAFE_TEXT
  | WHITESPACES
  | .
  )*?
 COMMENT_MULTI_LINE_CLOSE;


mode CommentMode;

COMMENT_SINGLE_LINE_TEXT: ~[\r\n]* -> mode(DEFAULT_MODE);