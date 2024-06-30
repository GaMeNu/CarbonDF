parser grammar ValuesParser;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */

options {
    tokenVocab = ValuesLexer;
}

any_item: standalone_item | complex_item | var_name;
standalone_item: simple_item;

// very basic items.
simple_item: number | simple_string | styled_text;

// Items that don't exist standalone in DF, and can only live in vars
complex_item: list | dict;


list:
 TA_LIST?
 LIST_OPEN WHITESPACES?
 (any_item (ARG_SEP any_item)* )?
 WHITESPACES? LIST_CLOSE
 ;

location:
 TA_LOC
 LIST_OPEN WHITESPACES?
 ( NUMBER ARG_SEP NUMBER ARG_SEP NUMBER
   (ARG_SEP NUMBER ARG_SEP NUMBER)?
 )
 WHITESPACES? LIST_CLOSE
 ;

vector:
 TA_VECT
 LIST_OPEN WHITESPACES?
 ( NUMBER ARG_SEP NUMBER ARG_SEP NUMBER)
 WHITESPACES? LIST_CLOSE
 ;

dict:
 DICT_OPEN WHITESPACES?
 ( dict_pair (ARG_SEP dict_pair)* )?
 WHITESPACES? DICT_CLOSE;

dict_pair: key WHITESPACES? ':' WHITESPACES? any_item;

simple_string
: STRING_LITERAL_SIMPLE_1
;

styled_text
: STRING_LITERAL_SIMPLE_2
;

number: NUMBER;

var_name: SAFE_TEXT;

type_annotations: (TA_ANY | TA_STRING | TA_ST | TA_NUM | TA_LOC | TA_VECT | TA_LIST | TA_DICT | TA_VAR);

key: simple_string;
