package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

import java.util.ArrayList;
import java.util.List;

public class ConstructorAccessNode extends AccessNode {
    private Token constructorToken;
    private List<ExpressionNode> parameters;

    public ConstructorAccessNode(Token constructorToken) {
        this.constructorToken = constructorToken;
        this.parameters = new ArrayList<>();
    }

    public List<ExpressionNode> getParameters() {
        return parameters;
    }

    public void setParameters(List<ExpressionNode> parameters) {
        this.parameters = parameters;
    }

    public Token getConstructorToken() {
        return constructorToken;
    }

    public void setConstructorToken(Token constructorToken) {
        this.constructorToken = constructorToken;
    }
}
