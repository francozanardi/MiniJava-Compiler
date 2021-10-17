package ar.edu.uns.cs.minijava.syntaxanalyzer.exception;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

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
                TokenNameDisplayable.getInstance().getTokenNameDisplayable(expectedToken) +
                " pero se encontró '" +
                tokenFound.getLexema() + "'");

        this.tokenFound = tokenFound;
    }

    public SyntaxException(Token tokenFound, List<String> expectedTokens){
        super(getLineNumberMessageError(tokenFound.getLineNumber()) +
                ": Se esperaba alguno de los siguientes tokens: " +
                arrayToString(expectedTokens) +
                "\nSin embargo, se encontró '"
                + tokenFound.getLexema() + "'");

        this.tokenFound = tokenFound;
    }

    private static String getLineNumberMessageError(int lineInError){
        return "Error sintáctico en linea " + lineInError;
    }

    private static String arrayToString(List<String> expectedTokens) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");

        for(String token: expectedTokens){
            stringBuilder
                    .append("(*) ")
                    .append(TokenNameDisplayable.getInstance().getTokenNameDisplayable(token))
                    .append("\n");
        }

        return stringBuilder.toString();
    }

    public String getErrorCodeMessage(){
        return "[Error:" + tokenFound.getLexema() + "|" + tokenFound.getLineNumber() + "]";
    }

}
