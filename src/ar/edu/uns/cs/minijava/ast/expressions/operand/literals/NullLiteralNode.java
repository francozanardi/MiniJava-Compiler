package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.NullType;

public class NullLiteralNode extends LiteralNode {
    public NullLiteralNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    public Type check() throws SemanticException {
        return new NullType();
    }

    @Override
    protected Instruction generateInstruction() {
        return new Instruction(OneArgumentInstruction.PUSH, 0);
    }
}
