package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalErrorDescription;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

import java.util.regex.Pattern;

class AutomataString extends Automata {
    private static final AutomataString ourInstance = new AutomataString();

    static AutomataString getInstance() {
        return ourInstance;
    }

    private AutomataString() {

    }

    Token esInicioString() throws LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('"')){
            updateHandler();
            return AutomataStringBloque.getInstance().esStringVaciaOEnBloque();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('\\')){
            updateHandler();
            return esBackslashEnStringInLine();
        } else if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esStringInLine();
        } else {
            throw createLexicalException(LexicalErrorDescription.STRING_NEVER_CLOSED);
        }
    }


    Token esStringInLine() throws LexicalException {
        while(!isEndOfFile() && notIsStringLineBreak()){
            updateHandler();
        }

        if(!isEndOfFile() && handler.getCurrentChar().equals('\\')){
            updateHandler();
            return esBackslashEnStringInLine();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('"')){
            updateHandler();
            return esFinStringInLine();
        } else {
            throw createLexicalException(LexicalErrorDescription.STRING_NEVER_CLOSED);
        }
    }

    private boolean notIsStringLineBreak(){
        Pattern stringBreakerPattern = Pattern.compile("[^\\\\\"\\n]");

        return stringBreakerPattern.matcher(handler.getCurrentChar().toString()).matches();
    }

    Token esBackslashEnStringInLine() throws LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esStringInLine();
        } else {
            throw createLexicalException(LexicalErrorDescription.STRING_NEVER_CLOSED);
        }
    }

    Token esFinStringInLine() {
        return createToken(TokenName.STRING);
    }

}
