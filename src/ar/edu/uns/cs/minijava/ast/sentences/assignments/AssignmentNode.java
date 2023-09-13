package ar.edu.uns.cs.minijava.ast.sentences.assignments;

import ar.edu.uns.cs.minijava.ast.expressions.operand.access.AccessNode;
import ar.edu.uns.cs.minijava.ast.sentences.SentenceNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public abstract class AssignmentNode extends SentenceNode {
    protected AccessNode assignableNode;

    public AssignmentNode(Token sentenceToken, AccessNode assignableNode) {
        super(sentenceToken);
        this.assignableNode = assignableNode;
    }
}
