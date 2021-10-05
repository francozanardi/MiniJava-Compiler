package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

import java.util.HashMap;

class PalabrasClaveHandler {
    private static final PalabrasClaveHandler ourInstance = new PalabrasClaveHandler();
    private final HashMap<String, String> palabrasClaveTable; //tabla hash de lexema a token

    private PalabrasClaveHandler() {
        palabrasClaveTable = new HashMap<>();

        loadPalabrasClave();
    }

    private void loadPalabrasClave() {
        palabrasClaveTable.put("class", TokenName.CLASS_PR);
        palabrasClaveTable.put("extends", TokenName.EXTENDS_PR);
        palabrasClaveTable.put("static", TokenName.STATIC_PR);
        palabrasClaveTable.put("dynamic", TokenName.DYNAMIC_PR);
        palabrasClaveTable.put("void", TokenName.VOID_PR);
        palabrasClaveTable.put("boolean", TokenName.BOOLEAN_PR);
        palabrasClaveTable.put("char", TokenName.CHAR_PR);
        palabrasClaveTable.put("int", TokenName.INT_PR);
        palabrasClaveTable.put("String", TokenName.STRING_PR);
        palabrasClaveTable.put("public", TokenName.PUBLIC_PR);
        palabrasClaveTable.put("private", TokenName.PRIVATE_PR);
        palabrasClaveTable.put("if", TokenName.IF_PR);
        palabrasClaveTable.put("else", TokenName.ELSE_PR);
        palabrasClaveTable.put("for", TokenName.FOR_PR);
        palabrasClaveTable.put("return", TokenName.RETURN_PR);
        palabrasClaveTable.put("this", TokenName.THIS_PR);
        palabrasClaveTable.put("new", TokenName.NEW_PR);
        palabrasClaveTable.put("null", TokenName.NULL_PR);
        palabrasClaveTable.put("true", TokenName.TRUE_PR);
        palabrasClaveTable.put("false", TokenName.FALSE_PR);

    }

    static PalabrasClaveHandler getInstance() {
        return ourInstance;
    }

    boolean esUnaPalabraClave(String palabraClave){
        return palabrasClaveTable.containsKey(palabraClave);
    }

    String getPalabraClaveToken(String palabraClave){
        return palabrasClaveTable.get(palabraClave);
    }

}
