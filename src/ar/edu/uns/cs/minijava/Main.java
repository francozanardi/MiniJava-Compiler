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
    private static String outputPath;
    public final static boolean UNTIL_STAGE_2 = false;
    public final static boolean UNTIL_STAGE_3 = false;
    public final static boolean UNTIL_STAGE_4 = false;

    public static void main(String[] args){
        infoDisplay = new InfoDisplay();

        if (Main.UNTIL_STAGE_2 || Main.UNTIL_STAGE_3 || Main.UNTIL_STAGE_4) {
            Main.startOldCompiler(args);
            return;
        }

        if (args.length == 2) {
            outputPath = args[1];
            createLexicalAnalyzer(args[0])
                    .map(SyntaxAnalyzer::new)
                    .ifPresent(Main::startSyntaxAndSemanticAnalyzer);
        } else {
            infoDisplay.showFileNotSpecified();
        }
    }

    private static void startOldCompiler(String[] args) {
        if (args.length != 1) {
            System.out.println("Debe especificar un archivo fuente");
            return;
        }
        Optional<SyntaxAnalyzer> syntaxAnalyzer = createLexicalAnalyzer(args[0]).map(SyntaxAnalyzer::new);
        if (Main.UNTIL_STAGE_2) {
            syntaxAnalyzer.ifPresent(Main::startUntilStage2);
        } else if (Main.UNTIL_STAGE_3) {
            syntaxAnalyzer.ifPresent(Main::startUntilStage3);
        } else if (Main.UNTIL_STAGE_4) {
            syntaxAnalyzer.ifPresent(Main::startUntilStage4);
        }
    }

    private static Optional<LexicalAnalyzer> createLexicalAnalyzer(String pathname){
        GestorDeSource gestorDeSource;
        LexicalAnalyzer lexicalAnalyzer = null;

        try {
            gestorDeSource = new GestorDeSource(pathname);
            lexicalAnalyzer = new LexicalAnalyzer(gestorDeSource);
        } catch (FileNotFoundException e) {
            infoDisplay.showFileNotFoundError();
        } catch (IOException e) {
            infoDisplay.showErrorReadingFile(e);
        }

        return Optional.ofNullable(lexicalAnalyzer);
    }

    private static void startSyntaxAndSemanticAnalyzer(SyntaxAnalyzer syntaxAnalyzer) {
        try {
            SymbolTable symbolTable = SymbolTable.getInstance();
            symbolTable.reloadSymbolTable(outputPath);
            syntaxAnalyzer.initGrammar();
            symbolTable.checkDeclarations();
            symbolTable.checkSentences();
            symbolTable.generate();
            infoDisplay.showSuccess();
        } catch (CompilerException compilerException) {
            infoDisplay.showCompilerException(compilerException);
        } catch (IOException ioException) {
            infoDisplay.showErrorWritingFile(ioException);
        }
    }

    private static void startUntilStage2(SyntaxAnalyzer syntaxAnalyzer) {
        try {
            syntaxAnalyzer.initGrammar();
            infoDisplay.showSuccess();
        } catch (CompilerException compilerException) {
            infoDisplay.showCompilerException(compilerException);
        }
    }

    private static void startUntilStage3(SyntaxAnalyzer syntaxAnalyzer) {
        try {
            SymbolTable symbolTable = SymbolTable.getInstance();
            symbolTable.reloadSymbolTable(null);
            syntaxAnalyzer.initGrammar();
            symbolTable.checkDeclarations();
            infoDisplay.showSuccess();
        } catch (CompilerException compilerException) {
            infoDisplay.showCompilerException(compilerException);
        } catch (IOException ioException) {
            infoDisplay.showErrorWritingFile(ioException);
        }
    }

    private static void startUntilStage4(SyntaxAnalyzer syntaxAnalyzer) {
        try {
            SymbolTable symbolTable = SymbolTable.getInstance();
            symbolTable.reloadSymbolTable(null);
            syntaxAnalyzer.initGrammar();
            symbolTable.checkDeclarations();
            symbolTable.checkSentences();
            infoDisplay.showSuccess();
        } catch (CompilerException compilerException) {
            infoDisplay.showCompilerException(compilerException);
        } catch (IOException ioException) {
            infoDisplay.showErrorWritingFile(ioException);
        }
    }

    @Deprecated
    private static void searchTokens(LexicalAnalyzer lexicalAnalyzer){
        int cantidadErrores = 0;
        Token token = new Token(null, "", 0);
        do {
            try {
                token = lexicalAnalyzer.nextToken();
                infoDisplay.showToken(token);
            } catch (LexicalException lexicalException) {
                infoDisplay.showCompilerException(lexicalException);
                cantidadErrores++;
            }

        } while (!token.getTokenName().equals(TokenName.EOF));

        if(cantidadErrores > 0)
            infoDisplay.showNumberOfErrors(cantidadErrores);
        else
            infoDisplay.showSuccess();
    }
}
