package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.operand.OperandNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained.ChainedNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.ReferenceType;

public abstract class AccessNode extends OperandNode {
    protected ReferenceType castingType;
    protected ChainedNode chained;
//    protected ChainedNode lastElementChained;
    // TODO: crear un puntero al último elemento del encadenado, para evitar iterar por todo el encadenado.
    // Aunque son pocos elementos. Quizás no vale la pena.

    public AccessNode(Token sentenceToken) {
        super(sentenceToken);
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

    public abstract boolean isCallable();
    public abstract boolean isAssignable();
}
