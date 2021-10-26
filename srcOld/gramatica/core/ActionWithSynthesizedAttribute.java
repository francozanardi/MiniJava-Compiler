package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

@FunctionalInterface
interface ActionWithSynthesizedAttribute<E> {
    E execute();
}