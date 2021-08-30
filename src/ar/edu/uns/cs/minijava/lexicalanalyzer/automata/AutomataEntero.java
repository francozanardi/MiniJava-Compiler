package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

class AutomataEntero extends Automata {
    private static AutomataEntero ourInstance = new AutomataEntero();
    private final static int MAX_INTEGER_LENGTH = 9;

    static AutomataEntero getInstance() {
        return ourInstance;
    }

    private AutomataEntero() {

    }

    Token esDigito() throws IOException, LexicalException {
        if(!isEndOfFile() && Character.isDigit(handler.getCurrentChar())){
            handler.updateLexema();
            handler.updateCurrentChar();
            return esDigito();
        } else {
            if(handler.getLexema().length() > MAX_INTEGER_LENGTH){
                throw createLexicalException();
            }
            return createToken("entero");
        }
    }
}
