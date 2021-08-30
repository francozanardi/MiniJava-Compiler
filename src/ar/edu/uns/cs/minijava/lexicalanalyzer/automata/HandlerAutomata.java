package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.util.GestorDeSource;
import java.io.IOException;

public class HandlerAutomata {
    private StringBuilder lexema;
    private Character currentChar;
    private GestorDeSource gestorDeSource;
    private static HandlerAutomata ourInstance = new HandlerAutomata();

    private HandlerAutomata() {
    }

    public static HandlerAutomata getInstance() {
        return ourInstance;
    }

    public void setGestorDeSource(GestorDeSource gestorDeSource) throws IOException {
        this.gestorDeSource = gestorDeSource;
        updateCurrentChar();
    }

    GestorDeSource getGestorDeSource(){
        return gestorDeSource;
    }

    public Token nextToken() throws LexicalException, IOException {
        lexema = new StringBuilder();
        return estadoInicial();
    }

    void updateLexema(){
        lexema.append(currentChar);
    }

    void updateCurrentChar() throws IOException {
        currentChar = gestorDeSource.nextChar();
    }

    Character getCurrentChar(){
        return currentChar;
    }

    String getLexema(){
        return lexema.toString();
    }

    private Token estadoInicial() throws LexicalException, IOException {
        if(gestorDeSource.isEndOfFile()) {
            return estadoEOF();
        } else if(Character.isWhitespace(currentChar)) {
            updateCurrentChar();
            return estadoInicial();
        } else if(Character.isDigit(currentChar)){
            updateLexema();
            updateCurrentChar();
            return AutomataEntero.getInstance().esDigito();
        } else if(Character.isUpperCase(currentChar)){
            updateLexema();
            updateCurrentChar();
            return AutomataIdentificador.getInstance().esIdClase();
        } else if(Character.isLowerCase(currentChar)){
            updateLexema();
            updateCurrentChar();
            return AutomataIdentificador.getInstance().esIdMetVar();
        } else if(currentChar.equals('\'')){
            updateLexema();
            updateCurrentChar();
            return AutomataCaracter.getInstance().esInicioCaracter();
        } else if(currentChar.equals('"')){
            updateLexema();
            updateCurrentChar();
            return AutomataString.getInstance().esInicioString();
        } else if(currentChar.equals('(')){
            updateLexema();
            updateCurrentChar();
            return AutomataPuntuacion.getInstance().esParentesisAbre();
        } else if(currentChar.equals(')')){
            updateLexema();
            updateCurrentChar();
            return AutomataPuntuacion.getInstance().esParentesisCierra();
        } else if(currentChar.equals('{')){
            updateLexema();
            updateCurrentChar();
            return AutomataPuntuacion.getInstance().esLlaveAbre();
        } else if(currentChar.equals('}')){
            updateLexema();
            updateCurrentChar();
            return AutomataPuntuacion.getInstance().esLlaveCierra();
        } else if(currentChar.equals('.')){
            updateLexema();
            updateCurrentChar();
            return AutomataPuntuacion.getInstance().esPunto();
        } else if(currentChar.equals(';')){
            updateLexema();
            updateCurrentChar();
            return AutomataPuntuacion.getInstance().esPuntoComa();
        } else if(currentChar.equals(',')){
            updateLexema();
            updateCurrentChar();
            return AutomataPuntuacion.getInstance().esComa();
        } else if(currentChar.equals('<')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esMenor();
        } else if(currentChar.equals('>')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esMayor();
        } else if(currentChar.equals('!')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esNegacion();
        } else if(currentChar.equals('+')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esSuma();
        } else if(currentChar.equals('-')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esResta();
        } else if(currentChar.equals('/')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esDivision();
        } else if(currentChar.equals('*')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esProducto();
        } else if(currentChar.equals('%')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esModulo();
        } else if(currentChar.equals('=')){
            updateLexema();
            updateCurrentChar();
            return AutomataAsignacion.getInstance().esIgual();
        } else if(currentChar.equals('|')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esOr1();
        } else if(currentChar.equals('&')){
            updateLexema();
            updateCurrentChar();
            return AutomataOperador.getInstance().esAnd1();
        } else {
            LexicalException charWithLexicalException;
            updateLexema();
            charWithLexicalException = new LexicalException(
                    lexema.toString(),
                    gestorDeSource.getLineNumber(),
                    getGestorDeSource().getColumnNumber()
            );

            updateCurrentChar();

            throw charWithLexicalException;
        }
    }

    private Token estadoEOF() {
        return new Token("eof", "", gestorDeSource.getLineNumber());
    }

}
