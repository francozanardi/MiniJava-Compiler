package ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Parameter;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

import java.util.List;

public class MethodChainedNode extends ChainedNode {
    private final List<ExpressionNode> arguments;

    public MethodChainedNode(Token methodToken, List<ExpressionNode> arguments) {
        super(methodToken);
        this.arguments = arguments;
    }

    @Override
    public Type check(Type previousType) throws SemanticException {
        Class classAssociatedWithType = getClassAssociatedToType(previousType);
        Method methodFound = searchMethodCalled(classAssociatedWithType);

        checkParameters(methodFound);

        if(nextChained != null){
            return nextChained.check(methodFound.getType());
        }

        return methodFound.getType();
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
}
