package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.operand.OperandNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained.ChainedNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.ReferenceType;

public abstract class AccessNode extends OperandNode {
    protected ReferenceType castingType;
    protected ChainedNode chained;
    protected boolean isWriteMode;

    public AccessNode(Token sentenceToken) {
        super(sentenceToken);
        this.isWriteMode = false;
    }

    public ReferenceType getCastingType() {
        return castingType;
    }

    public void setCastingType(ReferenceType castingType) {
        this.castingType = castingType;
    }

    public ChainedNode getChained() {
        return chained;
    }

    public void setChained(ChainedNode chained) {
        this.chained = chained;
        if (this.chained != null) {
            this.chained.setPrevChained(this);
        }
    }

    public boolean isLastElementChainedCallable() {
        if(chained != null){
            return getLastElementChained().isCallable();
        }

        return isCallable();
    }

    public ChainedNode getLastElementChained(){
        if(chained != null){
            ChainedNode chainedPointer = chained;
            while(chainedPointer.getNextChained() != null){
                chainedPointer = chainedPointer.getNextChained();
            }

            return chainedPointer;
        }

        return null;
    }

    public boolean isLastElementChainedAssignable() {
        if(chained != null){
            return getLastElementChained().isAssignable();
        }

        return isAssignable();
    }

    protected Type checkCasting(Type finalType) throws SemanticException {
        if(castingType != null){
            if(!castingType.isSubtypeOf(finalType)){
                throw new SemanticException(sentenceToken, "Se produjo un error al realizar el casting. " +
                        castingType.getType() + " no es un subtipo de " +
                        finalType.getType());
            }

            return castingType;
        }

        return finalType;
    }

    public abstract boolean isAssignable();
    public abstract boolean isCallable();

    public void enableWriteMode(){
        if(chained != null){
            getLastElementChained().enableWriteMode();
        } else if(isAssignable()) {
            this.isWriteMode = true;
        }
    }

    public void disableWriteMode(){
        if(chained != null){
            getLastElementChained().disableWriteMode();
        } else if(isAssignable()) {
            this.isWriteMode = false;
        }
    }
}
