package ar.edu.uns.cs.minijava.infodisplay;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

public class InfoDisplay {

    public void mostrarErrorAlLeerArchivo(IOException error) {
        System.out.println("Se ha producido un error al intentar leer el archivo");
        System.out.println(error.getMessage());
    }

    public void mostrarToken(Token token){
        String tokenInfo = "(" +
                token.getTokenName() +
                "," +
                token.getLexema() +
                "," +
                token.getLineNumber() +
                ")";

        System.out.println(tokenInfo);
    }

    public void mostrarLexicalException(LexicalException error) {
        String errorMessage = "Error léxico en línea " +
                error.getLineNumberError() +
                ", columna " +
                error.getColumnNumberError() +
                ": " +
                getFirstLine(error.getLexemaError()) +
                " " +
                error.getDescriptionError();

        System.out.println();
        System.out.println(errorMessage);
        System.out.println(error.getMessage());
        mostrarInformacionDetallada(error);
        System.out.println();
    }

    private String getFirstLine(String text){
        String[] multiLine = text.split("\n");
        return multiLine.length > 1 ? multiLine[0] + "..." : multiLine[0];
    }

    private void mostrarInformacionDetallada(LexicalException error) {
        String errorTitleInErrorLine = "Detalle: ";
        StringBuilder errorIndication = new StringBuilder();

        addSpacesDueToTitle(errorIndication, errorTitleInErrorLine);
        addSpacesDueToLine(errorIndication, error);

        errorIndication.append("^");

        System.out.println(errorTitleInErrorLine + error.getLineError());
        System.out.println(errorIndication.toString());
    }

    private void addSpacesDueToTitle(StringBuilder errorIndication, String errorTitleInErrorLine) {
        for(int i = 0; i < errorTitleInErrorLine.length(); i++){
            errorIndication.append(" ");
        }
    }

    private void addSpacesDueToLine(StringBuilder errorIndication, LexicalException error) {
        String lineError = error.getLineError();
        for(int i = 0; i < error.getColumnNumberError()-1; i++){
            if(lineError.charAt(i) != '\t'){
                errorIndication.append(" ");
            } else {
                errorIndication.append("\t");
            }
        }
    }

    public void mostrarSinErrores() {
        System.out.println();
        System.out.println("[SinErrores]");
    }

    public void mostrarCantidadErrores(int cantidadErrores) {
        System.out.println();
        System.out.println("Se ha" +
                (cantidadErrores != 1 ? "n" : "") +
                " " +
                "producido " +
                cantidadErrores +
                " error" +
                (cantidadErrores != 1 ? "es" : "") +
                " en todo el archivo fuente.");
    }
}
