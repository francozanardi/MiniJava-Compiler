package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalErrorDescription;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

import java.util.regex.Pattern;

class AutomataString extends Automata {
    private static AutomataString ourInstance = new AutomataString();

    static AutomataString getInstance() {
        return ourInstance;
    }

    private AutomataString() {

    }

    Token esInicioString() throws LexicalException {
        while(!isEndOfFile() && notIsStringBreak()){
            updateHandler();
        }

        if(!isEndOfFile() && handler.getCurrentChar().equals('\\')){
            updateHandler();
            return esBackslashEnString();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('"')){
            updateHandler();
            return esFinString();
        } else {
            throw createLexicalException(LexicalErrorDescription.STRING_NEVER_CLOSED);
        }
    }

    private boolean notIsStringBreak(){
        Pattern stringBreakerPattern = Pattern.compile("[^\\\\\"\\n]");

        return stringBreakerPattern.matcher(handler.getCurrentChar().toString()).matches();
    }

    Token esBackslashEnString() throws LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esInicioString();
        } else {
            throw createLexicalException(LexicalErrorDescription.STRING_NEVER_CLOSED);
        }
    }

    Token esFinString() {
        return createToken(TokenName.STRING);
    }

}
