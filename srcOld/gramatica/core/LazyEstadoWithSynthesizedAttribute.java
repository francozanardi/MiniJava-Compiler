package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

@FunctionalInterface
public interface LazyEstadoWithSynthesizedAttribute<R> {
    Estado execute(R actionResult);
}
