package ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.primitivetype;

import ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.BinaryExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;

public class PlusBinaryExpressionNode extends PrimitiveTypeBinaryExpressionNode {
    public PlusBinaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    protected Type getTypeSupportedForLeftExpression() {
        return new IntType();
    }

    @Override
    protected Type getTypeSupportedForRightExpression() {
        return new IntType();
    }

    @Override
    protected Type getTypeReturned() {
        return new IntType();
    }
}
