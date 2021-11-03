package ar.edu.uns.cs.minijava.semanticanalyzer.types.reference;

import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class NullType extends ReferenceType {
    public NullType() {
        super("null");
    }

    @Override
    public boolean requireCheckExistence() {
        return false;
    }

    @Override
    public boolean isSubtypeOf(Type supertype) throws SemanticException {
        Class supertypeClass = SymbolTable.getInstance().getClassById(supertype.getType());

        return supertypeClass != null;
    }
}
