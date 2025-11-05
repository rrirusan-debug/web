%{
#include "y.tab.h"
#include <stdlib.h>
%}

%%

[ \t\r\n]+                ;
[0-9]+                    { yylval.ival = atoi(yytext); return NUMBER; }
[a-zA-Z_][a-zA-Z0-9_]*    { yylval.sval = strdup(yytext); return IDENT; }

"="                       return ASSIGN;
";"                       return SEMI;
"("                       return LPAREN;
")"                       return RPAREN;
"+"                       return PLUS;
"-"                       return MINUS;
"*"                       return MUL;
"/"                       return DIV;

.                         { /* ignore unknown single chars for now */ }

%%

int yywrap(void) { return 1; }
