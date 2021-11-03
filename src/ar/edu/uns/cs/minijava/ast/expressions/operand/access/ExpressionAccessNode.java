package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;

public class ExpressionAccessNode extends AccessNode {
    private ExpressionNode expression;

    public ExpressionAccessNode(ExpressionNode expression) {
        this.expression = expression;
    }
}
