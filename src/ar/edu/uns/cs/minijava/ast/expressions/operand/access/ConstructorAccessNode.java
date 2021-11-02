package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import java.util.List;

public class ConstructorAccessNode extends AccessNode {
    private String constructorName;
    private List<ExpressionNode> parameters;
}
