package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalErrorDescription;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

import java.util.regex.Pattern;

class AutomataCaracter extends Automata {
    private static AutomataCaracter ourInstance = new AutomataCaracter();

    static AutomataCaracter getInstance() {
        return ourInstance;
    }

    private AutomataCaracter() {
    }

    Token esInicioCaracter() throws LexicalException {
        if(!isEndOfFile() && notIsCharacterBreak()){
            updateHandler();
            return esContenidoCaracter();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('\\')){
            updateHandler();
            return esBackslashEnCaracter();
        } else {
            throw createLexicalException(LexicalErrorDescription.INVALID_SYMBOL);
        }
    }

    private boolean notIsCharacterBreak(){
        Pattern characterBreakerPattern = Pattern.compile("[^\\\\'\\n]");

        return characterBreakerPattern.matcher(handler.getCurrentChar().toString()).matches();
    }

    Token esContenidoCaracter() throws LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('\'')){
            updateHandler();
            return esFinCaracter();
        } else {
            throw createLexicalException(LexicalErrorDescription.INVALID_CHAR);
        }
    }

    Token esFinCaracter() {
        return createToken(TokenName.CARACTER);
    }

    Token esBackslashEnCaracter() throws LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esContenidoCaracter();
        } else {
            throw createLexicalException(LexicalErrorDescription.INVALID_CHAR);
        }
    }

}
