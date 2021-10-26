package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

@FunctionalInterface
public interface Action<I, S> {
    S execute(I attributeInherited);
}
