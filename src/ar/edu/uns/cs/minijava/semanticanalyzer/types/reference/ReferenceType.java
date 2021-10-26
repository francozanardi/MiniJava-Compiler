package ar.edu.uns.cs.minijava.semanticanalyzer.types.reference;

import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class ReferenceType extends Type {
    public ReferenceType(String type) {
        super(type);
    }

    @Override
    public boolean requireCheckExistence() {
        return true;
    }
}
