package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

class AutomataAsignacion extends Automata {
    private static AutomataAsignacion ourInstance = new AutomataAsignacion();

    static AutomataAsignacion getInstance() {
        return ourInstance;
    }

    private AutomataAsignacion() {
    }

    Token esIgual() throws IOException {
        if(handler.getCurrentChar().equals('=')){
            updateHandler();
            return AutomataOperador.getInstance().esComparacion();
        } else {
            return createToken("asignacion");
        }
    }

    Token esIncrementor() throws IOException {
        return createToken("incrementor");
    }

    Token esDecrementor() throws IOException {
        return createToken("decrementor");
    }
}
