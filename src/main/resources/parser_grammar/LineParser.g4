parser grammar LineParser;

import ValuesParser;

options {
    tokenVocab=LineLexer;
}

single_line: (simple_statement
           | compound_statement
           | comment)
           ;

compound_statement:  if_statement | repeat_statement;

simple_statement: (fun_call | var_define | var_assign) LINE_END;

if_statement:
 (IF_KEYWORD | IFNT_KEYWORD) (WHITESPACES NOT_KEYWORD)?
 WHITESPACES?
 fun_call WHITESPACES?
 block WHITESPACES?
 (
  ELSE_KEYWORD
  WHITESPACES? block WHITESPACES?
 )?;

repeat_statement:
 REPEAT_KEYWORD WHITESPACES repeat_type (WHITESPACES NOT_KEYWORD)? WHITESPACES?
 '(' (fun_call | call_params)? ')' WHITESPACES?
 block WHITESPACES?;

repeat_type: SAFE_TEXT;

fun_call: fun_call_chain? (target OBJ_SPECIFY)? single_fun_call tags?;

tags: WHITESPACES? dict WHITESPACES?;

single_token_call: SAFE_TEXT;

fun_call_chain: ((single_token_call) CLASS_CALL_DIV);

single_fun_call: SAFE_TEXT PAR_OPEN call_params? PAR_CLOSE;

call_params
 : call_param
 (ARG_SEP
   call_param
 )*
 ;

call_param: standalone_item | var_name;

var_define: (CONSTANT_KEYWORD WHITESPACES)? var_scope WHITESPACES
            var_define_name (WHITESPACES? ARG_SEP WHITESPACES? var_define_name)*
            (
              WHITESPACES OP_VAR_ASSIGN WHITESPACES?
              var_value (WHITESPACES? ARG_SEP WHITESPACES? var_value)*
            )?
          ;

var_define_name: var_name (WHITESPACES? ':' WHITESPACES? type_annotations)?;

var_assign:
            var_name (WHITESPACES? ARG_SEP var_name)*
            WHITESPACES OP_VAR_ASSIGN WHITESPACES
            var_value (WHITESPACES? ARG_SEP var_value WHITESPACES?)*
          ;

var_scope: SCOPE_SAVED | SCOPE_GLOBAL | SCOPE_LOCAL | SCOPE_LINE;

var_value: (var_name | any_item | fun_call);

defblock: '{' WHITESPACES? (single_line WHITESPACES?)* '}';

block: '{' WHITESPACES? (single_line WHITESPACES?)* '}';


comment: COMMENT_MULTI_LINE | comment_single_line;
comment_single_line: COMMENT_SINGLE_LINE_OPEN COMMENT_SINGLE_LINE_TEXT;

