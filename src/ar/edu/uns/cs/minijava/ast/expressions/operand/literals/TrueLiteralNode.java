package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;

public class TrueLiteralNode extends LiteralNode {
    public TrueLiteralNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    public Type check() throws SemanticException {
        return new BooleanType();
    }
}
