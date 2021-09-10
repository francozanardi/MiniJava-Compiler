package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

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
        return handler.createLexicalException(errorDescription);
    }
}
