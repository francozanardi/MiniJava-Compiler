package ar.edu.uns.cs.minijava.lexicalanalyzer;

public class LexicalException extends Exception {
    private String lexemaError;
    private int lineNumberError;
    private int columnNumberError;
    private String lineError;
    private String descriptionError;

    public LexicalException(String lexema, int lineNumber, int columnNumber){
        super(getTechnicalErrorMessage(lexema, lineNumber));

        this.lexemaError = lexema;
        this.lineNumberError = lineNumber;
        this.columnNumberError = columnNumber;
        this.lineError = "";
        this.descriptionError = "";
    }

    private static String getTechnicalErrorMessage(String lexema, int lineNumber) {
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

    public void setLineError(String lineError){
        this.lineError = lineError;
    }

    public String getLineError(){
        return lineError;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

}
