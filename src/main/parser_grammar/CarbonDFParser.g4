parser grammar CarbonDFParser;

options {
    tokenVocab= CarbonDFLexer;
}

base: (codeline | NEWLINE)*? EOF;

codeline: def;

def: ( funcdef | procdef ) block_content;


funcdef: FUNDEF_KEYWORD SPACES SAFE_TEXT ':' NEWLINE;

procdef: PROCDEF_KEYWORD SPACES SAFE_TEXT ':' NEWLINE;


block_content: (block_line)+;

block_line: INDENT (any_method)? line_sep;

any_method: normal_method;

normal_method: SAFE_TEXT '(' method_arguments? ')';

method_arguments: any_item (SPACES? ',' (SPACES)*? any_item)*;

any_item: simple_item;
simple_item: TEXT | NUMBER;

line_sep
 : EOF
 | NEWLINE
 ;