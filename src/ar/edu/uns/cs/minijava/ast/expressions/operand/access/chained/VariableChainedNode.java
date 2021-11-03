package ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained;

import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class VariableChainedNode extends ChainedNode {
    private BlockSentenceNode blockContainer;

    public VariableChainedNode(Token variableToken, BlockSentenceNode blockContainer) {
        super(variableToken);
        this.blockContainer = blockContainer;
    }

    @Override
    public Type check(Type previousType) {
        return null;
    }

    @Override
    public boolean isCallable() {
        return false;
    }

    @Override
    public boolean isAssignable() {
        return true;
    }
}
