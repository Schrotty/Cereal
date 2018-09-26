grammar Cereal;

/* === PARSER === */

program : bowl
        ;

bowl    : 'bowl' ID block?
        ;

method  : type? fixed? 'ingredient' ID '(' params? ')' block
        ;

expr    : ID '(' exprList* ')'
        | expr '*' expr
        | expr '[' expr ']'
        | expr ('+' | '-') expr
        | ID
        | INTEGER
        | '(' expr ')'
        | STRING
        ;

exprList: expr (',' expr)*
        ;

varDec  : type ID ('<' expr)? ';'
        ;

block   : '{' stat* '}'
        ;

stat    : block
        | varDec
        | expr '<' expr
        | expr ';'
        | method
        | 'return' expr ';'
        ;

params  : param (',' param)*
        ;

param   : ID ':' type ('[]')*
        ;

type    : '_' ('string' | 'int' | 'void')
        ;

fixed   : 'fixed'
        ;

/* === LEXER === */

STRING  : '"' ('\\"'|.)*? '"'
        ;

ID  : CHAR (CHAR | DIGIT)*
    ;

CHARSTRING  : CHAR+
            ;

INTEGER : DIGIT+
        ;

fragment
CHAR  : [a-zA-Z]
      ;

fragment
DIGIT   : [0-9]
      ;

WS  : [ \r\n\t] -> skip
    ;