parser grammar ValuesParser;
/**
 * CarbonDF Parser
 * (C) GaMeNu under GPLv3
 */

options {
    tokenVocab = ValuesLexer;
}

any_item: standalone_item | complex_item | var_name;
standalone_item: simple_item | instantiator ;
simple_item: number | simple_string | styled_text | game_value;

instantiator: (SAFE_TEXT | type_annotations) INST_OPEN inst_params? INST_CLOSE;

inst_params: inst_param
           ( ARG_SEP inst_param
           )*
           ;

inst_param: simple_item;

// very basic items.

game_value: target OBJ_SPECIFY var_name;

// Items that don't exist standalone in DF, and can only live in vars
complex_item: list | dict;

target: target_any | target_player | target_entity;

target_any: TARGET_DEFAULT
          | TARGET_SELECTION
          | TARGET_KILLER
          | TARGET_DAMAGER
          | TARGET_SHOOTER
          | TARGET_VICTIM
          | TARGET_GAME
          ;

target_player: TARGET_ALL_PLAYERS
             ;

target_entity: TARGET_PROJECTILE
             | TARGET_ALL_MOBS
             | TARGET_LAST_SPAWNED_ENTITY
             ;

list:
 TA_LIST?
 LIST_OPEN WHITESPACES?
 (any_item (ARG_SEP any_item)* )?
 WHITESPACES? LIST_CLOSE
 ;

dict:
 DICT_OPEN WHITESPACES?
 ( dict_pair (ARG_SEP dict_pair)* )?
 WHITESPACES? DICT_CLOSE;

dict_pair: key WHITESPACES? ':' WHITESPACES? any_item;

key: simple_string;

simple_string: STRING_LITERAL_SIMPLE_1;

styled_text: STRING_LITERAL_SIMPLE_2;

number: NUMBER;

var_name: SAFE_TEXT;

type_annotations: (TA_ANY | TA_STRING | TA_ST | TA_NUM | TA_LOC | TA_VECT | TA_LIST | TA_DICT | TA_VAR);
