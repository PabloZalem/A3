grammar CustomLanguage;

@header {
import org.antlr.v4.runtime.*;
import java.util.*;
}

// Ações semânticas
@members {
  Pele pele = new Pele();
}

// Definição das regras sintáticas
program: start statement* end {pele.writeCode();};

start: 'inizio' {pele.printInicio();};

statement:
	assignment
	| ifStatement
	| loopStatement
	| scanfStatement
	| printStatement;

// The method that assigns variables is called declararVariavel and it takes the following parameters:
// 1. The name of the variable
// 2. The value of the variable
// 3. The type of the variable (int, float, string, bool)

assignment: type=('int' | 'float' | 'string' | 'bool') id=ID '=' (value = INT | value = DECIMAL | value = STRING | value = BOOL | value=expression) ';' {pele.declararVariavel($id.getText(), $value.getText(), $type.getText());};

expression: atom (op=('*' | '/' | '+' | '-') atom)*;

atom: ID | INT | DECIMAL | STRING | BOOL | '(' expression ')';

end: 'fine' {pele.printFim();};

ifStatement: 'if' '(' expression ')' block ('else' block)?;

loopStatement: whileStatement | doWhileStatement | forStatement;

whileStatement: 'while' '(' expression ')' block;

doWhileStatement: 'do' block 'while' '(' expression ')' ';';

forStatement:
	'for' '(' assignment? ';' expression? ';' assignment? ')' block;

block: '{' statement* '}';

scanfStatement: 'leggere' '(' ID ')';

printStatement:
	'scrivere' '(' (id = ID | str = STRING) ')' ';' {pele.printString($id != null ? $id.getText() : $str.getText());
		};

BOOL: 'vero' {pele.BOOL("vero");} | 'falso' {pele.BOOL("falso");};

// Definição dos tokens léxicos
ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
DECIMAL: [0-9]+ '.' [0-9]+;
STRING: '"' ~["\r\n]* '"';
OPREL: '>' | '<' | '>=' | '<=' | '==' | '!=';
WS: [ \t\r\n]+ -> skip;
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
AND: '&&';
OR: '||';