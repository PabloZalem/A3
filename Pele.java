import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Pele {
    // Empty constructor

    String code = "";

    public Pele() {
        this.code = "";
    }

    // Method to return the greeting
    public String getGreeting() {
        return "Hello World!";
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
    }
    
    private boolean matchTerminal(String terminal) {
        // Implementação do matchTerminal de acordo com a sua definição
    }
}
