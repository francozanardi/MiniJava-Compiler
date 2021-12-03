package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.Node;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;

public abstract class SentenceNode extends Node {
    private int memoryReserved;
    public SentenceNode(Token sentenceToken) {
        super(sentenceToken);
        memoryReserved = 0;
    }

    public abstract void check() throws SemanticException;

    public int getAmountOfMemoryReserved() {
        return memoryReserved;
    }

    protected void modifyMemoryReservedWith(int memoryToAdd) {
        memoryReserved += memoryToAdd;
    }
}
