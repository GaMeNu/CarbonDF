parser grammar CarbonDFParser;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */

import LineParser;


options {
    tokenVocab= CarbonDFLexer;
}

base: WHITESPACES? ((single_def | comment) WHITESPACES?)*;

single_def: startdef (block | LINE_END);

startdef
 : (extern_modifier WHITESPACES)? (vis_modifier WHITESPACES)?
 def_keyword WHITESPACES def_name WHITESPACES?
 '(' WHITESPACES? def_params? WHITESPACES? ')' WHITESPACES?;

vis_modifier: MOD_VISIBLE | MOD_INVISIBLE;

extern_modifier: MOD_EXTERN;

def_keyword: (FUNDEF_KEYWORD | PROCDEF_KEYWORD | EVENTDEF_KEYWORD);

def_name: SAFE_TEXT;

def_params: def_param (ARG_SEP def_param)*;

def_param
 : SAFE_TEXT
   ( WHITESPACES? ':' WHITESPACES?
   type_annotations
   )?
 ;

