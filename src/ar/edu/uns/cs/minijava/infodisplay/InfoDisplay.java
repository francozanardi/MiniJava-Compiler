package ar.edu.uns.cs.minijava.infodisplay;

import ar.edu.uns.cs.minijava.CompilerException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.syntaxanalyzer.exception.SyntaxException;

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

    public void showSuccess() {
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

    public void showCompilerException(CompilerException compilerException) {
        System.out.println(compilerException.getMessage());
        System.out.println();
        System.out.println(compilerException.getErrorCodeMessage());
    }
}
