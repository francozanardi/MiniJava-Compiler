package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;

class AutomataPuntuacion extends Automata {
    private static AutomataPuntuacion ourInstance = new AutomataPuntuacion();

    static AutomataPuntuacion getInstance() {
        return ourInstance;
    }

    private AutomataPuntuacion() {
    }

    Token esParentesisAbre(){
        return createToken(TokenName.PARENTESIS_ABRE);
    }

    Token esParentesisCierra(){
        return createToken(TokenName.PARENTESIS_CIERRA);
    }

    Token esLlaveAbre(){
        return createToken(TokenName.LLAVE_ABRE);
    }

    Token esLlaveCierra(){
        return createToken(TokenName.LLAVE_CIERRA);
    }

    Token esPuntoComa(){
        return createToken(TokenName.PUNTO_Y_COMA);
    }

    Token esComa(){
        return createToken(TokenName.COMA);
    }

    Token esPunto(){
        return createToken(TokenName.PUNTO);
    }
}
