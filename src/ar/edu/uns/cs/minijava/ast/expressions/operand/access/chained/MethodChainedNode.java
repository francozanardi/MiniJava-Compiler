package ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;

import java.util.List;

public class MethodChainedNode extends ChainedNode {
    private String methodName;
    private List<ExpressionNode> parameters;
}
