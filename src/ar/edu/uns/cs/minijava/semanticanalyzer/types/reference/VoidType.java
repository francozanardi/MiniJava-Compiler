package ar.edu.uns.cs.minijava.semanticanalyzer.types.reference;

public class VoidType extends ReferenceType {
    public VoidType() {
        super("void");
    }

    @Override
    public boolean requireCheckExistence() {
        return false;
    }
}
