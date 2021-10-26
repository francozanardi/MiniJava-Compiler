package ar.edu.uns.cs.minijava.syntaxanalyzer.exception;

import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

import java.util.HashMap;
import java.util.Map;

public class TokenNameDisplayable {
    private final Map<TokenName, String> mapTokenName;
    private static final TokenNameDisplayable instance = new TokenNameDisplayable();

    private TokenNameDisplayable() {
        this.mapTokenName = new HashMap<>();
        fillMapTokenName();
    }

    private void fillMapTokenName(){
        mapTokenName.put(TokenName.STRING, "un string");
        mapTokenName.put(TokenName.ASIGNACION, "una asignación");
        mapTokenName.put(TokenName.INCREMENTOR, "++");
        mapTokenName.put(TokenName.DECREMENTOR, "--");
        mapTokenName.put(TokenName.CARACTER, "un carácter");
        mapTokenName.put(TokenName.ENTERO, "un entero");
        mapTokenName.put(TokenName.IDENTIFICADOR_DE_CLASE, "un identificador de clase");
        mapTokenName.put(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE, "un indentificador de método o variable");
        mapTokenName.put(TokenName.MAYOR, "el símbolo '>'");
        mapTokenName.put(TokenName.MAYOR_IGUAL, "el símbolo '>='");
        mapTokenName.put(TokenName.MENOR, "el símbolo '<'");
        mapTokenName.put(TokenName.MENOR_IGUAL, "el símbolo '<='");
        mapTokenName.put(TokenName.NEGACION, "el símbolo '!'");
        mapTokenName.put(TokenName.DISTINTO, "el símbolo '!='");
        mapTokenName.put(TokenName.COMPARACION, "el símbolo '=='");
        mapTokenName.put(TokenName.SUMA, "el símbolo '+'");
        mapTokenName.put(TokenName.RESTA, "el símbolo '-'");
        mapTokenName.put(TokenName.PRODUCTO, "el símbolo '*'");
        mapTokenName.put(TokenName.DIVISION, "el símbolo '/'");
        mapTokenName.put(TokenName.MODULO, "el símbolo '%'");
        mapTokenName.put(TokenName.AND, "el símbolo '&&'");
        mapTokenName.put(TokenName.OR, "el símbolo '||'");
        mapTokenName.put(TokenName.PARENTESIS_ABRE, "el símbolo '('");
        mapTokenName.put(TokenName.PARENTESIS_CIERRA, "el símbolo ')'");
        mapTokenName.put(TokenName.LLAVE_ABRE, "el símbolo '{'");
        mapTokenName.put(TokenName.LLAVE_CIERRA, "el símbolo '}'");
        mapTokenName.put(TokenName.PUNTO_Y_COMA, "el símbolo ';'");
        mapTokenName.put(TokenName.COMA, "el símbolo ','");
        mapTokenName.put(TokenName.PUNTO, "el símbolo '.'");
        mapTokenName.put(TokenName.EOF, "");
        mapTokenName.put(TokenName.CLASS_PR, "la palabra reservada 'class'");
        mapTokenName.put(TokenName.EXTENDS_PR, "la palabra reservada 'extends'");
        mapTokenName.put(TokenName.STATIC_PR, "la palabra reservada 'static'");
        mapTokenName.put(TokenName.DYNAMIC_PR, "la palabra reservada 'dynamic'");
        mapTokenName.put(TokenName.VOID_PR, "la palabra reservada 'void'");
        mapTokenName.put(TokenName.BOOLEAN_PR, "la palabra reservada 'boolean'");
        mapTokenName.put(TokenName.CHAR_PR, "la palabra reservada 'char'");
        mapTokenName.put(TokenName.INT_PR, "la palabra reservada 'int'");
        mapTokenName.put(TokenName.STRING_PR, "la palabra reservada 'String'");
        mapTokenName.put(TokenName.PUBLIC_PR, "la palabra reservada 'public'");
        mapTokenName.put(TokenName.PRIVATE_PR, "la palabra reservada 'private'");
        mapTokenName.put(TokenName.IF_PR, "la palabra reservada 'if'");
        mapTokenName.put(TokenName.ELSE_PR, "la palabra reservada 'else'");
        mapTokenName.put(TokenName.FOR_PR, "la palabra reservada 'for'");
        mapTokenName.put(TokenName.RETURN_PR, "la palabra reservada 'return'");
        mapTokenName.put(TokenName.THIS_PR, "la palabra reservada 'this'");
        mapTokenName.put(TokenName.NEW_PR, "la palabra reservada 'new'");
        mapTokenName.put(TokenName.NULL_PR, "la palabra reservada 'null'");
        mapTokenName.put(TokenName.TRUE_PR, "la palabra reservada 'true'");
        mapTokenName.put(TokenName.FALSE_PR, "la palabra reservada 'false'");
    }

    public static TokenNameDisplayable getInstance(){
        return instance;
    }

    public String getTokenNameDisplayable(TokenName tokenName){
        return mapTokenName.get(tokenName);
    }
}
