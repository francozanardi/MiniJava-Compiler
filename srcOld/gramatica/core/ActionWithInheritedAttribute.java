package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

@FunctionalInterface
public interface ActionWithInheritedAttribute<E> {
    void execute(E attributeInherited);
}
