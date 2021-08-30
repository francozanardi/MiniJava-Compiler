package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

class AutomataOperador extends Automata {
    private static AutomataOperador ourInstance = new AutomataOperador();

    static AutomataOperador getInstance() {
        return ourInstance;
    }

    private AutomataOperador() {
    }

    Token esMayor() throws IOException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('=')){
            updateHandler();
            return esMayorIgual();
        } else {
            return createToken("mayor");
        }
    }

    Token esMayorIgual() {
        return createToken("mayor_igual");
    }

    Token esMenor() throws IOException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('=')){
            updateHandler();
            return esMenorIgual();
        } else {
            return createToken("menor");
        }
    }

    Token esMenorIgual() {
        return createToken("menor_igual");
    }

    Token esNegacion() throws IOException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('=')){
            updateHandler();
            return esDistinto();
        } else {
            return createToken("negacion");
        }
    }

    Token esDistinto() {
        return createToken("distinto");
    }

    Token esComparacion() {
        return createToken("comparacion");
    }

    Token esSuma() throws IOException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('+')){
            updateHandler();
            return AutomataAsignacion.getInstance().esIncrementor();
        } else {
            return createToken("suma");
        }
    }

    Token esResta() throws IOException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('-')){
            updateHandler();
            return AutomataAsignacion.getInstance().esDecrementor();
        } else {
            return createToken("resta");
        }
    }

    Token esProducto(){
        return createToken("producto");
    }

    Token esDivision() throws IOException, LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('*')) {
            updateHandler();
            return AutomataComentario.getInstance().esInicioComentarioEnBloque();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('/')){
            return AutomataComentario.getInstance().esComentarioEnLinea();
        } else {
            return createToken("division");
        }
    }

    Token esModulo(){
        return createToken("modulo");
    }

    Token esAnd1() throws IOException, LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('&')){
            updateHandler();
            return esAnd2();
        } else {
            throw createLexicalException();
        }
    }

    private Token esAnd2() {
        return createToken("and");
    }

    Token esOr1() throws IOException, LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('|')){
            updateHandler();
            return esOr2();
        } else {
            throw createLexicalException();
        }
    }

    private Token esOr2() {
        return createToken("or");
    }

}
