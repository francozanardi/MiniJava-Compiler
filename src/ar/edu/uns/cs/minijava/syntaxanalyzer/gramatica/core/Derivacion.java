package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Derivacion {
    private final List<Supplier<Estado>> lazyEstados;
    private final List<String> primeros;

    private Derivacion(List<String> primeros) {
        this.primeros = primeros;
        this.lazyEstados = new ArrayList<>();
    }

    public static Derivacion create(String ...primeros){
        return new Derivacion(Arrays.asList(primeros));
    }

    List<Supplier<Estado>> getLazyEstados() {
        return lazyEstados;
    }

    List<String> getPrimeros() {
        return primeros;
    }

    public Derivacion appendEstado(Supplier<Estado> lazyEstado){
        lazyEstados.add(lazyEstado);

        return this;
    }

    public Derivacion appendEstadoRecursivo(){
        lazyEstados.add(EstadoRecursivo::new);
        return this;
    }
}
