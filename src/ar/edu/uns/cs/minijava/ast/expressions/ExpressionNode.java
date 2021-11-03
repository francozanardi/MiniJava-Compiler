package ar.edu.uns.cs.minijava.ast.expressions;

import ar.edu.uns.cs.minijava.ast.Node;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public abstract class ExpressionNode extends Node {
    public ExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    public abstract Type check() throws SemanticException;
}
