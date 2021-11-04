package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.StringType;

public class StringLiteralNode extends LiteralNode {
    public StringLiteralNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    public Type check() throws SemanticException {
        return new StringType();
    }
}
