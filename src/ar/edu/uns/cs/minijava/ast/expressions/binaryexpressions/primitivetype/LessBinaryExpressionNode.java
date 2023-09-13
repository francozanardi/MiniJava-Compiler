package ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.primitivetype;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;

public class LessBinaryExpressionNode extends PrimitiveTypeBinaryExpressionNode {
    public LessBinaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    protected Type getTypeSupportedForLeftExpression() {
        return new IntType();
    }

    @Override
    protected Type getTypeSupportedForRightExpression() {
        return new IntType();
    }

    @Override
    protected Type getTypeReturned() {
        return new BooleanType();
    }

    @Override
    protected Instruction generateInstruction() {
        return new Instruction(ZeroArgumentInstruction.LT);
    }
}
