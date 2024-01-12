parser grammar LineParser;

import ValuesParser;

options {
    tokenVocab=LineLexer;
}

single_line: (simple_statement
           | compound_statement
           | comment)
           ;

compound_statement:  if_statement;

simple_statement: (fun_call | var_assign) LINE_END;

if_statement:
 IF_KEYWORD WHITESPACES?
 '(' ')' WHITESPACES?
 block WHITESPACES?
 (
  ELSE_KEYWORD
  WHITESPACES? block WHITESPACES?
 )?;


fun_call: fun_call_chain* single_fun_call ;

single_fun_call: SAFE_TEXT '(' call_params? ')';

single_token_call: SAFE_TEXT;

fun_call_chain: ((single_fun_call | single_token_call) '.');


call_params
 : (standalone_item | SAFE_TEXT)
 (ARG_SEP
   (standalone_item | SAFE_TEXT)
 )*
 ;

var_assign: var_scope WHITESPACES var_name WHITESPACES? VAR_ASSIGN WHITESPACES? var_value;

var_scope: SCOPE_SAVED | SCOPE_GLOBAL | SCOPE_LOCAL | SCOPE_LINE;

var_name: SAFE_TEXT;

var_value: (var_name | standalone_item | fun_call);

block: '{' WHITESPACES? (single_line WHITESPACES?)* '}';


comment: COMMENT_MULTI_LINE | comment_single_line;
comment_single_line: COMMENT_SINGLE_LINE_OPEN COMMENT_SINGLE_LINE_TEXT;

