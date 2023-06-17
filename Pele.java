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
}
