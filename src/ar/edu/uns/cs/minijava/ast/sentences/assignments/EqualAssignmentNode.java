package ar.edu.uns.cs.minijava.ast.sentences.assignments;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.AccessNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;

public class EqualAssignmentNode extends AssignmentNode {
    private ExpressionNode expressionToAssign;

    public EqualAssignmentNode(Token sentenceToken, AccessNode assignableNode, ExpressionNode expressionToAssign) {
        super(sentenceToken, assignableNode);
        this.expressionToAssign = expressionToAssign;
    }

    @Override
    public void check() throws SemanticException {
        assignableNode.check();

        if(!assignableNode.isLastElementChainedAssignable()){
            throw new SemanticException(sentenceToken, "Se esperaba una entidad asignable.");
        }

        //TODO: nos falta chequear si existe la variable local en el bloque, o en los bloques ancestros
        // o es parámetro
        // o es atributo de instancia.
        // En orden.

        
    }
}
