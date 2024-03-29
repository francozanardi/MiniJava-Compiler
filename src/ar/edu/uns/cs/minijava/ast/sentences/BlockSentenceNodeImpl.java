package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.LocalVariable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Parameter;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.EntityTable;

import java.util.ArrayList;
import java.util.List;

public class BlockSentenceNodeImpl extends BlockSentenceNode {
    protected List<SentenceNode> sentences;
    protected EntityTable<String, LocalVariable> localVariables;

    public BlockSentenceNodeImpl(Token sentenceToken, Method containerMethod, BlockSentenceNode containerBlock) {
        super(sentenceToken, containerMethod, containerBlock);
        this.sentences = new ArrayList<>();
        this.localVariables = new EntityTable<>();
    }

    @Override
    public boolean containsReturnSentence() {
        return this.sentences.stream().anyMatch(SentenceNode::containsReturnSentence);
    }

    public void appendSentence(SentenceNode sentence){
        sentences.add(sentence);
    }

    public void setSentences(List<SentenceNode> sentences){
        this.sentences = sentences;
    }

    @Override
    protected LocalVariable findLocalVariableInCurrentBlock(String localVariableName) {
        return localVariables.get(localVariableName);
    }

    @Override
    public void addLocalVariable(LocalVariable localVariable) throws EntityAlreadyExistsException {
        String name = localVariable.getIdentifierToken().getLexema();
        Parameter parameterFound = containerMethod.getParameterById(name);

        if(parameterFound != null){
            throw new EntityAlreadyExistsException(localVariable.getIdentifierToken(),
                    "Ya existe un parámetro definido en el método con el nombre de " + name);
        }

        if(hasLocalVariable(name)){
            throw new EntityAlreadyExistsException(localVariable.getIdentifierToken(),
                    "Ya existe una variable local definida en el método con el nombre de " + name);
        }

        localVariable.setOffset((-1)*getLocalVariablesNumberInMethod());
        localVariables.putAndCheck(name, localVariable);
    }

    @Override
    public void check() throws SemanticException {
        for(SentenceNode sentence : sentences){
            sentence.check();
        }
    }

    @Override
    public int getLocalVariablesNumberInMethod() {
        if(containerBlock != null){
            return containerBlock.getLocalVariablesNumberInMethod() + localVariables.size();
        }

        return localVariables.size();
    }

    @Override
    public void generate() throws CodeGeneratorException {
        for (SentenceNode sentence : sentences) {
            sentence.generate();
            modifyMemoryReservedWith(sentence.getAmountOfMemoryReserved());
        }

        if(localVariables.size() > 0){
            SymbolTable.getInstance().appendInstruction(
                    new Instruction(OneArgumentInstruction.FMEM, localVariables.size()));
            modifyMemoryReservedWith(-localVariables.size());
        }
    }
}
