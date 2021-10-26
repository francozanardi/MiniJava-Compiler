package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.syntaxanalyzer.CurrentTokenHandler;
import ar.edu.uns.cs.minijava.syntaxanalyzer.exception.SyntaxException;

public class Terminal extends Estado<Token, Void> {
    private final String tokenName;

    public Terminal(String tokenName){
        this.tokenName = tokenName;
    }

    public String getTokenName() {
        return tokenName;
    }

    @Override
    public void run(Estado<?, ?> parent, CurrentTokenHandler currentTokenHandler) throws SyntaxException, LexicalException {
        Token currentToken = currentTokenHandler.getCurrentToken();

        if(tokenName.equals(currentToken.getTokenName())){
            attributeInherited = currentToken;
            runAction();
            currentTokenHandler.nextToken();
        } else {
            throw new SyntaxException(currentTokenHandler.getCurrentToken(), tokenName);
        }
    }
}
