package ar.edu.uns.cs.minijava.ast.expressions.unaryexpressions;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;

public class MinusUnaryExpressionNode extends UnaryExpressionNode {
    public MinusUnaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    protected Type getTypeSupported() {
        return new IntType();
    }

}
