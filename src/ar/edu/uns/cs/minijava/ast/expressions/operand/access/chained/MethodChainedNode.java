package ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.ClassAccessNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Parameter;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;

import java.util.List;

public class MethodChainedNode extends ChainedNode {
    private final List<ExpressionNode> arguments;
    private Method methodCalled;

    public MethodChainedNode(Token methodToken, List<ExpressionNode> arguments) {
        super(methodToken);
        this.arguments = arguments;
    }

    @Override
    public Type check(Type previousType) throws SemanticException {
        Class classAssociatedWithType = getClassAssociatedToType(previousType);
        methodCalled = searchMethodCalled(classAssociatedWithType);

        checkParameters(methodCalled);

        if(nextChained != null){
            return nextChained.check(methodCalled.getType());
        }

        return methodCalled.getType();
    }

    private Method searchMethodCalled(Class classWhereToSearch) throws SemanticException {
        Method methodCalled = classWhereToSearch.getMethodById(sentenceToken.getLexema());

        if(methodCalled == null){
            throw new SemanticException(sentenceToken, "No se encontró el método " +
                    sentenceToken.getLexema() +
                    " en la clase " +
                    classWhereToSearch.getIdentifierToken().getLexema());
        }

        return methodCalled;
    }

    private void checkParameters(Method methodToCall) throws SemanticException {
        List<Parameter> parameters = methodToCall.getParametersInOrder();

        if(parameters.size() != arguments.size()){
            throw new SemanticException(sentenceToken, "La cantidad de argumentos enviados y parámetros esperados no coinciden");
        }

        for(int i = 0; i < arguments.size(); i++){
            Type argumentType = arguments.get(i).check();
            Type parameterType = parameters.get(i).getType();

            if(!argumentType.isSubtypeOf(parameterType)){
                throw new SemanticException(sentenceToken, "El argumento número " + (i+1) +
                        " se esperaba que fuera un subtipo del tipo " +
                        parameterType +
                        ", pero se encontró una expresión con el tipo " +
                        argumentType);
            }
        }
    }

    @Override
    public boolean isCallable() {
        return true;
    }

    @Override
    public boolean isAssignable() {
        return false;
    }

    @Override
    public boolean isStatic(Class classContainer) throws SemanticException {
        return this.searchMethodCalled(classContainer).getMethodForm().equals(MethodForm.STATIC);
    }

    @Override
    public void generate() throws CodeGeneratorException {
        removeThisIfIsStatic();
        saveReturnSpaceIfExists();
        loadArguments();
        loadVirtualTableIfIsNotStatic();
        loadMethodCalled();

        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.CALL));

        if(nextChained != null){
            nextChained.generate();
        }
    }

    private void removeThisIfIsStatic() throws CodeGeneratorException {
        if (methodCalledIsStatic()) {
            SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.POP));
        }
    }

    private boolean methodCalledIsStatic(){
        return methodCalled.getMethodForm().equals(MethodForm.STATIC);
    }

    protected void saveReturnSpaceIfExists() throws CodeGeneratorException {
        Type returnType = methodCalled.getType();

        if (!returnType.equals(new VoidType())) {
            SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.RMEM, 1));
            addSwapIfIsNotStatic();
        }
    }

    protected void addSwapIfIsNotStatic() throws CodeGeneratorException {
        if (!methodCalledIsStatic()) {
            SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.SWAP));
        }
    }

    protected void loadArguments() throws CodeGeneratorException {
        for (int i = arguments.size()-1; i >= 0; i--) {
            arguments.get(i).generate();
            addSwapIfIsNotStatic(); //bajamos el this
        }
    }

    protected void loadVirtualTableIfIsNotStatic() throws CodeGeneratorException {
        if (!methodCalledIsStatic()) {
            SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.DUP));
            SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.LOADREF, 0));
        }
    }

    protected void loadMethodCalled() throws CodeGeneratorException {
        if (methodCalledIsStatic()) {
            SymbolTable.getInstance().appendInstruction(
                    new Instruction(OneArgumentInstruction.PUSH, methodCalled.getBeginMethodLabel()));
        } else {
            SymbolTable.getInstance().appendInstruction(
                    new Instruction(OneArgumentInstruction.LOADREF, methodCalled.getOffset()));
        }
    }
}
