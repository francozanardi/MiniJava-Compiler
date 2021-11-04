package ar.edu.uns.cs.minijava.semanticanalyzer.types.reference;

import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class ReferenceType extends Type {
    public ReferenceType(String type) {
        super(type);
    }

    @Override
    public boolean requireCheckExistence() {
        return true;
    }

    @Override
    public boolean isSubtypeOf(Type supertype) throws SemanticException {
        if(this.equals(supertype)){
            return true;
        }

        Class subtypeClass = SymbolTable.getInstance().getClassById(type);
        Class supertypeClass = SymbolTable.getInstance().getClassById(supertype.getType());

        return subtypeClass != null && supertypeClass != null && subtypeClass.hasThisAncestor(supertypeClass);
    }
}
