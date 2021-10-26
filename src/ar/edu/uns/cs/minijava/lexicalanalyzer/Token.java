package ar.edu.uns.cs.minijava.lexicalanalyzer;

public class Token {
    private final TokenName tokenName;
    private final String lexema;
    private final int lineNumber;

    public Token(TokenName tokenName, String lexema, int lineNumber){
        this.tokenName = tokenName;
        this.lexema = lexema;
        this.lineNumber = lineNumber;
    }

    public TokenName getTokenName() {
        return tokenName;
    }

    public String getLexema() {
        return lexema;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}
