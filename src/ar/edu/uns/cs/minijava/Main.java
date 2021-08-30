package ar.edu.uns.cs.minijava;

import ar.edu.uns.cs.minijava.infodisplay.InfoDisplay;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalAnalyzer;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.util.GestorDeSource;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    private static InfoDisplay infoDisplay;
    public static void main(String[] args){
        LexicalAnalyzer lexicalAnalyzer;
        infoDisplay = new InfoDisplay();

        if(args.length == 1){
            lexicalAnalyzer = crearLexicalAnalyzer(args[0]);
            if(lexicalAnalyzer != null)
                searchTokens(lexicalAnalyzer);
        } else {
            System.out.println("Debe especificar un archivo fuente");
        }
    }


    private static LexicalAnalyzer crearLexicalAnalyzer(String pathname){
        GestorDeSource gestorDeSource;
        LexicalAnalyzer lexicalAnalyzer = null;

        try {
            gestorDeSource = new GestorDeSource(pathname);
            lexicalAnalyzer = new LexicalAnalyzer(gestorDeSource);
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo fuente especificado");
        } catch (IOException e) {
            infoDisplay.mostrarErrorAlLeerArchivo(e);
        }

        return lexicalAnalyzer;
    }


    private static void searchTokens(LexicalAnalyzer lexicalAnalyzer){
        boolean hayError = false;
        Token token = null;
        do {
            try {
                token = lexicalAnalyzer.nextToken();
                infoDisplay.mostrarToken(token);
            } catch (LexicalException lexicalException) {
                infoDisplay.mostrarLexicalException(lexicalException);
                hayError = true;
            } catch (IOException e) {
                infoDisplay.mostrarErrorAlLeerArchivo(e);
            }
        } while (token != null && !token.getTokenName().equals("eof"));

        if(!hayError)
            infoDisplay.mostrarSinErrores();
    }

}
