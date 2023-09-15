package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;

public class IfSentenceNode extends SentenceNode {
    private final ExpressionNode condition;
    private final SentenceNode ifBody;
    private final SentenceNode elseBody;

    public IfSentenceNode(Token sentenceToken, ExpressionNode condition, SentenceNode ifBody, SentenceNode elseBody) {
        super(sentenceToken);
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    @Override
    public boolean containsReturnSentence() {
        if (this.elseBody == null) {
            return false;
        }
        return this.ifBody.containsReturnSentence() && this.elseBody.containsReturnSentence();
    }

    @Override
    public void check() throws SemanticException {
        Type expressionType = condition.check();
        if(!expressionType.equals(new BooleanType())){
            throw new SemanticException(sentenceToken,
                    "La expresión de la condición del if debe ser de tipo booleano, " +
                    "sin embargo se encontró una expresión de tipo '" + expressionType.getType() + "'");
        }

        ifBody.check();
        if(elseBody != null){
            elseBody.check();
        }
    }

    @Override
    public void generate() throws CodeGeneratorException {
        Label elseLabel = SymbolTable.getInstance().getUniqueLabel();
        Label endIfLabel = SymbolTable.getInstance().getUniqueLabel();

        condition.generate();
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.BF, elseLabel));

        ifBody.generate();
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.JUMP, endIfLabel));

        SymbolTable.getInstance().appendInstruction(new Instruction(elseLabel));
        if(elseBody != null){
            elseBody.generate();
        } else {
            SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.NOP));
        }

        Instruction endIfInstruction = new Instruction(ZeroArgumentInstruction.NOP);
        endIfInstruction.setLabel(endIfLabel);
        SymbolTable.getInstance().appendInstruction(endIfInstruction);
    }

}
