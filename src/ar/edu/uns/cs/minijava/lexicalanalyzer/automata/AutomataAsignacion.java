package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

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
            return createToken("asignacion");
        }
    }

    Token esIncrementor() {
        return createToken("incrementor");
    }

    Token esDecrementor() {
        return createToken("decrementor");
    }
}
