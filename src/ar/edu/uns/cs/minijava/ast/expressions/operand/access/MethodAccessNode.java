package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Parameter;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

import java.util.List;

public class MethodAccessNode extends AccessNode {
    protected final List<ExpressionNode> arguments;
    protected final Method methodWhereIsCalled;

    public MethodAccessNode(Token methodToken, Method methodWhereIsCalled, List<ExpressionNode> arguments) {
        super(methodToken);
        this.arguments = arguments;
        this.methodWhereIsCalled = methodWhereIsCalled;
    }

    @Override
    public Type check() throws SemanticException {
        Method methodFound = findMethodCalled();

        checkMethodExistence(methodFound);
        checkIfMethodIsCallable(methodFound);
        checkParameters(methodFound);

        if(chained != null){
            return checkCasting(chained.check(methodFound.getType()));
        }

        return checkCasting(methodFound.getType());
    }

    protected Method findMethodCalled(){
        return methodWhereIsCalled.getClassContainer().getMethodById(sentenceToken.getLexema());
    }

    protected void checkMethodExistence(Method methodToCall) throws SemanticException {
        if(methodToCall == null){
            throw new SemanticException(sentenceToken, "No se encontró el método " +
                    sentenceToken.getLexema() +
                    " en la clase " +
                    methodWhereIsCalled.getClassContainer().getIdentifierToken().getLexema());
        }
    }

    protected void checkIfMethodIsCallable(Method methodToCall) throws SemanticException {
        if(     methodWhereIsCalled.getMethodForm().equals(MethodForm.STATIC) &&
                methodToCall.getMethodForm().equals(MethodForm.DYNAMIC)){
            throw new SemanticException(sentenceToken, "No se puede invocar un método dinámico desde un método estático.");
        }
    }

    protected void checkParameters(Method methodToCall) throws SemanticException {
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
