package ar.edu.uns.cs.minijava.semanticanalyzer;

import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNode;
import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNodeImpl;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;

public class Context {
    private Class currentClass;
    private Method currentMethod;
    private BlockSentenceNode currentBlock;

    public Class getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(Class currentClass) {
        this.currentClass = currentClass;
        this.currentMethod = null;
        this.currentBlock = null;
    }

    public Method getCurrentMethod() {
        return currentMethod;
    }

    public void setCurrentMethod(Method currentMethod) {
        this.currentMethod = currentMethod;
        this.currentBlock = null;
    }

    public BlockSentenceNode getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(BlockSentenceNode currentBlock) {
        this.currentBlock = currentBlock;
    }
}
