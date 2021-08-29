package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

class AutomataPuntuacion extends Automata {
    private static AutomataPuntuacion ourInstance = new AutomataPuntuacion();

    static AutomataPuntuacion getInstance() {
        return ourInstance;
    }

    private AutomataPuntuacion() {
    }

    Token esParentesisAbre(){
        return createToken("parentesis_abre");
    }

    Token esParentesisCierra(){
        return createToken("parentesis_cierra");
    }

    Token esLlaveAbre(){
        return createToken("llave_abre");
    }

    Token esLlaveCierra(){
        return createToken("llave_cierra");
    }

    Token esPuntoComa(){
        return createToken("punto_y_coma");
    }

    Token esComa(){
        return createToken("coma");
    }

    Token esPunto(){
        return createToken("punto");
    }
}
