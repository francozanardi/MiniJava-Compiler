package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

class AutomataEntero extends Automata {
    private static AutomataEntero ourInstance = new AutomataEntero();

    static AutomataEntero getInstance() {
        return ourInstance;
    }

    private AutomataEntero() {

    }

    Token estadoEntero1() throws IOException {
        if(Character.isDigit(handler.getCurrentChar())){
            handler.updateLexema();
            handler.updateCurrentChar();
            return estadoEntero1();
        } else {
            return createToken("entero");
        }
    }
}
