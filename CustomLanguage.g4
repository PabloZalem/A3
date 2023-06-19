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
	| variableDeclaration
	| ifStatement
	| loopStatement
	| scanfStatement
	| printStatement;

variableDeclaration: type=('int' | 'float' | 'string' | 'bool') id=ID '=' mathExpression
 ';' {pele.declararVariavel($id.getText(), $type.getText());};

assignment: id=ID '=' mathExpression ';' {pele.atribuirVariavel($id.getText());};

mathExpression: (value = INT {pele.adicionaBuffer($value.getText());} | value = DECIMAL {pele.adicionaBuffer($value.getText());} | value = STRING {pele.adicionaBuffer($value.getText());}) (value=OPMATH {pele.adicionaBuffer($value.getText());} (value = INT {pele.adicionaBuffer($value.getText());} | value = DECIMAL {pele.adicionaBuffer($value.getText());} | value = STRING {pele.adicionaBuffer($value.getText());}))*;

atom: ID | INT | DECIMAL | STRING | BOOL | '(' expression ')';

end: 'fine' {pele.printFim();};

ifStatement: 'se' '(' logicExpression ')' block ('altrimenti' block)?;

loopStatement: whileStatement | doWhileStatement | forStatement;

whileStatement: 'mentre' '(' logicExpression ')' block;

doWhileStatement: 'fare' block 'mentre' '(' logicExpression ')' ';';

forStatement:
	'per' '(' assignment? ';' logicExpression? ';' assignment? ')' block;

block: '{' statement* '}';

scanfStatement: 'leggere' '(' id = ID  ')' ';' {pele.scanfString($id.getText());};

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
OPMATH: '+' | '-' | '*' | '/';
AND: '&&';
OR: '||';