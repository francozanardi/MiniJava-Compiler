package ar.edu.uns.cs.minijava.ast.expressions.unaryexpressions;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.OperandNode;

public abstract class UnaryExpressionNode extends ExpressionNode {
    private OperandNode operand;

    public OperandNode getOperand() {
        return operand;
    }

    public void setOperand(OperandNode operand) {
        this.operand = operand;
    }
}
