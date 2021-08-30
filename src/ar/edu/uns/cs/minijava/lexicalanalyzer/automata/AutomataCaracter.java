package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

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
            throw createLexicalException("no es un caracter válido");
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
            throw createLexicalException("no es un caracter válido");
        }
    }

    Token esFinCaracter() {
        return createToken("caracter");
    }

    Token esBackslashEnCaracter() throws LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esContenidoCaracter();
        } else {
            throw createLexicalException("no es un caracter válido");
        }
    }

}
