package ar.edu.uns.cs.minijava.ast.expressions.unaryexpressions;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class WithoutOperatorUnaryExpressionNode extends UnaryExpressionNode {
    public WithoutOperatorUnaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    public Type check() throws SemanticException {
        return operand.check();
    }

    @Override
    protected Type getTypeSupported() {
        return null;
    }
}
