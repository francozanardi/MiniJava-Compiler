package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.Node;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;

public abstract class SentenceNode extends Node {
    public SentenceNode(Token sentenceToken) {
        super(sentenceToken);
    }

    public abstract void check() throws SemanticException;
}
