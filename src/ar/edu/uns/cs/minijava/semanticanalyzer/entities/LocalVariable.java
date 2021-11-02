package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class LocalVariable extends EntityWithType {
    private ExpressionNode expressionAssigned;

    public LocalVariable(Token identifierToken, Type type) {
        super(identifierToken, type);
    }

    public ExpressionNode getExpressionAssigned() {
        return expressionAssigned;
    }

    public void setExpressionAssigned(ExpressionNode expressionAssigned) {
        this.expressionAssigned = expressionAssigned;
    }
}
