package ar.edu.uns.cs.minijava.lexicalanalyzer;

public class Token {
    private final String tokenName;
    private final String lexema;
    private final int lineNumber;

    public Token(String tokenName, String lexema, int lineNumber){
        this.tokenName = tokenName;
        this.lexema = lexema;
        this.lineNumber = lineNumber;
    }

    public String getTokenName() {
        return tokenName;
    }

    public String getLexema() {
        return lexema;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}
