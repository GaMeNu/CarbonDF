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
 : def_keyword
 WHITESPACES def_name WHITESPACES?
 '(' WHITESPACES? def_params? WHITESPACES? ')' WHITESPACES?;

def_keyword: (FUNDEF_KEYWORD | PROCDEF_KEYWORD | EVENTDEF_KEYWORD);

def_name: SAFE_TEXT;

def_params: def_param (ARG_SEP def_param)*;

def_param
 : SAFE_TEXT
   ( WHITESPACES? ':' WHITESPACES?
   type_annotations
   )?
 ;



type_annotations: (TA_ANY | TA_STRING | TA_ST | TA_NUM | TA_LOC | TA_VECT | TA_LIST | TA_DICT);