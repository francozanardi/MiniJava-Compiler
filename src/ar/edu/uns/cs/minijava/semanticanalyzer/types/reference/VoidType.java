package ar.edu.uns.cs.minijava.semanticanalyzer.types.reference;

import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class VoidType extends ReferenceType {
    public VoidType() {
        super("void");
    }

    @Override
    public boolean requireCheckExistence() {
        return false;
    }

    @Override
    public boolean isSubtypeOf(Type supertype) throws SemanticException {
        return false;
    }
}
