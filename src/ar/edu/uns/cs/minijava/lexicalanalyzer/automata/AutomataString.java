package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;
import java.util.regex.Pattern;

class AutomataString extends Automata {
    private static AutomataString ourInstance = new AutomataString();

    static AutomataString getInstance() {
        return ourInstance;
    }

    private AutomataString() {

    }

    Token esInicioString() throws IOException, LexicalException {
        if(!isEndOfFile() && isStringBreak()){
            updateHandler();
            return esInicioString();
        } else if( handler.getCurrentChar().equals('\\')){
            updateHandler();
            return esBackslashEnString();
        } else if( handler.getCurrentChar().equals('"')){
            updateHandler();
            return esFinString();
        } else {
            throw createLexicalException();
        }
    }

    private boolean isStringBreak(){
        Pattern stringBreakerPattern = Pattern.compile("[^\\\\\"\\n]");

        return stringBreakerPattern.matcher(handler.getCurrentChar().toString()).matches();
    }

    Token esBackslashEnString() throws IOException, LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esInicioString();
        } else {
            throw createLexicalException();
        }
    }

    Token esFinString() {
        return createToken("string");
    }

}
