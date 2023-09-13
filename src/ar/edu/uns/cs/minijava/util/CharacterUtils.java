package ar.edu.uns.cs.minijava.util;

public class CharacterUtils {
    public static boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }

    public static boolean isLowerCase(char c){
        return c >= 'a' && c <= 'z';
    }

    public static boolean isUpperCase(char c){
        return c >= 'A' && c <= 'Z';
    }

    public static boolean isLetter(char c){
        return isLowerCase(c) || isUpperCase(c);
    }

    //Character.isWhitespace reconoce más caracteres que los mencionados como espacios en blanco
    //en el documento sobre minijava. Sin embargo preferimos usar esto para evitar reconocer símbolos
    //como la "vertical tabulation" como un símbolo inválido.
    public static boolean isWhitespace(char c){
        return Character.isWhitespace(c);
    }

    private static Character[] getAllCharactersInIntervale(char min, char max){
        Character[] allCharacters = new Character[max-min+1];
        for(int i = 0; i < allCharacters.length; i++){
            allCharacters[i] = (char)(min + i);
        }

        return allCharacters;
    }

    public static Character[] getAllDigits(){
        return getAllCharactersInIntervale('0', '9');
    }

    public static Character[] getAllLowerCase(){
        return getAllCharactersInIntervale('a', 'z');
    }

    public static Character[] getAllUpperCase(){
        return getAllCharactersInIntervale('A', 'Z');
    }
}
