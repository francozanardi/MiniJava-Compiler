package ar.edu.uns.cs.minijava.lexicalanalyzer;

public class LexicalException extends Exception {
    private String lexemaError;
    private int lineNumberInError;
    private int columnNumberInError;
    private String errorLine;
    private String descriptionError;

    public LexicalException(String lexema, int lineNumber, int columnNumber){
        super(getErrorCodeMessage(lexema, lineNumber));

        this.lexemaError = lexema;
        this.lineNumberInError = lineNumber;
        this.columnNumberInError = columnNumber;
        this.errorLine = "";
        this.descriptionError = "";
    }

    private static String getErrorCodeMessage(String lexema, int lineNumber) {
        return "[Error:" + getFirstLine(lexema) + "|" + lineNumber + "]";
    }

    private static String getFirstLine(String text){
        int indexSaltoDeLinea = text.indexOf('\n');
        boolean haySaltoDeLinea = indexSaltoDeLinea != -1;

        indexSaltoDeLinea = haySaltoDeLinea ? indexSaltoDeLinea : text.length();
        String firstLine = text.substring(0, indexSaltoDeLinea);

        return haySaltoDeLinea ? firstLine + "..." : firstLine;
    }


    public String getLexemaError() {
        return lexemaError;
    }

    public int getLineNumberInError() {
        return lineNumberInError;
    }

    public int getColumnNumberError(){
        return columnNumberInError;
    }

    public void setErrorLine(String errorLine){
        this.errorLine = errorLine;
    }

    public String getErrorLine(){
        return errorLine;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

}
