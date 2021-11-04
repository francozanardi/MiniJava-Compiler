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

    public abstract void addLocalVariable(LocalVariable localVariable) throws EntityAlreadyExistsException;
    protected abstract LocalVariable findLocalVariableInCurrentBlock(String localVariableName);

    protected LocalVariable findLocalVariableInAncestorBlocks(String localVariableName) {
        if(containerBlock == null){
            return null;
        }

        LocalVariable localVariableInParentBlock = containerBlock.findLocalVariableInCurrentBlock(localVariableName);
        if(localVariableInParentBlock != null){
            return localVariableInParentBlock;
        }

        return containerBlock.findLocalVariableInAncestorBlocks(localVariableName);
    }

    public LocalVariable getLocalVariable(String localVariableName){
        LocalVariable localVariableInCurrentBlock = findLocalVariableInCurrentBlock(localVariableName);
        if(localVariableInCurrentBlock != null){
            return localVariableInCurrentBlock;
        }

        return findLocalVariableInAncestorBlocks(localVariableName);
    }

    protected boolean hasLocalVariableInAncestorBlocks(String localVariableName){
        return findLocalVariableInAncestorBlocks(localVariableName) != null;
    }

    protected boolean hasLocalVariableInCurrentBlock(String localVariableName){
        return findLocalVariableInCurrentBlock(localVariableName) != null;
    }

    public boolean hasLocalVariable(String localVariableName){
        return  hasLocalVariableInCurrentBlock(localVariableName) ||
                hasLocalVariableInAncestorBlocks(localVariableName);
    }
}
