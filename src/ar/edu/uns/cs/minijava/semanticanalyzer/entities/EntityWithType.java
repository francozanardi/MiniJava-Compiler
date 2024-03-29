package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public abstract class EntityWithType extends Entity {
    protected final Type type;
    protected Integer offset;

    public EntityWithType(Token identifierToken, Type type) {
        super(identifierToken);
        this.type = type;
        this.offset = null;
    }

    public Type getType(){
        return type;
    }

    @Override
    public void checkDeclarations() throws SemanticException {
        if(type.requireCheckExistence()){
            Class typeClass = SymbolTable.getInstance().getClassById(type.getType());
            if(typeClass == null){
                throw new SemanticException(identifierToken, "La clase " + type.getType() + " no está definida.");
            }
        }
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public boolean isOffsetAssigned(){
        return offset != null;
    }
}
