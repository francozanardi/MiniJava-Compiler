package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalErrorDescription;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.util.CharacterUtils;

import static ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalAnalyzerSettings.MAX_INTEGER_LENGTH;

class AutomataEntero extends Automata {
    private static final AutomataEntero ourInstance = new AutomataEntero();

    static AutomataEntero getInstance() {
        return ourInstance;
    }

    private AutomataEntero() {

    }

    Token esDigito() throws LexicalException {
        while(!isEndOfFile() && CharacterUtils.isDigit(handler.getCurrentChar())){
            updateHandler();
        }

        if(handler.getLexema().length() > MAX_INTEGER_LENGTH){
            throw createLexicalException(LexicalErrorDescription.INTEGER_LIMITED_EXCEEDED);
        }

        return createToken(TokenName.ENTERO);
    }
}
