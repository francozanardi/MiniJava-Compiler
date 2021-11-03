package ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public abstract class BinaryExpressionNode extends ExpressionNode {
    private ExpressionNode leftExpression;
    private ExpressionNode rightExpression;

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
}
