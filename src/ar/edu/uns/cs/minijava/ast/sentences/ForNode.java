package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.sentences.assignments.AssignmentNode;

public class ForNode extends SentenceNode {
    //private LocalVariable variable;
    private ExpressionNode condition;
    private AssignmentNode assignment;
    private SentenceNode body;
}
