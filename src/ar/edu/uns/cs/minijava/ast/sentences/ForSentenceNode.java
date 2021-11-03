package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.sentences.assignments.AssignmentNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.LocalVariable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Parameter;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;

public class ForSentenceNode extends BlockSentenceNode {
    private LocalVariable variable;
    private ExpressionNode condition;
    private AssignmentNode assignment;
    private SentenceNode body;

    public ForSentenceNode(Token sentenceToken, Method containerMethod, BlockSentenceNode containerBlock) {
        super(sentenceToken, containerMethod, containerBlock);
    }


    @Override
    protected boolean hasLocalVariableInCurrentBlock(String localVariableName) {
        return variable.getIdentifierToken().getLexema().equals(localVariableName);
    }

    @Override
    protected boolean hasLocalVariableInAncestorBlocks(String localVariableName) {
        if(containerBlock == null){
            return false;
        }

        return  containerBlock.hasLocalVariableInCurrentBlock(localVariableName) ||
                containerBlock.hasLocalVariableInAncestorBlocks(localVariableName);
    }

    public LocalVariable getVariable() {
        return variable;
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

        this.variable = localVariable;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public AssignmentNode getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentNode assignment) {
        this.assignment = assignment;
    }

    public SentenceNode getBody() {
        return body;
    }

    public void setBody(SentenceNode body) {
        this.body = body;
    }

    @Override
    public void check() throws SemanticException {
        variable.getSentence().check();
        checkCondition();
        assignment.check();
        body.check();
    }

    private void checkCondition() throws SemanticException {
        Type conditionType = condition.check();
        if(!conditionType.equals(new BooleanType())){
            throw new SemanticException(sentenceToken, "La expresión de la condición del for debe ser de tipo booleano, " +
                            "sin embargo se encontró una expresión de tipo '" +
                            conditionType.getType() +
                            "'");
        }
    }
}
