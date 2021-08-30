package ar.edu.uns.cs.minijava.lexicalanalyzer;

import ar.edu.uns.cs.minijava.lexicalanalyzer.automata.HandlerAutomata;
import ar.edu.uns.cs.minijava.util.GestorDeSource;


public class LexicalAnalyzer {
    private GestorDeSource gestorDeSource;

    public LexicalAnalyzer(GestorDeSource gestorDeSource) {
        this.gestorDeSource = gestorDeSource;
        HandlerAutomata.getInstance().setGestorDeSource(gestorDeSource);
    }

    public Token nextToken() throws LexicalException {
        return HandlerAutomata.getInstance().nextToken();
    }
}
