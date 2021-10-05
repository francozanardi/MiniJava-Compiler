package ar.edu.uns.cs.minijava.syntaxanalyzer;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SyntaxException extends Exception {
    private final Token tokenFound;

    public SyntaxException(Token tokenFound, String expectedToken){
        super(getLineNumberMessageError(tokenFound.getLineNumber()) +
                ": Se esperaba " +
                expectedToken +
                " pero se encontró " +
                tokenFound.getLexema());

        this.tokenFound = tokenFound;
    }

    public SyntaxException(Token tokenFound, List<String> expectedTokens){
        super(getLineNumberMessageError(tokenFound.getLineNumber()) +
                ": Se esperaba alguno de los siguientes tokens " +
                arrayToString(expectedTokens) +
                " pero se encontró "
                + tokenFound.getLexema());

        this.tokenFound = tokenFound;
    }

    private static String getLineNumberMessageError(int lineInError){
        return "Error sintáctico en linea " + lineInError;
    }

    private static String arrayToString(List<String> expectedTokens) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        int listIndex = 0;
        for(String token: expectedTokens){
            if(listIndex < expectedTokens.size()-1){
                stringBuilder.append(token).append(", ");
            } else {
                stringBuilder.append(token).append("}");
            }

            listIndex++;
        }

        return stringBuilder.toString();
    }

    public String getErrorCodeMessage(){
        return "[Error:" + tokenFound.getLexema() + "|" + tokenFound.getLineNumber() + "]";
    }

}
