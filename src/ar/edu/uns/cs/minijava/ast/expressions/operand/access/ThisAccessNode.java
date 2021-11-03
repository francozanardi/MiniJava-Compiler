package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public class ThisAccessNode extends AccessNode {
    private Token thisToken;

    public ThisAccessNode(Token thisToken) {
        this.thisToken = thisToken;
    }
}
