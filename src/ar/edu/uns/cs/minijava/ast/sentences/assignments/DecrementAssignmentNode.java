package ar.edu.uns.cs.minijava.ast.sentences.assignments;

import ar.edu.uns.cs.minijava.ast.expressions.operand.access.AccessNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;

public class DecrementAssignmentNode extends AssignmentNode {

    public DecrementAssignmentNode(Token sentenceToken, AccessNode assignableNode) {
        super(sentenceToken, assignableNode);
    }

    @Override
    public void check() throws SemanticException {
        if(!assignableNode.isLastElementChainedAssignable()){
            throw new SemanticException(sentenceToken, "Se esperaba una entidad asignable.");
        }

        Type typeOfAssignableEntity = assignableNode.check();
        Type typeSupported = new IntType();

        if(!typeOfAssignableEntity.equals(typeSupported)){
            throw new SemanticException(sentenceToken, "La asignación de decremento no se puede realizar " +
                    "en entidades del tipo " + typeOfAssignableEntity.getType() +
                    ". Se esperaba una entidad con el tipo " + typeSupported.getType());
        }
    }

    @Override
    public void generate() throws CodeGeneratorException {
        assignableNode.generate();
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.PUSH, 1));
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.SUB));
    }
}
