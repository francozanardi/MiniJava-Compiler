package ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive;

import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public abstract class PrimitiveType extends Type {
    public PrimitiveType(String type) {
        super(type);
    }

    @Override
    public boolean requireCheckExistence() {
        return false;
    }
}
