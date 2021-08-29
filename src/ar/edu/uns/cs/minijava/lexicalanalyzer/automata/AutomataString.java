package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;
import java.util.regex.Pattern;

public class AutomataString extends Automata {
    private static AutomataString ourInstance = new AutomataString();

    public static AutomataString getInstance() {
        return ourInstance;
    }

    private AutomataString() {

    }

    private Token estadoString1() throws IOException, LexicalException {
        if(!isEndOfFile() && isStringBreak()){
            updateHandler();
            return estadoString1();
        } else if( handler.getCurrentChar().equals('\\')){
            updateHandler();
            return estadoString2();
        } else if( handler.getCurrentChar().equals('"')){
            updateHandler();
            return estadoString3();
        } else {
            throw createLexicalException();
        }
    }

    private boolean isStringBreak(){
        Pattern stringBreakerPattern = Pattern.compile("[^\\\\\"\\n]");

        return stringBreakerPattern.matcher(handler.getCurrentChar().toString()).matches();
    }

    private Token estadoString2() throws IOException, LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return estadoString1();
        } else {
            throw createLexicalException();
        }
    }

    private Token estadoString3() {
        return createToken("string");
    }

}
