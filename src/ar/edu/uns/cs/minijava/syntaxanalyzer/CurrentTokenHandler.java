package ar.edu.uns.cs.minijava.syntaxanalyzer;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalAnalyzer;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

public class CurrentTokenHandler {
    private final static CurrentTokenHandler instance = new CurrentTokenHandler();
    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;

    private CurrentTokenHandler(){

    }

    public static CurrentTokenHandler getInstance(){
        return instance;
    }

    public LexicalAnalyzer getLexicalAnalyzer() {
        return lexicalAnalyzer;
    }

    public void setLexicalAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    public String getCurrentTokenName(){
        return currentToken.getTokenName();
    }

    public Token getCurrentToken(){
        return currentToken;
    }

    public void nextToken() throws LexicalException {
        currentToken = lexicalAnalyzer.nextToken();

        System.out.println("Se obtuvo el token " + currentToken.getTokenName());
    }
}
