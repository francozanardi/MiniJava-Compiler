package ar.edu.uns.cs.minijava.ast.sentences.assignments;

import ar.edu.uns.cs.minijava.ast.expressions.operand.access.AccessNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;

public class DecrementAssignmentNode extends AssignmentNode {

    public DecrementAssignmentNode(Token sentenceToken, AccessNode assignableNode) {
        super(sentenceToken, assignableNode);
    }

    @Override
    public void check() throws SemanticException {

    }
}
