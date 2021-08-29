package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

class AutomataComentario extends Automata {
    private static AutomataComentario ourInstance = new AutomataComentario();

    static AutomataComentario getInstance() {
        return ourInstance;
    }

    private AutomataComentario() {
    }

    Token esInicioComentarioEnBloque() throws IOException, LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('*')){
            updateHandler();
            return esInicioComentarioEnBloque();
        } else if(handler.getCurrentChar().equals('*')){
            return esFinComentarioEnBloque1();
        } else {
            throw createLexicalException();
        }
    }

    Token esFinComentarioEnBloque1() throws IOException, LexicalException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('*')){
            updateHandler();
            return esInicioComentarioEnBloque();
        } else if(handler.getCurrentChar().equals('*')) {
            return esFinComentarioEnBloque1();
        } else if(handler.getCurrentChar().equals('/')) {
            return esFinComentarioEnBloque2();
        } else {
            throw createLexicalException();
        }
    }

    Token esFinComentarioEnBloque2() {
        return createToken("comentario_bloque");
    }

    Token esComentarioEnLinea() throws IOException {
        if(!isEndOfFile() && !handler.getCurrentChar().equals('\n')){
            updateHandler();
            return esComentarioEnLinea();
        } else {
            return createToken("comentario_linea");
        }
    }

}
