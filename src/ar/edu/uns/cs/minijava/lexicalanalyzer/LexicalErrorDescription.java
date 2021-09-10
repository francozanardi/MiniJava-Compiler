package ar.edu.uns.cs.minijava.lexicalanalyzer;

public class LexicalErrorDescription {
    public static final String INVALID_SYMBOL = "no es un símbolo válido.";
    public static final String INVALID_CHAR = "no es un caracter válido.";
    public static final String BLOCK_COMMENT_NEVER_CLOSED = "debe finalizar con */ para cerrar el bloque de comentarios.";
    public static final String STRING_NEVER_CLOSED = "debe finalizar con comillas dobles para ser un string válido.";
    public static final String STRING_BLOCK_NEVER_CLOSED = "debe finalizar con tres comillas dobles seguidas (\"\"\") para ser un string en bloque válido.";
    public static final String INTEGER_LIMITED_EXCEEDED = "es un entero que supera el límite establecido de " + LexicalAnalyzerSettings.MAX_INTEGER_LENGTH + " dígitos.";
}
