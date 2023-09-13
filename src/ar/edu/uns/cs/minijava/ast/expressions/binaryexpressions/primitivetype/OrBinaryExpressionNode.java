package ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.primitivetype;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;

public class OrBinaryExpressionNode extends PrimitiveTypeBinaryExpressionNode {
    public OrBinaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    protected Type getTypeSupportedForLeftExpression() {
        return new BooleanType();
    }

    @Override
    protected Type getTypeSupportedForRightExpression() {
        return new BooleanType();
    }

    @Override
    protected Type getTypeReturned() {
        return new BooleanType();
    }

    @Override
    protected Instruction generateInstruction() {
        return new Instruction(ZeroArgumentInstruction.OR);
    }
}
