package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

class AutomataComentario extends Automata {
    private static AutomataComentario ourInstance = new AutomataComentario();

    static AutomataComentario getInstance() {
        return ourInstance;
    }

    private AutomataComentario() {
    }

    Token esInicioComentarioEnBloque() throws LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('*')){
            updateHandler();
            return esInicioComentarioEnBloque();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('*')){
            updateHandler();
            return esFinComentarioEnBloque1();
        } else {
            throw createLexicalException("debe finalizar con */ para cerrar el bloque de comentarios.");
        }
    }

    Token esFinComentarioEnBloque1() throws LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('*')){
            updateHandler();
            return esInicioComentarioEnBloque();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('*')) {
            updateHandler();
            return esFinComentarioEnBloque1();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('/')) {
            updateHandler();
            return esFinComentarioEnBloque2();
        } else {
            throw createLexicalException("debe finalizar con */ para cerrar el bloque de comentarios.");
        }
    }

    Token esFinComentarioEnBloque2() {
        return createToken("comentario_bloque");
    }

    Token esComentarioEnLinea() {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esComentarioEnLinea();
        } else {
            return createToken("comentario_linea");
        }
    }

}
