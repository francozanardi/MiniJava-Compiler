package ar.edu.uns.cs.minijava.semanticanalyzer.exceptions;

import ar.edu.uns.cs.minijava.CompilerException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public class SemanticException extends CompilerException {
    private final Token tokenWithError;

    public SemanticException(Token tokenWithError, String description){
        super("Error en línea " +
                tokenWithError.getLineNumber() +
                ": " + description);

        this.tokenWithError = tokenWithError;
    }

    @Override
    public String getErrorCodeMessage() {
        return "[Error:" + tokenWithError.getLexema() + "|" + tokenWithError.getLineNumber() + "]";
    }
}
