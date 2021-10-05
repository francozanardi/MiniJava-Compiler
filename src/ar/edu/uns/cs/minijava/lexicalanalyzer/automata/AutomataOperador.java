package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalErrorDescription;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;


class AutomataOperador extends Automata {
    private static final AutomataOperador ourInstance = new AutomataOperador();

    static AutomataOperador getInstance() {
        return ourInstance;
    }

    private AutomataOperador() {
    }

    Token esMayor() {
        if(!isEndOfFile() && handler.getCurrentChar().equals('=')){
            updateHandler();
            return esMayorIgual();
        } else {
            return createToken(TokenName.MAYOR);
        }
    }

    Token esMayorIgual() {
        return createToken(TokenName.MAYOR_IGUAL);
    }

    Token esMenor() {
        if(!isEndOfFile() && handler.getCurrentChar().equals('=')){
            updateHandler();
            return esMenorIgual();
        } else {
            return createToken(TokenName.MENOR);
        }
    }

    Token esMenorIgual() {
        return createToken(TokenName.MENOR_IGUAL);
    }

    Token esNegacion() {
        if(!isEndOfFile() && handler.getCurrentChar().equals('=')){
            updateHandler();
            return esDistinto();
        } else {
            return createToken(TokenName.NEGACION);
        }
    }

    Token esDistinto() {
        return createToken(TokenName.DISTINTO);
    }

    Token esComparacion() {
        return createToken(TokenName.COMPARACION);
    }

    Token esSuma() {
        if(!isEndOfFile() && handler.getCurrentChar().equals('+')){
            updateHandler();
            return AutomataAsignacion.getInstance().esIncrementor();
        } else {
            return createToken(TokenName.SUMA);
        }
    }

    Token esResta(){
        if(!isEndOfFile() && handler.getCurrentChar().equals('-')){
            updateHandler();
            return AutomataAsignacion.getInstance().esDecrementor();
        } else {
            return createToken(TokenName.RESTA);
        }
    }

    Token esProducto(){
        return createToken(TokenName.PRODUCTO);
    }

    Token esDivision() throws LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('*')) {
            updateHandler();
            return AutomataComentario.getInstance().esInicioComentarioEnBloque();
        } else if(!isEndOfFile() && handler.getCurrentChar().equals('/')){
            updateHandler();
            return AutomataComentario.getInstance().esComentarioEnLinea();
        } else {
            return createToken(TokenName.DIVISION);
        }
    }

    Token esModulo(){
        return createToken(TokenName.MODULO);
    }

    Token esAnd1() throws LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('&')){
            updateHandler();
            return esAnd2();
        } else {
            throw createLexicalException(LexicalErrorDescription.INVALID_SYMBOL);
        }
    }

    private Token esAnd2() {
        return createToken(TokenName.AND);
    }

    Token esOr1() throws LexicalException {
        if(!isEndOfFile() && handler.getCurrentChar().equals('|')){
            updateHandler();
            return esOr2();
        } else {
            throw createLexicalException(LexicalErrorDescription.INVALID_SYMBOL);
        }
    }

    private Token esOr2() {
        return createToken(TokenName.OR);
    }

}
