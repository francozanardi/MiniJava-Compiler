package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

class AutomataAsignacion extends Automata {
    private static AutomataAsignacion ourInstance = new AutomataAsignacion();

    static AutomataAsignacion getInstance() {
        return ourInstance;
    }

    private AutomataAsignacion() {
    }

    Token esIgual() {
        if(!isEndOfFile() && handler.getCurrentChar().equals('=')){
            updateHandler();
            return AutomataOperador.getInstance().esComparacion();
        } else {
            return createToken(TokenName.ASIGNACION);
        }
    }

    Token esIncrementor() {
        return createToken(TokenName.INCREMENTOR);
    }

    Token esDecrementor() {
        return createToken(TokenName.DECREMENTOR);
    }
}
