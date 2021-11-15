package ar.edu.uns.cs.minijava.ast.expressions.unaryexpressions;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;

public class NegationUnaryExpressionNode extends UnaryExpressionNode {
    public NegationUnaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    protected Type getTypeSupported() {
        return new BooleanType();
    }

    @Override
    public void generate() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(
                new Instruction(ZeroArgumentInstruction.NOT));
    }
}
