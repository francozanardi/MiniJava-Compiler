package ar.edu.uns.cs.minijava.lexicalanalyzer;

import ar.edu.uns.cs.minijava.CompilerException;

public class LexicalException extends CompilerException {
    private final String lexemaError;
    private final int lineNumberInError;
    private final int columnNumberInError;
    private final String errorLine;
    private final String descriptionError;

    public LexicalException(String lexema, int lineNumber, int columnNumber, String errorLine, String descriptionError){
        super("Error léxico en línea " +
                lineNumber +
                ", columna " +
                columnNumber +
                ": " +
                lexema +
                " " +
                descriptionError +
                "\n" +
                getDetailedInformation(errorLine, columnNumber));

        this.lexemaError = lexema;
        this.lineNumberInError = lineNumber;
        this.columnNumberInError = columnNumber;
        this.errorLine = errorLine;
        this.descriptionError = descriptionError;
    }

    private static String getDetailedInformation(String errorLine, int columnNumber) {
        String errorTitleInErrorLine = "Detalle: ";
        StringBuilder errorIndication = new StringBuilder();

        addSpacesDueToTitle(errorIndication, errorTitleInErrorLine);
        addSpacesDueToLineInError(errorIndication, errorLine, columnNumber);

        errorIndication.append("^");

        return errorTitleInErrorLine + errorLine + "\n" + errorIndication;
    }

    private static void addSpacesDueToTitle(StringBuilder errorIndication, String errorTitleInErrorLine) {
        for(int i = 0; i < errorTitleInErrorLine.length(); i++){
            errorIndication.append(" ");
        }
    }

    private static void addSpacesDueToLineInError(StringBuilder errorIndication, String errorLine, int columnNumber) {
        for(int i = 0; i < columnNumber-1; i++){
            if(errorLine.charAt(i) != '\t'){
                errorIndication.append(" ");
            } else {
                errorIndication.append("\t");
            }
        }
    }

    @Override
    public String getErrorCodeMessage() {
        return "[Error:" + getFirstLine(lexemaError) + "|" + lineNumberInError + "]";
    }

    private String getFirstLine(String text){
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

    public String getErrorLine(){
        return errorLine;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

}
