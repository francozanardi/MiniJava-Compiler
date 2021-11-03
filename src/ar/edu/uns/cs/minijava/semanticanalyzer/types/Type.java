package ar.edu.uns.cs.minijava.semanticanalyzer.types;

import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;

import java.util.Objects;

public abstract class Type {
    protected final String type;

    protected Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type anotherType = (Type) o;
        return Objects.equals(this.type, anotherType.type);
    }

    public abstract boolean requireCheckExistence();
    public abstract boolean isSubtypeOf(Type superType) throws SemanticException;

}
