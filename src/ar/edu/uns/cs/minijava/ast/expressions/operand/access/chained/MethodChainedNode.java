package ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

import java.util.List;

public class MethodChainedNode extends ChainedNode {
    private List<ExpressionNode> parameters;
    private Class classContainer;

    public MethodChainedNode(Token methodToken, Class classContainer, List<ExpressionNode> parameters) {
        super(methodToken);
        this.parameters = parameters;
        this.classContainer = classContainer;
    }

    @Override
    public Type check(Type previousType) {
        return null;
    }

    @Override
    public boolean isCallable() {
        return true;
    }

    @Override
    public boolean isAssignable() {
        return false;
    }
}
