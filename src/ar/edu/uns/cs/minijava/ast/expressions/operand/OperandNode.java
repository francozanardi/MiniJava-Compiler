package ar.edu.uns.cs.minijava.ast.expressions.operand;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public abstract class OperandNode extends ExpressionNode {
    public OperandNode(Token sentenceToken) {
        super(sentenceToken);
    }
}
