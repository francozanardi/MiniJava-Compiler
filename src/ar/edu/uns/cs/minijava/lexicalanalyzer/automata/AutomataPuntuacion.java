package ar.edu.uns.cs.minijava.lexicalanalyzer.automata;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public class AutomataPuntuacion extends Automata {
    private static AutomataPuntuacion ourInstance = new AutomataPuntuacion();

    public static AutomataPuntuacion getInstance() {
        return ourInstance;
    }

    private AutomataPuntuacion() {
    }

    private Token estadoEsParentesisAbre(){
        return createToken("parentesis_abre");
    }

    private Token estadoEsParentesisCierra(){
        return createToken("parentesis_cierra");
    }

    private Token estadoEsLlaveAbre(){
        return createToken("llave_abre");
    }

    private Token estadoEsLlaveCierra(){
        return createToken("llave_cierra");
    }

    private Token estadoEsPuntoComa(){
        return createToken("punto_y_coma");
    }

    private Token estadoEsComa(){
        return createToken("coma");
    }

    private Token estadoEsPunto(){
        return createToken("punto");
    }
}
