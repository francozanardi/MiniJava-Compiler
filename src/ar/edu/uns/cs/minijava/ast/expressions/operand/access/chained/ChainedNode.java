package ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained;

import ar.edu.uns.cs.minijava.ast.Node;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public abstract class ChainedNode extends Node {

    protected ChainedNode nextChained;
    protected boolean isWriteMode;
    protected Node prevChained;

    public ChainedNode(Token sentenceToken) {
        super(sentenceToken);
    }

    public ChainedNode getNextChained() {
        return nextChained;
    }

    public void setNextChained(ChainedNode nextChained) {
        this.nextChained = nextChained;
        if (this.nextChained != null) {
            this.nextChained.setPrevChained(this);
        }
    }

    public abstract Type check(Type previousType) throws SemanticException;
    public abstract boolean isCallable();
    public abstract boolean isAssignable();
    public abstract boolean isStatic(Class classContainer) throws SemanticException;

    public void setPrevChained(Node prevChained) {
        this.prevChained = prevChained;
    }

    public void enableWriteMode(){
        if(nextChained != null){
            nextChained.enableWriteMode();
        } else if(isAssignable()) {
            isWriteMode = true;
        }
    }

    public void disableWriteMode() {
        if(nextChained != null){
            nextChained.disableWriteMode();
        } else if(isAssignable()) {
            isWriteMode = false;
        }
    }

    protected Class getClassAssociatedToType(Type type) throws SemanticException {
        Class classAssociated = SymbolTable.getInstance().getClassById(type.getType());
        if (classAssociated != null) {
            return classAssociated;
        }
        throw new SemanticException(sentenceToken, "La clase " + type.getType() + " no existe o se trata de un tipo primitivo.");
    }
}
