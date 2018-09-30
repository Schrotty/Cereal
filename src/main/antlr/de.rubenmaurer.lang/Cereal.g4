grammar Cereal;

@lexer::members {
    public static final int JAVA = 1;
}

/* === PARSER === */

program : brand? buy* bowl*
        ;

brand   : 'brand' BRAND
        ;

buy     : 'buy' BRAND
        ;

bowl    : visibility? 'bowl' ID block?
        ;

method  : visibility? 'ingredient' ID '(' params? ')' ('=>' type)? block
        ;

expr    : ID '(' exprList* ')'          # MethodCall
        | 'add' ID '(' exprList* ')'    # CreateNewObj
        | expr '*' expr                 # Multi
        | expr '[' expr ']'             # ArrayAccess
        | expr ('+' | '-') expr         # AddSub
        | ID                            # id
        | INTEGER                       # int
        | '(' expr ')'                  # parens
        | '(' exprList* ')'             # curlList
        | STRING                        # string
        | '<<' expr                     # allocValue
        ;

exprList: expr (',' expr)*
        ;

varDec  : type ID (expr)?
        ;

block   : '{' (stat | method)* '}'
        ;

imp     : 'important' block
        ;

pre     : 'pre' block
        ;

post    : 'post' block
        ;

stat    :
        ( varDec
        | expr
        | ret
        ) ';'
        | imp
        | pre
        | post
        | block
        ;

ret  : 'return' expr?
     ;

params  : param (',' param)*
        ;

param   : ID ':' type
        ;

type    : '_' ID BRACKETS?
        ;

visibility  : 'delicious'
            | 'rare'
            | 'common'
            ;

STRING  : '"' ('\\"'|.)*? '"'
        ;

ID      : CHAR (CHAR | DIGIT)*
        ;

CHARSTRING
        : CHAR+
        ;

INTEGER : DIGIT+
        ;

BRAND   : CHARSTRING ('.' (CHARSTRING | '*'))*
        ;

WS      : [ \r\n\t] -> skip
        ;

COMMENT : '/*' ('\\"'|.)*? '*/' -> skip
        ;

IGNORE  : '<!--' ('\\"'|.)*? '--!>' -> channel(1)
        ;

TEXT    : [a-z]
        ;

BRACKETS
        : '[]'
        ;

fragment
CHAR    : [a-zA-Z]
        ;

DIGIT   : [0-9]
        ;