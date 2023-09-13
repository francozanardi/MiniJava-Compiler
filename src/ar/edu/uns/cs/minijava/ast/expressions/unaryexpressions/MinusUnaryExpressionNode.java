package ar.edu.uns.cs.minijava.ast.expressions.unaryexpressions;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;

public class MinusUnaryExpressionNode extends UnaryExpressionNode {
    public MinusUnaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    protected Type getTypeSupported() {
        return new IntType();
    }

    @Override
    public void generate() throws CodeGeneratorException {
        operand.generate();
        SymbolTable.getInstance().appendInstruction(
                new Instruction(ZeroArgumentInstruction.NEG));
    }
}
