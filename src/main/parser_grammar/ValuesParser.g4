parser grammar ValuesParser;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */

options {
    tokenVocab = ValuesLexer;
}

any_item: standalone_item | complex_item;
standalone_item: simple_item | compound_item;

// very basic items.
simple_item: NUMBER | STRING_LITERAL | STYLED_TEXT;

// Items that are standalone in DF
compound_item: location | vector;

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

key: STRING_LITERAL;
