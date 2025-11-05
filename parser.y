%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* AST node kinds */
typedef enum { NODE_NUM, NODE_ID, NODE_BINOP, NODE_ASSIGN, NODE_SEQ } NodeKind;

typedef struct AST {
    NodeKind kind;
    /* for NODE_NUM */
    int ival;
    /* for NODE_ID */
    char *name;
    /* for NODE_BINOP: op stored in 'op' */
    char op;
    struct AST *left, *right;
    /* next pointer for sequences */
    struct AST *next;
} AST;

AST* make_num(int v);
AST* make_id(char *s);
AST* make_binop(char op, AST *l, AST *r);
AST* make_assign(AST *idnode, AST *expr);
AST* make_seq(AST *first, AST *next);
void print_ast(AST *node, int indent);
void free_ast(AST *node);

extern int yylex(void);
extern int yyparse(void);
extern FILE *yyin;
void yyerror(const char *s);
%}

/* tokens */
%token NUMBER IDENT
%token ASSIGN SEMI LPAREN RPAREN PLUS MINUS MUL DIV

/* precedence and associativity */
%left PLUS MINUS
%left MUL DIV

%union {
    int ival;
    char *sval;
    AST *ast;
}

%type <ast> program stmt_list stmt expr term factor

%%

program:
      stmt_list               { /* root AST: $1 */ 
                                 /* store root globally via $$ */
                                 /* we'll print $1 after parse */
                                 /* pass AST to main through a global variable */
                                 extern AST *root; root = $1;
                               }
    ;

stmt_list:
      /* empty */            { $$ = NULL; }
    | stmt_list stmt         { $$ = make_seq($1, $2); }
    ;

stmt:
      IDENT ASSIGN expr SEMI { AST *idn = make_id($1); $$ = make_assign(idn, $3); free($1); }
    ;

expr:
      expr PLUS term         { $$ = make_binop('+', $1, $3); }
    | expr MINUS term        { $$ = make_binop('-', $1, $3); }
    | term                   { $$ = $1; }
    ;

term:
      term MUL factor        { $$ = make_binop('*', $1, $3); }
    | term DIV factor        { $$ = make_binop('/', $1, $3); }
    | factor                 { $$ = $1; }
    ;

factor:
      LPAREN expr RPAREN     { $$ = $2; }
    | NUMBER                 { $$ = make_num($1); }
    | IDENT                  { $$ = make_id($1); free($1); }
    ;

%%

/* Global root for AST so main can access it */
AST *root = NULL;

void yyerror(const char *s) {
    fprintf(stderr, "Parse error: %s\n", s);
}

/* AST helper implementations */

AST* make_num(int v) {
    AST *n = (AST*)malloc(sizeof(AST));
    n->kind = NODE_NUM;
    n->ival = v;
    n->name = NULL;
    n->op = 0;
    n->left = n->right = n->next = NULL;
    return n;
}

AST* make_id(char *s) {
    AST *n = (AST*)malloc(sizeof(AST));
    n->kind = NODE_ID;
    n->name = strdup(s);
    n->left = n->right = n->next = NULL;
    return n;
}

AST* make_binop(char op, AST *l, AST *r) {
    AST *n = (AST*)malloc(sizeof(AST));
    n->kind = NODE_BINOP;
    n->op = op;
    n->left = l;
    n->right = r;
    n->name = NULL;
    n->next = NULL;
    return n;
}

AST* make_assign(AST *idnode, AST *expr) {
    AST *n = (AST*)malloc(sizeof(AST));
    n->kind = NODE_ASSIGN;
    n->left = idnode;  /* left holds id */
    n->right = expr;   /* right holds expression */
    n->name = NULL;
    n->next = NULL;
    return n;
}

/* combine sequences: if first == NULL return next; else append next to end */
AST* make_seq(AST *first, AST *next) {
    if (!first) return next;
    AST *p = first;
    while (p->next) p = p->next;
    p->next = next;
    return first;
}

void print_indent(int n) {
    for (int i=0;i<n;i++) putchar(' ');
}

void print_ast(AST *node, int indent) {
    while (node) {
        print_indent(indent);
        if (!node) { printf("NULL\n"); return; }
        switch (node->kind) {
            case NODE_NUM:
                printf("NUM(%d)\n", node->ival);
                break;
            case NODE_ID:
                printf("ID(%s)\n", node->name);
                break;
            case NODE_BINOP:
                printf("BINOP(%c)\n", node->op);
                print_ast(node->left, indent+2);
                print_ast(node->right, indent+2);
                break;
            case NODE_ASSIGN:
                printf("ASSIGN\n");
                print_ast(node->left, indent+2);
                print_ast(node->right, indent+2);
                break;
            case NODE_SEQ:
                printf("SEQ\n");
                print_ast(node->left, indent+2);
                if (node->right) print_ast(node->right, indent+2);
                break;
        }
        node = node->next;
    }
}

void free_ast(AST *node) {
    if (!node) return;
    if (node->kind == NODE_ID && node->name) free(node->name);
    if (node->left) free_ast(node->left);
    if (node->right) free_ast(node->right);
    AST *next = node->next;
    free(node);
    if (next) free_ast(next);
}

/* main to run parser and print AST */
int main(int argc, char **argv) {
    if (argc > 1) {
        yyin = fopen(argv[1], "r");
        if (!yyin) { perror("fopen"); return 1; }
    }
    if (yyparse() == 0) {
        printf("Parse succeeded. AST:\n");
        print_ast(root, 0);
        free_ast(root);
    } else {
        printf("Parse failed.\n");
    }
    return 0;
}

