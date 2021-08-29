package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import java.util.HashMap;
import java.util.Hashtable;

class PalabrasClaveHandler {
    private static PalabrasClaveHandler ourInstance = new PalabrasClaveHandler();
    private HashMap<String, String> palabrasClaveTable; //tabla hash de lexema a token

    private PalabrasClaveHandler() {
        palabrasClaveTable = new HashMap<>();

        loadPalabrasClave();
    }

    private void loadPalabrasClave() {
        palabrasClaveTable.put("class", "pr_class");
        palabrasClaveTable.put("extends", "pr_extends");
        palabrasClaveTable.put("static", "pr_static");
        palabrasClaveTable.put("dynamic", "pr_dynamic");
        palabrasClaveTable.put("void", "pr_void");
        palabrasClaveTable.put("boolean", "pr_boolean");
        palabrasClaveTable.put("char", "pr_char");
        palabrasClaveTable.put("int", "pr_int");
        palabrasClaveTable.put("String", "pr_String");
        palabrasClaveTable.put("public", "pr_public");
        palabrasClaveTable.put("private", "pr_private");
        palabrasClaveTable.put("if", "pr_if");
        palabrasClaveTable.put("else", "pr_else");
        palabrasClaveTable.put("for", "pr_for");
        palabrasClaveTable.put("return", "pr_return");
        palabrasClaveTable.put("this", "pr_this");
        palabrasClaveTable.put("new", "pr_new");
        palabrasClaveTable.put("null", "pr_null");
        palabrasClaveTable.put("true", "pr_true");
        palabrasClaveTable.put("false", "pr_false");

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
