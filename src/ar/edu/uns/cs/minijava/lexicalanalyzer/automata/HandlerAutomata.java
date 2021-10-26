package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.*;
import ar.edu.uns.cs.minijava.util.CharacterUtils;
import ar.edu.uns.cs.minijava.util.GestorDeSource;

import java.util.HashMap;

public class HandlerAutomata {
    private StringBuilder lexema;
    private Character currentChar;
    private GestorDeSource gestorDeSource;
    private final HashMap<Character, LazyTokenEvaluation> charToTokenMap;
    private static final HandlerAutomata ourInstance = new HandlerAutomata();

    private HandlerAutomata() {
        charToTokenMap = new HashMap<>();

        loadCharToTokenMap();
    }

    private void loadCharToTokenMap() {
        charToTokenMap.put('\'', () -> AutomataCaracter.getInstance().esInicioCaracter());
        charToTokenMap.put('"', () -> AutomataString.getInstance().esInicioString());
        charToTokenMap.put('(', () -> AutomataPuntuacion.getInstance().esParentesisAbre());
        charToTokenMap.put(')', () -> AutomataPuntuacion.getInstance().esParentesisCierra());
        charToTokenMap.put('{', () -> AutomataPuntuacion.getInstance().esLlaveAbre());
        charToTokenMap.put('}', () -> AutomataPuntuacion.getInstance().esLlaveCierra());
        charToTokenMap.put('.', () -> AutomataPuntuacion.getInstance().esPunto());
        charToTokenMap.put(',', () -> AutomataPuntuacion.getInstance().esComa());
        charToTokenMap.put(';', () -> AutomataPuntuacion.getInstance().esPuntoComa());
        charToTokenMap.put('<', () -> AutomataOperador.getInstance().esMenor());
        charToTokenMap.put('>', () -> AutomataOperador.getInstance().esMayor());
        charToTokenMap.put('!', () -> AutomataOperador.getInstance().esNegacion());
        charToTokenMap.put('+', () -> AutomataOperador.getInstance().esSuma());
        charToTokenMap.put('-', () -> AutomataOperador.getInstance().esResta());
        charToTokenMap.put('*', () -> AutomataOperador.getInstance().esProducto());
        charToTokenMap.put('/', () -> AutomataOperador.getInstance().esDivision());
        charToTokenMap.put('%', () -> AutomataOperador.getInstance().esModulo());
        charToTokenMap.put('=', () -> AutomataAsignacion.getInstance().esIgual());
        charToTokenMap.put('&', () -> AutomataOperador.getInstance().esAnd1());
        charToTokenMap.put('|', () -> AutomataOperador.getInstance().esOr1());

        loadDigitos();
        loadMayusculas();
        loadMinusculas();
    }

    private void loadDigitos(){
        LazyTokenEvaluation lazyEvalDigitos = () -> AutomataEntero.getInstance().esDigito();

        for(Character c: CharacterUtils.getAllDigits()){
            charToTokenMap.put(c, lazyEvalDigitos);
        }
    }

    private void loadMinusculas(){
        LazyTokenEvaluation lazyEvalMinusculas = () -> AutomataIdentificador.getInstance().esIdMetVar();

        for(Character c: CharacterUtils.getAllLowerCase()){
            charToTokenMap.put(c, lazyEvalMinusculas);
        }
    }

    private void loadMayusculas(){
        LazyTokenEvaluation lazyEvalMayusculas = () -> AutomataIdentificador.getInstance().esIdClase();

        for(Character c: CharacterUtils.getAllUpperCase()){
            charToTokenMap.put(c, lazyEvalMayusculas);
        }
    }

    public static HandlerAutomata getInstance() {
        return ourInstance;
    }

    public void setGestorDeSource(GestorDeSource gestorDeSource){
        this.gestorDeSource = gestorDeSource;
        updateCurrentChar();
    }

    GestorDeSource getGestorDeSource(){
        return gestorDeSource;
    }

    public Token nextToken() throws LexicalException {
        lexema = new StringBuilder();
        return estadoInicial();
    }

    void updateLexema(){
        lexema.append(currentChar);
    }

    void updateCurrentChar() {
        currentChar = gestorDeSource.nextChar();
    }

    private void updateAll(){
        updateLexema();
        updateCurrentChar();
    }

    Token estadoInicial() throws LexicalException {
        LazyTokenEvaluation lazyTokenEvaluation;

        if(gestorDeSource.isEndOfFile()) {
            return estadoEOF();
        } else if(CharacterUtils.isWhitespace(currentChar)) {
            updateCurrentChar();
            return estadoInicial();
        } else if((lazyTokenEvaluation = charToTokenMap.get(currentChar)) != null){
            updateAll();
            return lazyTokenEvaluation.eval();
        } else {
            LexicalException charWithLexicalException;
            updateLexema();
            charWithLexicalException = createLexicalException(LexicalErrorDescription.INVALID_SYMBOL);
            updateCurrentChar();

            throw charWithLexicalException;
        }
    }

    private Token estadoEOF() {
        return new Token(TokenName.EOF, "", gestorDeSource.getLineNumber());
    }

    LexicalException createLexicalException(String errorDescription){
        String[] lexemaInLines = lexema.toString().split("\n", -1);
        int cantDeLineasEnLexema = lexemaInLines.length;
        int firstLineNumberInError = getFirstLineNumberInError(cantDeLineasEnLexema);
        String firstLineInError = getFirstLineInError(cantDeLineasEnLexema, firstLineNumberInError);
        int firstColumnNumberInError = getFirstColumnNumberInError(
                cantDeLineasEnLexema, firstLineInError, lexemaInLines[0].length());

        return new LexicalException(
                lexema.toString(),
                firstLineNumberInError,
                firstColumnNumberInError,
                firstLineInError,
                errorDescription);
    }

    private int getFirstLineNumberInError(int cantDeLineasEnLexema){
        if(cantDeLineasEnLexema > 1){
            return gestorDeSource.getLineNumber()-cantDeLineasEnLexema+1;
        }

        return gestorDeSource.getLineNumber();
    }

    private String getFirstLineInError(int cantDeLineasEnLexema, int firstLineNumberInError){
        if(cantDeLineasEnLexema > 1){
            return gestorDeSource.getLine(firstLineNumberInError);
        }

        return gestorDeSource.getCurrentLine();
    }

    private int getFirstColumnNumberInError(int cantDeLineasEnLexema, String firstLineInError, int firstLineInLexemaLength){
        if(cantDeLineasEnLexema > 1){
            return firstLineInError.length()-firstLineInLexemaLength+1;
        }

        return firstLineInError.indexOf(lexema.toString()) + 1;
    }

    Character getCurrentChar(){
        return currentChar;
    }

    String getLexema(){
        return lexema.toString();
    }
}
