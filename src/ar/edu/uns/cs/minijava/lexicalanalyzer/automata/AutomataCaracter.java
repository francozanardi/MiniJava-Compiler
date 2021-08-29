package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;
import java.util.regex.Pattern;

public class AutomataCaracter extends Automata {
    private static AutomataCaracter ourInstance = new AutomataCaracter();

    public static AutomataCaracter getInstance() {
        return ourInstance;
    }

    private AutomataCaracter() {
    }

    private Token estadoCharacter1() throws IOException, LexicalException {
        if(!isEndOfFile() && isCharacterBreak()){
            updateHandler();
            return estadoCharacter2();
        } else if(handler.getCurrentChar().equals('\\')){
            updateHandler();
            return estadoCharacter3();
        } else {
            throw createLexicalException();
        }
    }

    private boolean isCharacterBreak(){
        Pattern characterBreakerPattern = Pattern.compile("[^\\\\'\\n]");

        return characterBreakerPattern.matcher(handler.getCurrentChar().toString()).matches();
    }

    private Token estadoCharacter2() throws IOException, LexicalException {
        if(handler.getCurrentChar().equals('\'')){
            updateHandler();
            return estadoCharacter4();
        } else {
            throw createLexicalException();
        }
    }

    private Token estadoCharacter4() {
        return createToken("caracter");
    }

    private Token estadoCharacter3() throws IOException, LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return estadoCharacter2();
        } else {
            throw createLexicalException();
        }
    }

}
