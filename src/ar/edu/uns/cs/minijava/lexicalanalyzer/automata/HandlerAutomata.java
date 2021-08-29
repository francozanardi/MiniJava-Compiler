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

    public void setGestorDeSource(GestorDeSource gestorDeSource){
        this.gestorDeSource = gestorDeSource;
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
        if(Character.isDigit(currentChar)){
            updateLexema();
            updateCurrentChar();
            return AutomataEntero.getInstance().estadoEntero1();
        } else if(Character.isLetter(currentChar)){

        }

        return null;
    }

}
