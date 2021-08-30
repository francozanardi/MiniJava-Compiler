package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;
import java.util.regex.Pattern;

class AutomataCaracter extends Automata {
    private static AutomataCaracter ourInstance = new AutomataCaracter();

    static AutomataCaracter getInstance() {
        return ourInstance;
    }

    private AutomataCaracter() {
    }

    Token esInicioCaracter() throws IOException, LexicalException {
        if(!isEndOfFile() && isCharacterBreak()){
            updateHandler();
            return esContenidoCaracter();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('\\')){
            updateHandler();
            return esBackslashEnCaracter();
        } else {
            throw createLexicalException();
        }
    }

    private boolean isCharacterBreak(){
        Pattern characterBreakerPattern = Pattern.compile("[^\\\\'\\n]");

        return characterBreakerPattern.matcher(handler.getCurrentChar().toString()).matches();
    }

    Token esContenidoCaracter() throws IOException, LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('\'')){
            updateHandler();
            return esFinCaracter();
        } else {
            throw createLexicalException();
        }
    }

    Token esFinCaracter() {
        return createToken("caracter");
    }

    Token esBackslashEnCaracter() throws IOException, LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esContenidoCaracter();
        } else {
            throw createLexicalException();
        }
    }

}
