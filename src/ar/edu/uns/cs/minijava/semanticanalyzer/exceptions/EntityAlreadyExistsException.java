package ar.edu.uns.cs.minijava.semanticanalyzer.exceptions;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public class EntityAlreadyExistsException extends SemanticException {
    public EntityAlreadyExistsException(Token tokenWithException, String description) {
        super(tokenWithException, description);
    }
}
