package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;

import java.util.List;

public class MethodAccessNode extends AccessNode {
    private String methodName;
    private List<ExpressionNode> parameters;
}
