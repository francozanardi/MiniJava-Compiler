package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.ast.expressions.operand.OperandNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;

public abstract class LiteralNode extends OperandNode {
    public LiteralNode(Token sentenceToken) {
        super(sentenceToken);
    }
}
