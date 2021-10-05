package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalErrorDescription;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

class AutomataComentario extends Automata {
    private static final AutomataComentario ourInstance = new AutomataComentario();

    static AutomataComentario getInstance() {
        return ourInstance;
    }

    private AutomataComentario() {
    }

    Token esInicioComentarioEnBloque() throws LexicalException {
        while(!isEndOfFile() && !handler.getCurrentChar().equals('*')){
            updateHandler();
        }

        if(!isEndOfFile()){
            updateHandler();
            return esFinComentarioEnBloque1();
        } else {
            throw createLexicalException(LexicalErrorDescription.BLOCK_COMMENT_NEVER_CLOSED);
        }
    }

    Token esFinComentarioEnBloque1() throws LexicalException {
         while(!isEndOfFile() && handler.getCurrentChar().equals('*')) {
            updateHandler();
         }

        if(!isEndOfFile() && !handler.getCurrentChar().equals('/')){
            updateHandler();
            return esInicioComentarioEnBloque();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('/')) {
            updateHandler();
            return esFinComentarioEnBloque2();
        } else {
            throw createLexicalException(LexicalErrorDescription.BLOCK_COMMENT_NEVER_CLOSED);
        }
    }

    Token esFinComentarioEnBloque2() throws LexicalException {
        return handler.nextToken();
    }

    Token esComentarioEnLinea() throws LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esComentarioEnLinea();
        } else {
            return handler.nextToken();
        }
    }

}
