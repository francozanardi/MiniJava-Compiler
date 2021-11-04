package ar.edu.uns.cs.minijava.ast.sentences.assignments;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.AccessNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class EqualAssignmentNode extends AssignmentNode {
    private final ExpressionNode expressionToAssign;

    public EqualAssignmentNode(Token sentenceToken, AccessNode assignableNode, ExpressionNode expressionToAssign) {
        super(sentenceToken, assignableNode);
        this.expressionToAssign = expressionToAssign;
    }

    @Override
    public void check() throws SemanticException {
        if(!assignableNode.isLastElementChainedAssignable()){
            throw new SemanticException(sentenceToken, "Se esperaba una entidad asignable.");
        }

        Type typeOfAssignableEntity = assignableNode.check();
        Type typeOfExpression = expressionToAssign.check();

        if(!typeOfExpression.isSubtypeOf(typeOfAssignableEntity)){
            throw new SemanticException(sentenceToken, "No se puede asignar una expresión de tipo " +
                    typeOfExpression.getType() +
                    " a una entidad de tipo " +
                    typeOfAssignableEntity.getType());
        }
    }
}
