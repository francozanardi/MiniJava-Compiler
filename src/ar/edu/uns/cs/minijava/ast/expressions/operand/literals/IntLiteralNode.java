package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;

public class IntLiteralNode extends LiteralNode {
    public IntLiteralNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    public Type check() throws SemanticException {
        return new IntType();
    }
}
