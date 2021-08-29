package ar.edu.uns.cs.minijava;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalAnalyzer;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.util.GestorDeSource;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        LexicalAnalyzer lexicalAnalyzer;

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
        }

        return lexicalAnalyzer;
    }

    private static void searchTokens(LexicalAnalyzer lexicalAnalyzer){
        boolean hayError = false;
        Token token;
        try {
            token = lexicalAnalyzer.nextToken();
            while(!token.getTokenName().equals("eof")){
                token = lexicalAnalyzer.nextToken();
                mostrarToken(token);
            }
        } catch (LexicalException lexicalException) {
            mostrarLexicalException(lexicalException);
            hayError = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(!hayError){
                mostrarSinErrores();
            }
        }
    }

    private static void mostrarToken(Token token){
        String tokenInfo = "(" +
                token.getTokenName() +
                "," +
                token.getLexema() +
                "," +
                token.getLineNumber() +
                ")";

        System.out.println(tokenInfo);
    }

    private static void mostrarLexicalException(LexicalException error) {
        String errorMessage = "Error léxico en línea " +
                error.getLineNumberError() +
                ": " +
                error.getLexemaError() +
                "no es un símbolo válido";

        System.out.println();
        System.out.println(errorMessage);
        System.out.println(error.getMessage());
    }


    private static void mostrarSinErrores() {
        System.out.println();
        System.out.println("[SinErrores]");
    }

}
