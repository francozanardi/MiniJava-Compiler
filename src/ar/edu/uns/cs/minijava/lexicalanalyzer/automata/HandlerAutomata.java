package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.*;
import ar.edu.uns.cs.minijava.util.CharacterUtils;
import ar.edu.uns.cs.minijava.util.GestorDeSource;

import java.util.HashMap;

public class HandlerAutomata {
    private StringBuilder lexema;
    private Character currentChar;
    private GestorDeSource gestorDeSource;
    private HashMap<Character, LazyTokenEvaluation> charToTokenMap;
    private static HandlerAutomata ourInstance = new HandlerAutomata();

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

        int firstLineNumberInError = gestorDeSource.getLineNumber()-cantDeLineasEnLexema+1;
        String firstLineInError = gestorDeSource.getLine(firstLineNumberInError);
        int firstColumnNumberInError = firstLineInError.length()-lexemaInLines[0].length()+1;

        LexicalException lexicalException = new LexicalException(
                lexema.toString(),
                firstLineNumberInError,
                firstColumnNumberInError
        );

        lexicalException.setErrorLine(firstLineInError);
        lexicalException.setDescriptionError(errorDescription);

        return lexicalException;
    }

    Character getCurrentChar(){
        return currentChar;
    }

    String getLexema(){
        return lexema.toString();
    }
}
