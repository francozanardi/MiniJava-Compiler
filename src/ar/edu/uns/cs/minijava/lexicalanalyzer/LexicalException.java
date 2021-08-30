package ar.edu.uns.cs.minijava.lexicalanalyzer;

public class LexicalException extends Exception {
    private String lexemaError;
    private int lineNumberError;
    private int columnNumberError;

    public LexicalException(String lexema, int lineNumber, int columnNumber){
        super(getErrorMessage(lexema, lineNumber));

        this.lexemaError = lexema;
        this.lineNumberError = lineNumber;
        this.columnNumberError = columnNumber;
    }

    private static String getErrorMessage(String lexema, int lineNumber) {
        return "[Error:" + lexema + "|" + lineNumber + "]";
    }

    public String getLexemaError() {
        return lexemaError;
    }

    public int getLineNumberError() {
        return lineNumberError;
    }

    public int getColumnNumberError(){
        return columnNumberError;
    }

}
