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
                error.getLexemaError() +
                " " +
                error.getDescriptionError();

        System.out.println();
        System.out.println(errorMessage);
        System.out.println(error.getMessage());
        mostrarInformacionDetallada(error);
        System.out.println();
    }

    private void mostrarInformacionDetallada(LexicalException error) {
        String errorTitleInErrorLine = "Detalle: ";


        StringBuilder errorIndication = new StringBuilder();
        for(int i = 0; i < error.getColumnNumberError()-1+errorTitleInErrorLine.length(); i++){
            errorIndication.append(" ");
        }

        errorIndication.append("^");

        System.out.println(errorTitleInErrorLine + error.getLineError());
        System.out.println(errorIndication.toString());
    }


    public void mostrarSinErrores() {
        System.out.println();
        System.out.println("[SinErrores]");
    }
}
