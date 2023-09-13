package ar.edu.uns.cs.minijava.infodisplay;

import ar.edu.uns.cs.minijava.CompilerException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

public class InfoDisplay {

    public void showErrorReadingFile(IOException error) {
        System.out.println("Se ha producido un error al intentar leer el archivo.");
        System.out.println(error.getMessage());
    }

    public void showErrorWritingFile(IOException error) {
        System.out.println("Se ha producido un error al intentar escribir en el archivo de salida.");
        System.out.println(error.getMessage());
    }

    public void showFileNotFoundError(){
        System.out.println("No se ha encontrado el archivo fuente especificado");
    }

    public void showFileNotSpecified(){
        System.out.println("Debe especificar un archivo fuente y el nombre del archivo a crear.");
    }

    public void showToken(Token token){
        String tokenInfo = "(" +
                token.getTokenName() +
                "," +
                token.getLexema() +
                "," +
                token.getLineNumber() +
                ")";

        System.out.println(tokenInfo);
    }

    public void showSuccess() {
        System.out.println();
        System.out.println("[SinErrores]");
    }

    public void showNumberOfErrors(int cantidadErrores) {
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

    public void showCompilerException(CompilerException compilerException) {
        System.out.println(compilerException.getMessage());
        System.out.println();
        System.out.println(compilerException.getErrorCodeMessage());
    }
}
