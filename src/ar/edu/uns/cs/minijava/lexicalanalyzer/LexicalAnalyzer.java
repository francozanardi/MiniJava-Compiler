package ar.edu.uns.cs.minijava.lexicalanalyzer;

import ar.edu.uns.cs.minijava.lexicalanalyzer.automata.HandlerAutomata;
import ar.edu.uns.cs.minijava.util.GestorDeSource;

import java.io.IOException;

public class LexicalAnalyzer {
    private GestorDeSource gestorDeSource;

    public LexicalAnalyzer(GestorDeSource gestorDeSource) throws IOException {
        this.gestorDeSource = gestorDeSource;
        HandlerAutomata.getInstance().setGestorDeSource(gestorDeSource);
    }

    public Token nextToken() throws LexicalException, IOException {
        return HandlerAutomata.getInstance().nextToken();
    }
}
