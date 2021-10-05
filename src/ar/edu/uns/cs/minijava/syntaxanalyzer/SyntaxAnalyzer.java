package ar.edu.uns.cs.minijava.syntaxanalyzer;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalAnalyzer;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.NoTerminales;

public class SyntaxAnalyzer {

    public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        CurrentTokenHandler.getInstance().setLexicalAnalyzer(lexicalAnalyzer);
    }

    public void initGrammar() throws LexicalException, SyntaxException {
        CurrentTokenHandler currentTokenHandler = CurrentTokenHandler.getInstance();
        currentTokenHandler.nextToken();
        System.out.println("Iniciando gramática con token actual: " + currentTokenHandler.getCurrentTokenName());

        NoTerminales.getInstance().inicial().run(null, currentTokenHandler);
    }


}
