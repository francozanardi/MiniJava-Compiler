package ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.classtype;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;

public class ComparatorBinaryExpressionNode extends ClassTypeBinaryExpressionNode {
    public ComparatorBinaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    protected Type getReturnedType() {
        return new BooleanType();
    }

    @Override
    protected Instruction generateInstruction() {
        return new Instruction(ZeroArgumentInstruction.EQ);
    }
}
