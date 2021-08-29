package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.io.IOException;

class AutomataIdentificador extends Automata {
    private static AutomataIdentificador ourInstance = new AutomataIdentificador();

    static AutomataIdentificador getInstance() {
        return ourInstance;
    }

    private AutomataIdentificador() {
    }

    Token esIdClase() throws IOException {
        if(isValidId()){
            updateHandler();
            return esIdClase();
        } else {
            String palabraClave = searchPalabraClave();
            if(palabraClave != null){
                return createToken(palabraClave);
            }
            return createToken("idClase");
        }
    }

    private boolean isValidId(){
        return  Character.isLetter(handler.getCurrentChar()) ||
                Character.isDigit(handler.getCurrentChar()) ||
                handler.getCurrentChar().equals('_');
    }

    private String searchPalabraClave(){
        return PalabrasClaveHandler.getInstance().getPalabraClaveToken(handler.getLexema());
    }


    Token esIdMetVar() throws IOException {
        if(isValidId()){
            updateHandler();
            return esIdMetVar();
        } else {
            String palabraClave = searchPalabraClave();
            if(palabraClave != null){
                return createToken(palabraClave);
            }
            return createToken("idMetVar");
        }
    }
}
