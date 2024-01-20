parser grammar CarbonDFParser;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */

import LineParser;


options {
    tokenVocab= CarbonDFLexer;
}

base: WHITESPACES? (((startdef block) | comment) WHITESPACES?)*;

startdef
 : (def_modifiers WHITESPACES)? def_keyword
 WHITESPACES def_name WHITESPACES?
 '(' WHITESPACES? def_params? WHITESPACES? ')' WHITESPACES?;

def_modifiers: vis_modifier;

vis_modifier: MOD_VISIBLE | MOD_INVISIBLE;

def_keyword: (FUNDEF_KEYWORD | PROCDEF_KEYWORD | EVENTDEF_KEYWORD);

def_name: SAFE_TEXT;

def_params: def_param (ARG_SEP def_param)*;

def_param
 : SAFE_TEXT
   ( WHITESPACES? ':' WHITESPACES?
   type_annotations
   )?
 ;

