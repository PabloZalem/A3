grammar CustomLanguage;

@header{
import org.antlr.v4.runtime.*;
import java.util.*;
}

// Ações semânticas
@members {
  Rules regras = new Rules();

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
program: start statement* end {regras.writeCode();};

statement: assignment | ifStatement | loopStatement | scanfStatement | printStatement;

assignment returns [String type] 
    : t=typeDeclaration ID '=' e=expression ';' 
      {
        if (symbolTable.containsKey($ID.text)) {
          String varType = symbolTable.get($ID.text);
          if (varType.equals($t.text)) {
            symbolTable.put($ID.text, $t.text);
            $type = $t.text;
          } else {
            throw new RuntimeException("Type mismatch for variable " + $ID.text);
          }
        } else {
          declareVariable($ID.text, $t.text);
          $type = $t.text;
        }
      };

start: 'inicio' {regras.printInicio();};

end: 'fim' {regras.printFim();};

typeDeclaration: 'int' | 'float' | 'string' | 'bool';

ifStatement: 'if' '(' expression ')' block ('else' block)?;

loopStatement: whileStatement | doWhileStatement | forStatement;

whileStatement: 'while' '(' expression ')' block;

doWhileStatement: 'do' block 'while' '(' expression ')' ';';

forStatement: 'for' '(' assignment? ';' expression? ';' assignment? ')' block;

block: '{' statement* '}';

scanfStatement: 'leggere' '(' ID ')';

printStatement: 'scrivere' '(' (id=ID | str=STRING) ')' ';' {regras.printString($id != null ? $id.getText() : $str.getText());};

expression: logicalExpression;

logicalExpression: equalityExpression (('&&' | '||') equalityExpression)*;

equalityExpression: relationalExpression (('==' | '!=') relationalExpression)*;

relationalExpression: additiveExpression (('<' | '>' | '<=' | '>=') additiveExpression)*;

additiveExpression: multiplicativeExpression (('+' | '-') multiplicativeExpression)*;

multiplicativeExpression: unaryExpression (('*' | '/') unaryExpression)*;

unaryExpression: ('+' | '-') atomExpression | atomExpression;

atomExpression: ID | (INT | DECIMAL | STRING | BOOL) | '(' expression ')';

BOOL: 'true' | 'false';

// Definição dos tokens léxicos
ID: [a-zA-Z][a-zA-Z0-9_]*;
INT: [0-9]+;
DECIMAL: [0-9]+ '.' [0-9]+;
STRING : '"' ~["\r\n]* '"';
OPREL: '>' | '<' | '>=' | '<=' | '==' | '!=' ;
WS: [ \t\r\n]+ -> skip;