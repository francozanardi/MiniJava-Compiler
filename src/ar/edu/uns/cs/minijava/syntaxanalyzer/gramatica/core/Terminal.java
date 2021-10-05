package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.syntaxanalyzer.CurrentTokenHandler;
import ar.edu.uns.cs.minijava.syntaxanalyzer.SyntaxException;

public class Terminal implements Estado {
    private final String tokenName;

    public Terminal(String tokenName){
        this.tokenName = tokenName;
    }

    public String getTokenName() {
        return tokenName;
    }

    @Override
    public void run(Estado parent, CurrentTokenHandler currentTokenHandler) throws SyntaxException, LexicalException {
        if(tokenName.equals(currentTokenHandler.getCurrentTokenName())){
            currentTokenHandler.nextToken();
        } else {
            throw new SyntaxException(currentTokenHandler.getCurrentToken(), tokenName);
        }
    }
}
