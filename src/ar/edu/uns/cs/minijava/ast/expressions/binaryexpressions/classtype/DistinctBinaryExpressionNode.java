package ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.classtype;

import ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.BinaryExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;

public class DistinctBinaryExpressionNode extends ClassTypeBinaryExpressionNode {
    public DistinctBinaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    protected Type getReturnedType() {
        return new BooleanType();
    }
}
