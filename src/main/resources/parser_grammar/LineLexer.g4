lexer grammar LineLexer;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */
import ValuesLexer;

IF_KEYWORD: 'if';
IFNT_KEYWORD: 'ifn\'t';
REPEAT_KEYWORD: 'repeat';
ELSE_KEYWORD: 'else';
NOT_KEYWORD: 'not';
CONSTANT_KEYWORD: 'final';

COMMENT_SINGLE_LINE_OPEN: '//' -> mode(CommentMode);
COMMENT_MULTI_LINE_OPEN: '/*';
COMMENT_MULTI_LINE_CLOSE: '*/';

SCOPE_SAVED: 'saved';
SCOPE_GLOBAL: 'global';
SCOPE_LOCAL: 'local';
SCOPE_LINE: 'line';


OP_VAR_ASSIGN: '=';
OP_VAR_INCREMENT: '+=';
OP_VAR_DECREMENT: '-=';
LINE_END: ';';
CLASS_CALL_DIV: '::';

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