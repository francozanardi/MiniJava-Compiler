package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.CharType;

public class CharLiteralNode extends LiteralNode {
    public CharLiteralNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    public Type check() throws SemanticException {
        return new CharType();
    }

    @Override
    protected Instruction generateInstruction() {
        int value = sentenceToken.getLexema().charAt(1); //assuming a character saved in single quotes
        //TODO: chequear qué pasa con carácteres como \n o \'
        return new Instruction(OneArgumentInstruction.PUSH, value);
    }
}
