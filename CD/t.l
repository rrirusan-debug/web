%{
#include <stdio.h>
#include <ctype.h>

int flag = 0;
%}

%%
[a-zA-Z_][a-zA-Z0-9_]*      { printf("Identifier: %s\n", yytext); }
[0-9]+(\.[0-9]+)?           { printf("Number: %s\n", yytext); }
[+\-*/%=]                   { printf("Operator: %s\n", yytext); }
[ \t\n]+                    ;   // Ignore whitespace
.                           { printf("Invalid character: %s\n", yytext); flag = 1; }
%%

int main()
{
    printf("Enter an expression: ");
    yylex();

    if(flag == 0)
        printf("Type checking successful — Expression is valid.\n");
    else
        printf("Invalid Expression!\n");

    return 0;
}

int yywrap()
{
    return 1;
}
