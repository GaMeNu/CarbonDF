parser grammar CarbonDFParser;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */
import ValuesParser;

options {
    tokenVocab= CarbonDFLexer;
}

base: (startdef block WHITESPACES?)*;

startdef
 : def_keyword
 WHITESPACES def_name WHITESPACES?
 '(' WHITESPACES? def_params? WHITESPACES? ')' WHITESPACES?;

def_keyword: (FUNDEF_KEYWORD | PROCDEF_KEYWORD);

def_name: SAFE_TEXT;

def_params: def_param (ARG_SEP def_param)*;

def_param
 : SAFE_TEXT
   ( WHITESPACES? ':' WHITESPACES?
   type_annotations
   )?
 ;

block: '{' WHITESPACES? single_line* WHITESPACES? '}';

single_line: (fun_call LINE_END) | compound_statement;

fun_call: SAFE_TEXT '(' call_params? ')';

call_params
 : (standalone_item | SAFE_TEXT)
 (ARG_SEP
   (standalone_item | SAFE_TEXT)
 )*
 ;

compound_statement:  if_statement;

if_statement:
 IF_KEYWORD WHITESPACES?
 '(' ')' WHITESPACES?
 block WHITESPACES?
 (
  ELSE_KEYWORD
  WHITESPACES? block WHITESPACES?
 )?;

type_annotations: (TA_ANY | TA_STRING | TA_ST | TA_NUM | TA_LOC | TA_VECT | TA_LIST | TA_DICT);