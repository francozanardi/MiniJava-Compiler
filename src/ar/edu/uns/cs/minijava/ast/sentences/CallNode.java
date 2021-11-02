package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.operand.access.AccessNode;

public class CallNode extends SentenceNode {
    private AccessNode nodeToInvoke; //TODO: pensar si es correcto que esto sea AccessNode en lugar de MethodAccessNode
}
