package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;

public class VoidLiteralNode extends LiteralNode {
    public VoidLiteralNode() {
        super(null);
    }

    @Override
    public Type check() throws SemanticException {
        return new VoidType();
    }
}
