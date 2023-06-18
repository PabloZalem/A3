import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Pele {

    String code = "";
    // Tabela de símbolos para armazenar variáveis declaradas
    Map<String, String> symbolTable = new HashMap<>();

    public Pele() {
        this.code = "";
        this.symbolTable = new HashMap<>();
    }
    
    // Method that prints a string
    public void printString(String str) {
        code += "System.out.println(" + str + ");";
    }

    // Method that adds public class and private main method
    public void printInicio() {
        code += "public class Output {\n";
        code += "public static void main(String[] args) {\n";
    }

    // Method that closes the public class and private main method
    public void printFim() {
        code += "}\n";
        code += "}\n";
    }

    protected void declararVariavel(String nome, String valor, String tipo) {
        // Implementação do declararVariavel de acordo com a sua definição
        // Verifica se a variável já foi declarada
        boolean isdeclared = checkVariableDeclared(nome);

        if (isdeclared) {
            throw new IllegalArgumentException("Variável já declarada: " + nome);
        }

        // Verifica se o valor é compatível com o tipo
        if (valor != null) {
            boolean isValidType = checkVariableType(valor, tipo);
            if (!isValidType) {
                throw new IllegalArgumentException("Valor incompatível da variável: " + nome);
            }
        }

        // Adiciona a variavel na tabela de simbolos
        this.symbolTable.put(valor, tipo);
        // Adiciona a variavel no código
        if (tipo.equals("bool")) {
            valor = getValidJavaBoolean(valor);
            tipo = "boolean";
        }
        code += tipo + " " + nome + " = " + valor + ";";
    }

    private String getValidJavaBoolean(String value) {
        if (value.equals("vero")) {
            return "true";
        } else if (value.equals("falso")) {
            return "false";
        } else {
            return "false";
        }
    }

    private boolean checkVariableType(String valor, String tipo){
        if (tipo.equals("int")) {
            try {
                Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (tipo.equals("float")) {
            try {
                Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (tipo.equals("string")) {
            if (valor.charAt(0) != '"' || valor.charAt(valor.length() - 1) != '"') {
                return false;
            }
        } else if (tipo.equals("bool")) {
            if (!valor.equals("vero") && !valor.equals("falso")) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    // Método que verifica se a variável já foi declarada
    private boolean checkVariableDeclared(String nome) {
        if (!this.symbolTable.containsKey(nome)) {
            return false;
        } else {
            return true;
        }
    }

    // Method that verify if true or false
    public String BOOL(String input) {
        String value = input.trim().toLowerCase();
        if (value.equals("vero")) {
            return "vero";
        } else if (value.equals("falso")) {
            return "falso";
        } else {
            throw new IllegalArgumentException("Valor BOOL inválido: " + input);
        }
    }

    //Metodos para alguns pontos da gramatica sintatica
        private void logicalExpression() {
        equalityExpression();
        while (matchTerminal("AND") || matchTerminal("OR")) {
            if (matchTerminal("AND")) {
                equalityExpression();
            } else {
                throw new RuntimeException("Expected 'AND'");
            }
        }
    }
    
    private void equalityExpression() {
        relationalExpression();
        while (matchTerminal("OPREL")) {
            if (matchTerminal("OPREL")) {
                relationalExpression();
            } else {
                throw new RuntimeException("Expected relational operator");
            }
        }
    }
    
    private void relationalExpression() {
        additiveExpression();
        if (matchTerminal("OPREL")) {
            if (matchTerminal("OPREL")) {
                additiveExpression();
            } else {
                throw new RuntimeException("Expected relational operator");
            }
        } else {
            throw new RuntimeException("Expected relational operator");
        }
    }
    
    private void additiveExpression() {
        multiplicativeExpression();
        while (matchTerminal("ADD") || matchTerminal("SUB")) {
            if (matchTerminal("ADD")) {
                multiplicativeExpression();
            } else {
                throw new RuntimeException("Expected '+'");
            }
        }
    }
    
    private void multiplicativeExpression() {
        unaryExpression();
        while (matchTerminal("MUL") || matchTerminal("DIV")) {
            if (matchTerminal("MUL")) {
                unaryExpression();
            } else {
                throw new RuntimeException("Expected '*'");
            }
        }
    }
    
    private void unaryExpression() {
        if (matchTerminal("ADD") || matchTerminal("SUB")) {
            if (matchTerminal("ADD")) {
                atomExpression();
            } else {
                throw new RuntimeException("Expected '+'");
            }
        } else {
            atomExpression();
        }
    }
    
    private void atomExpression() {
        if (matchID()) {
            return;
        }
        
        if (matchTerminal("INT") || matchTerminal("DECIMAL") || matchTerminal("STRING") 
        || matchTerminal("BOOL")) {
            return;
        }
        
        if (matchTerminal("(")) {
            logicalExpression();
            if (matchTerminal(")")) {
                return;
            } else {
                throw new RuntimeException("Expected ')'");
            }
        }
        
        throw new RuntimeException("Invalid expression");
    }
    
    private boolean matchID() {
        // Implementação do matchID de acordo com a sua definição
        return true;
    }
    
    private boolean matchTerminal(String terminal) {
        // Implementação do matchTerminal de acordo com a sua definição
        return true;
    }

    private void grammarError() {
        // Método responsável por tratar erros na gramática e lançar exceções

    }

    // Method that writes the code string to a java file
    public void writeCode() {
        // Open a file
        try {
            FileWriter fileWriter = new FileWriter("Output.java");

            // Always wrap FileWriter in BufferedWriter
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the code string to the file
            bufferedWriter.write(this.code);

            // Close the file
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + "Output.java" + "'");
        }

    }
}
