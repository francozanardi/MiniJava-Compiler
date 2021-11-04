package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
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

        localVariables.putAndCheck(name, localVariable);
    }

    @Override
    public void check() throws SemanticException {
        for(SentenceNode sentence : sentences){
            sentence.check();
        }
    }
}
