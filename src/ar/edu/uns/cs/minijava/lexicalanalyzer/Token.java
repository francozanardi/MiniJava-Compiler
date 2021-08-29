package ar.edu.uns.cs.minijava.lexicalanalyzer;

public class Token {
    private String tokenName;
    private String lexema;
    private int lineNumber;

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