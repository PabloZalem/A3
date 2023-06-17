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
        //Esse método tem como objetivo avaliar se a entrada esta de acordo sintaticamente, caso sim, retorna true.
        public boolean parse(String input) {
        try {
            atomExpression(input);
            return input.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
        /*
         verifica se a expressão é um identificador, um tipo terminal ou uma expressão entre parênteses válidos. 
         Ele retornará sem exceções se a análise for bem-sucedida ou lança uma exceção se a expressão for inválida.
         */
        private void atomExpression(String input) {
        if (matchID(input)) {
            return;
        }
        
        if (matchTerminal("INT", input) || matchTerminal("DECIMAL", input) || matchTerminal("STRING", input) 
        || matchTerminal("BOOL", input)) 
        {
            return;
        }
        
        if (matchTerminal("(", input)) {
            atomExpression(input);
            if (matchTerminal(")", input)) {
                return;
            } else {
                throw new RuntimeException("Expected ')'");
            }
        }
        
        throw new RuntimeException("Invalid expression");
    }

    /*
    verifica se a sequência de caracteres corresponde a um identificador válido, 
    retornando um valor booleano indicando se a correspondência é bem-sucedida ou não. 
    */
    private boolean matchID(String input) {
        if (!input.isEmpty() && Character.isLetter(input.charAt(0))) {
            int currentPosition = 1;
            while (currentPosition < input.length() && (Character.isLetterOrDigit(input.charAt(currentPosition)) || input.charAt(currentPosition) == '_')) {
                currentPosition++;
            }
            return true;
        }
        return false;
    }

    /*Verifica se a sequência de caracteres input começa com um terminal específico terminal, 
    retornando true se houver correspondência e false caso contrário.*/
    private boolean matchTerminal(String terminal, String input) {
        if (input.startsWith(terminal)) {
            return true;
        }
        return false;
    }
}
