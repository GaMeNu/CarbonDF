parser grammar CarbonDFParser;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */
import ValuesParser;

options {
    tokenVocab= CarbonDFLexer;
}

base: startdef block;

startdef
 : (FUNDEF_KEYWORD | PROCDEF_KEYWORD)
 WHITESPACES SAFE_TEXT WHITESPACES? '(' WHITESPACES? def_params? WHITESPACES? ')' WHITESPACES?;

def_params: def_param (ARG_SEP def_param)*;

def_param
 : SAFE_TEXT
   ( WHITESPACES? ':' WHITESPACES?
   type_annotations
   )?
 ;

block: '{' WHITESPACES? single_line* WHITESPACES? '}';

single_line: (fun_call) LINE_END;

fun_call: SAFE_TEXT '(' call_params? ')';

call_params
 : (standalone_item | SAFE_TEXT)
 (ARG_SEP
   (standalone_item | SAFE_TEXT)
 )*
 ;
/*
setvar
 : SAFE_TEXT WHITESPACES?
 ( '=' WHITESPACES? )
 ;

setvar_exp
 : any_item | fun_call | single_operation;

single_operation: NUMBER WHITESPACES? (OP_ADD | OP_SUB | OP_MULT | OP_DIV) WHITESPACES? NUMBER;

var_inc: SAFE_TEXT WHITESPACES? ('+=' | '-=') NUMBER;
*/

type_annotations: (TA_ANY | TA_STRING | TA_ST | TA_NUM | TA_LOC | TA_VECT | TA_LIST | TA_DICT);