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