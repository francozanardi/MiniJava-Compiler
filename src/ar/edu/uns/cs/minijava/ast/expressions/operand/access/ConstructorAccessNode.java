package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;

import java.util.List;

public class ConstructorAccessNode extends MethodAccessNode {

    public ConstructorAccessNode(Token constructorToken, Method methodWhereIsUsed, List<ExpressionNode> arguments) {
        super(constructorToken, methodWhereIsUsed, arguments);
    }

    @Override
    protected Method searchMethodCalled() {
        Class classToInstantiate = SymbolTable.getInstance().getClassById(sentenceToken.getLexema());

        if(classToInstantiate != null){
            return classToInstantiate.getConstructor();
        }

        return null;
    }

    @Override
    public void generate() throws CodeGeneratorException {
        saveReturnSpaceIfExists();
        loadCIRSpace();
        loadArguments();
        loadVirtualTable();
        loadMethodCalledOffset();

        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.CALL));

        if(chained != null){
            chained.generate();
        }
    }

    private void loadCIRSpace() throws CodeGeneratorException {
        Class classToCreate = searchMethodCalled().getClassContainer();
        SymbolTable.getInstance().appendInstruction(
                new Instruction(OneArgumentInstruction.PUSH, classToCreate.getAttributesNumber()+1));
    }
}
