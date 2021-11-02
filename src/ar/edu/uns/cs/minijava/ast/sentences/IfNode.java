package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;

public class IfNode extends SentenceNode {
    private ExpressionNode condition;
    private SentenceNode ifBody;
    private SentenceNode elseBody;
}
