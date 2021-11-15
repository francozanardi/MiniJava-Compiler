package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.ast.expressions.operand.OperandNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;

public abstract class LiteralNode extends OperandNode {
    public LiteralNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    public void generate() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(generateInstruction());
    }

    protected abstract Instruction generateInstruction();
}
