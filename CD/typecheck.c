#include <stdio.h>
#include <ctype.h>

int main() {
    char expr[100];
    int i, valid = 1;

    printf("Enter an expression: ");
    fgets(expr, sizeof(expr), stdin);

    for (i = 0; expr[i] != '\0'; i++) {
        if (!isalnum(expr[i]) && expr[i] != '+' && expr[i] != '-' &&
            expr[i] != '*' && expr[i] != '/' && expr[i] != '%' && 
            expr[i] != ' ' && expr[i] != '\n') {
            valid = 0;
            break;
        }
    }

    if (valid)
        printf("Type checking successful — Expression is valid.\n");
    else
        printf("Invalid Expression!\n");

    return 0;
}
