package ar.edu.uns.cs.minijava.lexicalanalyzer;

public interface LazyTokenEvaluation {
    Token eval() throws LexicalException;
}
