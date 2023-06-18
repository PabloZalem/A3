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

assignment: type=('int' | 'float' | 'string' | 'bool') id=ID '=' (value = INT | value = DECIMAL | value = STRING | value = BOOL | value=expression) ';' {pele.declararVariavel($id.getText(), $value.getText(), $type.getText());};

expression: atom (op=('*' | '/' | '+' | '-') atom)*;

atom: ID | INT | DECIMAL | STRING | BOOL | '(' expression ')';

end: 'fine' {pele.printFim();};

ifStatement: 'se' '(' expression ')' block ('altrimenti' block)?;

loopStatement: whileStatement | doWhileStatement | forStatement;

whileStatement: 'mentre' '(' expression ')' block;

doWhileStatement: 'fare' block 'mentre' '(' expression ')' ';';

forStatement:
	'per' '(' assignment? ';' expression? ';' assignment? ')' block;

block: '{' statement* '}';

scanfStatement: 'leggere' '(' ID ')';

printStatement:
	'scrivere' '(' (id = ID | str = STRING) ')' ';' {pele.printString($id != null ? $id.getText() : $str.getText());
		};

BOOL: 'vero' | 'falso';

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