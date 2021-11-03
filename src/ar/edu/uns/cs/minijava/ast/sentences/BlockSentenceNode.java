package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.LocalVariable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;

public abstract class BlockSentenceNode extends SentenceNode {
    protected final Method containerMethod;
    protected final BlockSentenceNode containerBlock;

    public BlockSentenceNode(Token sentenceToken, Method containerMethod, BlockSentenceNode containerBlock) {
        super(sentenceToken);
        this.containerMethod = containerMethod;
        this.containerBlock = containerBlock;
    }

    public Method getContainerMethod() {
        return containerMethod;
    }

    public BlockSentenceNode getContainerBlock() {
        return containerBlock;
    }

    public boolean hasLocalVariable(String localVariableName){
        return  hasLocalVariableInCurrentBlock(localVariableName) ||
                hasLocalVariableInAncestorBlocks(localVariableName);
    }

    protected abstract boolean hasLocalVariableInCurrentBlock(String localVariableName);
    protected abstract boolean hasLocalVariableInAncestorBlocks(String localVariableName);
    public abstract void addLocalVariable(LocalVariable localVariable) throws EntityAlreadyExistsException;

}
