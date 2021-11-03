package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class ReturnSentenceNode extends SentenceNode {
    private final ExpressionNode expressionToReturn;
    private final Method methodContainer;

    public ReturnSentenceNode(Token sentenceToken, ExpressionNode expressionToReturn, Method methodContainer) {
        super(sentenceToken);
        this.expressionToReturn = expressionToReturn;
        this.methodContainer = methodContainer;
    }

    @Override
    public void check() throws SemanticException {
        if(!methodContainer.canHasReturn()){
            throw new SemanticException(sentenceToken, "El método " +
                    methodContainer.getIdentifierToken().getLexema() +
                    " no puede tener una sentencia return.");
        }

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
