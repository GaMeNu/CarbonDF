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

simple_statement: (fun_call | var_define | var_assign) LINE_END;

if_statement:
 IF_KEYWORD WHITESPACES?
 '(' ')' WHITESPACES?
 block WHITESPACES?
 (
  ELSE_KEYWORD
  WHITESPACES? block WHITESPACES?
 )?;


fun_call: fun_call_chain? single_fun_call ;

single_fun_call: SAFE_TEXT '(' call_params? ')';

single_token_call: SAFE_TEXT;

fun_call_chain: ((single_token_call) CLASS_CALL_DIV);


call_params
 : call_param
 (ARG_SEP
   call_param
 )*
 ;

call_param: standalone_item | var_name;

var_define: var_scope WHITESPACES
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

var_name: SAFE_TEXT;

var_value: (var_name | standalone_item | fun_call);

defblock: '{' WHITESPACES? (single_line WHITESPACES?)* '}';

block: '{' WHITESPACES? (single_line WHITESPACES?)* '}';


comment: COMMENT_MULTI_LINE | comment_single_line;
comment_single_line: COMMENT_SINGLE_LINE_OPEN COMMENT_SINGLE_LINE_TEXT;

