package ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public abstract class BinaryExpressionNode extends ExpressionNode {
    protected ExpressionNode leftExpression;
    protected ExpressionNode rightExpression;

    public BinaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    public ExpressionNode getLeftExpression() {
        return leftExpression;
    }

    public void setLeftExpression(ExpressionNode leftExpression) {
        this.leftExpression = leftExpression;
    }

    public ExpressionNode getRightExpression() {
        return rightExpression;
    }

    public void setRightExpression(ExpressionNode rightExpression) {
        this.rightExpression = rightExpression;
    }

    @Override
    public void generate() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(generateInstruction());
    }

    protected abstract Instruction generateInstruction();
}
