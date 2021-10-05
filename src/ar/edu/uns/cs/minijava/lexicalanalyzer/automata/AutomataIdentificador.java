package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.util.CharacterUtils;

class AutomataIdentificador extends Automata {
    private static final AutomataIdentificador ourInstance = new AutomataIdentificador();

    static AutomataIdentificador getInstance() {
        return ourInstance;
    }

    private AutomataIdentificador() {
    }

    Token esIdClase(){
        while(!isEndOfFile() && isValidId()){
            updateHandler();
        }

        String palabraClave = searchPalabraClave();
        if(palabraClave != null){
            return createToken(palabraClave);
        }

        return createToken(TokenName.IDENTIFICADOR_DE_CLASE);
    }

    private boolean isValidId(){
        return  CharacterUtils.isLetter(handler.getCurrentChar()) ||
                CharacterUtils.isDigit(handler.getCurrentChar()) ||
                handler.getCurrentChar().equals('_');
    }

    private String searchPalabraClave(){
        return PalabrasClaveHandler.getInstance().getPalabraClaveToken(handler.getLexema());
    }


    Token esIdMetVar() {
        while(!isEndOfFile() && isValidId()){
            updateHandler();
        }

        String palabraClave = searchPalabraClave();
        if(palabraClave != null){
            return createToken(palabraClave);
        }
        return createToken(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE);
    }
}
