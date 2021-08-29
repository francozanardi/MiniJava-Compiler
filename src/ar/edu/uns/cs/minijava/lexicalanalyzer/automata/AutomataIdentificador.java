package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

public class AutomataIdentificador extends Automata {
    private static AutomataIdentificador ourInstance = new AutomataIdentificador();

    public static AutomataIdentificador getInstance() {
        return ourInstance;
    }

    private AutomataIdentificador() {
    }

    Token esIdClase() throws IOException {
        if(isValidId()){
            updateHandler();
            return esIdClase();
        } else {
            return createToken("idClase");
        }
    }

    private boolean isValidId(){
        return  Character.isLetter(handler.getCurrentChar()) ||
                Character.isDigit(handler.getCurrentChar()) ||
                handler.getCurrentChar().equals('_');
    }


    Token esIdMetVar() throws IOException {
        if(isValidId()){
            updateHandler();
            return esIdMetVar();
        } else {
            return createToken("idMetVar");
        }
    }
}
