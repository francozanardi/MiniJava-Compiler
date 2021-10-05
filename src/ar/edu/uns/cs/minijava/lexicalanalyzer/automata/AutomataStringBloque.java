package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalErrorDescription;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

public class AutomataStringBloque extends Automata {
    private static final AutomataStringBloque ourInstance = new AutomataStringBloque();

    public static AutomataStringBloque getInstance() {
        return ourInstance;
    }

    private AutomataStringBloque() {
    }

    Token esStringVaciaOEnBloque() throws LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('"')){
            updateHandler();
            return esStringEnBloque();
        } else {
            return AutomataString.getInstance().esFinStringInLine();
        }
    }

    Token esStringEnBloque() throws LexicalException {
        while(!isEndOfFile() && notIsStringBlockBreak()){
            updateHandler();
        }

        if(!isEndOfFile() && handler.getCurrentChar().equals('\\')){
            updateHandler();
            return esBackslashEnStringEnBloque();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('"')) {
            updateHandler();
            return unaComillaEnStringEnBloque();
        } else {
            throw createLexicalException(LexicalErrorDescription.STRING_BLOCK_NEVER_CLOSED);
        }
    }

    private boolean notIsStringBlockBreak(){
        return !handler.getCurrentChar().equals('\\') && !handler.getCurrentChar().equals('"');
    }

    Token esBackslashEnStringEnBloque() throws LexicalException {
        if(!isEndOfFile()){
            updateHandler();
            return esStringEnBloque();
        } else {
            throw createLexicalException(LexicalErrorDescription.STRING_BLOCK_NEVER_CLOSED);
        }
    }

    Token unaComillaEnStringEnBloque() throws LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('"')){
            updateHandler();
            return dosComillasEnStringEnBloque();
        } else if(!isEndOfFile()) {
            updateHandler();
            return esStringEnBloque();
        } else {
            throw createLexicalException(LexicalErrorDescription.STRING_BLOCK_NEVER_CLOSED);
        }
    }

    Token dosComillasEnStringEnBloque() throws LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('"')){
            updateHandler();
            return esFinStringEnBloque();
        } else if(!isEndOfFile()) {
            updateHandler();
            return esStringEnBloque();
        } else {
            throw createLexicalException(LexicalErrorDescription.STRING_BLOCK_NEVER_CLOSED);
        }
    }

    Token esFinStringEnBloque() {
        return createToken(TokenName.STRING_BLOCK);
    }
}
