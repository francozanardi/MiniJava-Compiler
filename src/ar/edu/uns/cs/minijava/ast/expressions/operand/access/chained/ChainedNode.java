package ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained;

import ar.edu.uns.cs.minijava.ast.Node;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public abstract class ChainedNode extends Node {
    private ChainedNode nextChained;

    public ChainedNode(Token sentenceToken) {
        super(sentenceToken);
    }

    public ChainedNode getNextChained() {
        return nextChained;
    }

    public void setNextChained(ChainedNode nextChained) {
        this.nextChained = nextChained;
    }

    public abstract Type check(Type previousType);
    public abstract boolean isCallable();
    public abstract boolean isAssignable();
}
