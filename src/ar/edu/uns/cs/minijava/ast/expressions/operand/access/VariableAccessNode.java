package ar.edu.uns.cs.minijava.ast.expressions.operand.access;


import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public class VariableAccessNode extends AccessNode {
    private Token variableToken;

    public VariableAccessNode(Token variableToken) {
        this.variableToken = variableToken;
    }
}
