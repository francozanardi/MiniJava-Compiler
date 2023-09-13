package ar.edu.uns.cs.minijava.ast;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public abstract class Node {
    protected Token sentenceToken;

    public Node(Token sentenceToken) {
        this.sentenceToken = sentenceToken;
    }

    public Token getSentenceToken() {
        return sentenceToken;
    }

    public void setSentenceToken(Token sentenceToken) {
        this.sentenceToken = sentenceToken;
    }

    public abstract void generate() throws CodeGeneratorException;
}
