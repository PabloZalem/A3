grammar CustomLanguage;

@header{
import org.antlr.v4.runtime.*;
import java.util.*;
}

// Ações semânticas
@members {
    // Tabela de símbolos para armazenar variáveis declaradas
    Map<String, String> symbolTable = new HashMap<>();

    // Verificar se uma variável foi declarada
    boolean isDeclared(String variable) {
        return symbolTable.containsKey(variable);
    }

    // Verificar se uma variável pode ser usada de acordo com o escopo
    boolean isVariableUsable(String variable) {
        // Lógica para verificar o escopo das variáveis
        return true;
    }

    // Declarar uma variável
    void declareVariable(String variable, String type) {
        symbolTable.put(variable, type);
    }
}

// Definição das regras sintáticas
program: statement*;

statement: assignment | ifStatement | loopStatement | scanfStatement | printfStatement;

assignment: ID '=' expression ';';

assignmentDecimal: 'float' ID '=' DECIMAL ';';

assignmentString: 'string' ID '=' STRING ';';

ifStatement: 'if' '(' expression ')' block ('else' block)?;

loopStatement: whileStatement | doWhileStatement; // | forStatement;

whileStatement: 'while' '(' expression ')' block;

doWhileStatement: 'do' block 'while' '(' expression ')' ';';

// forStatement: 'for' '(' assignment? ';' expression? ';' assignment? ')' block;

block: '{' statement* '}';

scanfStatement: 'scanf' '(' ID ');';

printfStatement: 'printf' '(' STRING ');';

expression: logicalExpression;

logicalExpression: equalityExpression (('&&' | '||') equalityExpression)*;

equalityExpression: relationalExpression (('==' | '!=') relationalExpression)*;

relationalExpression: additiveExpression (('<' | '>' | '<=' | '>=') additiveExpression)*;

additiveExpression: multiplicativeExpression (('+' | '-') multiplicativeExpression)*;

multiplicativeExpression: unaryExpression (('*' | '/') unaryExpression)*;

unaryExpression: ('+' | '-') atomExpression | atomExpression;

atomExpression: ID | INT | DECIMAL | '(' expression ')';


// Definição dos tokens léxicos
ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
DECIMAL: [0-9]+ '.' [0-9]+;
STRING : '"' ~["\r\n]* '"';
WS: [ \t\r\n]+ -> skip;