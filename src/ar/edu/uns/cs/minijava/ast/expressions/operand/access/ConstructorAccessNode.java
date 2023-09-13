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
        Class classToInstantiate = getClassToInstantiate();

        if(classToInstantiate != null){
            return classToInstantiate.getConstructor();
        }

        return null;
    }

    private Class getClassToInstantiate(){
        return SymbolTable.getInstance().getClassById(sentenceToken.getLexema());
    }

    @Override
    public void generate() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.RMEM, 1));
        loadCIRSpace();
        loadMallocCall();
        associateVirtualTableToCIR();
        loadArguments();
        callConstructor();

        if(chained != null){
            chained.generate();
        }
    }

    private void loadCIRSpace() throws CodeGeneratorException {
        Class classToCreate = searchMethodCalled().getClassContainer();
        SymbolTable.getInstance().appendInstruction(
                new Instruction(OneArgumentInstruction.PUSH, classToCreate.getAttributesNumber()+1));
    }

    private void loadMallocCall() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(
                new Instruction(OneArgumentInstruction.PUSH, SymbolTable.getInstance().getMalloc().getLabel()));

        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.CALL));
    }

    private void associateVirtualTableToCIR() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.DUP));

        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.PUSH,
                getClassToInstantiate().getVirtualTableLabel()));

        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.STOREREF, 0));

        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.DUP));
    }

    private void callConstructor() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.PUSH,
                searchMethodCalled().getBeginMethodLabel()));
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.CALL));
    }
}
