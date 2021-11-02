package ar.edu.uns.cs.minijava.ast.sentences.assignments;

import ar.edu.uns.cs.minijava.ast.expressions.operand.access.AccessNode;
import ar.edu.uns.cs.minijava.ast.sentences.SentenceNode;

public abstract class AssignmentNode extends SentenceNode {
    private AccessNode assignableNode;
}
