package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;

public class ReturnSentenceNode extends SentenceNode {
    private final ExpressionNode expressionToReturn;
    private final Method methodContainer;
    private final BlockSentenceNode blockContainer;

    public ReturnSentenceNode(Token sentenceToken, ExpressionNode expressionToReturn, Method methodContainer, BlockSentenceNode blockContainer) {
        super(sentenceToken);
        this.expressionToReturn = expressionToReturn;
        this.methodContainer = methodContainer;
        this.blockContainer = blockContainer;
    }

    @Override
    public void check() throws SemanticException {
        if(!methodContainer.canHasReturn()){
            throw new SemanticException(sentenceToken, "El método " +
                    methodContainer.getIdentifierToken().getLexema() +
                    " no puede tener una sentencia return.");
        }

        checkIfExpressionIsVoid();
        checkIfExpressionIsTypeCompatible();
    }

    private void checkIfExpressionIsVoid() throws SemanticException {
        if(expressionToReturn == null){
            Type methodReturnType = methodContainer.getType();

            if(!methodReturnType.equals(new VoidType())){
                throw new SemanticException(sentenceToken, "Retorno inválido." +
                        "Un retorno vacío solo es permitido en un método void.");
            }
        }
    }

    private void checkIfExpressionIsTypeCompatible() throws SemanticException {
        if(expressionToReturn != null){
            Type expressionType = expressionToReturn.check();
            Type methodReturnType = methodContainer.getType();

            if(!expressionType.isSubtypeOf(methodReturnType)){
                throw new SemanticException(sentenceToken, "Incompatibilidad de tipos. " +
                        "El método esperaba retornar un subtipo del tipo '" +
                        methodReturnType.getType() +
                        "'. Sin embargo, se está retornando una expresión con el tipo '" +
                        expressionType.getType() +
                        "'");
            }
        }
    }

    @Override
    public void generate() throws CodeGeneratorException {
        if(expressionToReturn != null){
            expressionToReturn.generate();
            SymbolTable.getInstance().appendInstruction(
                    new Instruction(OneArgumentInstruction.STORE, getRASize()));
        }

        freeMemoryReserved();

        SymbolTable.getInstance().appendInstruction(
                new Instruction(
                        OneArgumentInstruction.JUMP,
                        methodContainer.getEndMethodLabel()
                )
        );
    }

    private int getRASize() {
        int raSize = methodContainer.getParameterNumber() + 3;

        if(!methodContainer.getMethodForm().equals(MethodForm.STATIC)){
            raSize++;
        }

        return raSize;
    }

    private void freeMemoryReserved() throws CodeGeneratorException {
        if(blockContainer.getCurrentMemoryReservedInMethod() > 0) {
            SymbolTable.getInstance().appendInstruction(
                    new Instruction(
                            OneArgumentInstruction.FMEM,
                            blockContainer.getCurrentMemoryReservedInMethod()
                    )
            );
        }
    }
}
