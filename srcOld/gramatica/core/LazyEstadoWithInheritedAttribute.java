package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

@FunctionalInterface
public interface LazyEstadoWithInheritedAttribute<R> {
    Estado execute();
}
