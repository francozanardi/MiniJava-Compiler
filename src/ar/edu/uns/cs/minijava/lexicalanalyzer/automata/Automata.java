package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

abstract class Automata {
    protected HandlerAutomata handler;

    protected Automata(){
        handler = HandlerAutomata.getInstance();
    }

    protected Token createToken(String tokenName){
        return new Token(tokenName, handler.getLexema(), handler.getGestorDeSource().getLineNumber());
    }

    protected void updateHandler() {
        handler.updateLexema();
        handler.updateCurrentChar();
    }

    protected boolean isEndOfFile(){
        return handler.getGestorDeSource().isEndOfFile();
    }

    protected LexicalException createLexicalException(String errorDescription) {
        LexicalException lexicalException = new LexicalException(
                handler.getLexema(),
                handler.getGestorDeSource().getLineNumber(),
                handler.getGestorDeSource().getColumnNumber()
        );

        lexicalException.setLineError(handler.getGestorDeSource().getCurrentLine());
        lexicalException.setDescriptionError(errorDescription);

        return lexicalException;
    }
}
