package ar.edu.uns.cs.minijava;

import ar.edu.uns.cs.minijava.infodisplay.InfoDisplay;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalAnalyzer;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.syntaxanalyzer.SyntaxAnalyzer;
import ar.edu.uns.cs.minijava.util.GestorDeSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class Main {
    private static InfoDisplay infoDisplay;
    private static final Boolean SYNTAX_ANALYZER_ENABLED = true;

    public static void main(String[] args){
        infoDisplay = new InfoDisplay();

        if(args.length == 1){
            if(SYNTAX_ANALYZER_ENABLED){
                createLexicalAnalyzer(args[0])
                        .map(SyntaxAnalyzer::new)
                        .ifPresent(Main::startSyntaxAndSemanticAnalyzer);
            } else {
                createLexicalAnalyzer(args[0]).ifPresent(Main::searchTokens);
            }

        } else {
            System.out.println("Debe especificar un archivo fuente");
        }
    }


    private static Optional<LexicalAnalyzer> createLexicalAnalyzer(String pathname){
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

        return Optional.ofNullable(lexicalAnalyzer);
    }


    private static void startSyntaxAndSemanticAnalyzer(SyntaxAnalyzer syntaxAnalyzer){
        try {
            syntaxAnalyzer.initGrammar();
            SymbolTable.getInstance().checkDeclarations();
            SymbolTable.getInstance().checkSentences();
            infoDisplay.showSuccess();
        } catch (CompilerException compilerException) {
            infoDisplay.showCompilerException(compilerException);
        }

        SymbolTable.getInstance().emptySymbolTable();
    }


    private static void searchTokens(LexicalAnalyzer lexicalAnalyzer){
        int cantidadErrores = 0;
        Token token = new Token(null, "", 0);
        do {
            try {
                token = lexicalAnalyzer.nextToken();
                infoDisplay.mostrarToken(token);
            } catch (LexicalException lexicalException) {
                infoDisplay.showCompilerException(lexicalException);
                cantidadErrores++;
            }

        } while (!token.getTokenName().equals(TokenName.EOF));

        if(cantidadErrores > 0)
            infoDisplay.mostrarCantidadErrores(cantidadErrores);
        else
            infoDisplay.showSuccess();
    }

}
