parser grammar CarbonDFParser;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */

import LineParser;


options {
    tokenVocab=CarbonDFLexer;
}

base: WHITESPACES? ((single_def | comment) WHITESPACES?)*;

single_def: startdef (defblock | LINE_END);

startdef
 : (modifiers WHITESPACES)*
 def_keyword WHITESPACES def_name WHITESPACES?
 '(' WHITESPACES? def_params? WHITESPACES? ')' WHITESPACES? (RETURN_ARROW WHITESPACES? '(' ret_params? ')' WHITESPACES?)?;

modifiers: vis_modifier
         | extern_modifier
         | ls_cancel_modifier
         ;

vis_modifier: MOD_VISIBLE | MOD_INVISIBLE;

extern_modifier: MOD_EXTERN;

ls_cancel_modifier: MOD_LS_CANCEL;

def_keyword: (FUNDEF_KEYWORD | PROCDEF_KEYWORD | EVENTDEF_KEYWORD);

def_name: SAFE_TEXT;

def_params: def_param (ARG_SEP def_param)*;

ret_params: def_param (ARG_SEP def_param)*;

def_param
 : param_options?
   SAFE_TEXT
   ( WHITESPACES? ':' WHITESPACES? type_annotations )?
   ( WHITESPACES? '=' WHITESPACES? standalone_item)?
 ;

param_options: (PARAM_PLURAL | PARAM_OPTIONAL)+;

