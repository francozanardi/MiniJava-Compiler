package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;

public abstract class Entity {
    protected final Token identifierToken;

    public Entity(Token identifierToken){
        this.identifierToken = identifierToken;
    }

    public Token getIdentifierToken(){
        return this.identifierToken;
    }

    public abstract void checkDeclarations() throws SemanticException;
}
