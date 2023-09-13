package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.sentences.assignments.AssignmentNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
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
    protected LocalVariable findLocalVariableInCurrentBlock(String localVariableName) {
        if(variable != null && variable.getIdentifierToken().getLexema().equals(localVariableName)){
            return variable;
        }

        return null;
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

        localVariable.setOffset((-1)*containerBlock.getLocalVariablesNumberInMethod());
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

    @Override
    public int getLocalVariablesNumberInMethod() {
        if(containerBlock != null){
            return containerBlock.getLocalVariablesNumberInMethod() + 1;
        }

        return 1;
    }

    @Override
    public void generate() throws CodeGeneratorException {
        Label forEndLabel = SymbolTable.getInstance().getUniqueLabel();
        Label forConditionalLabel = SymbolTable.getInstance().getUniqueLabel();

        variable.getSentence().generate();
        modifyMemoryReservedWith(1);

        SymbolTable.getInstance().appendInstruction(new Instruction(forConditionalLabel));
        condition.generate();
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.BF, forEndLabel));

        body.generate();

        assignment.generate();
        SymbolTable.getInstance().appendInstruction(
                new Instruction(OneArgumentInstruction.JUMP, forConditionalLabel));

        SymbolTable.getInstance().appendInstruction(new Instruction(forEndLabel));
        SymbolTable.getInstance().appendInstruction(
                new Instruction(OneArgumentInstruction.FMEM, 1));
        modifyMemoryReservedWith(-1);
    }
}
