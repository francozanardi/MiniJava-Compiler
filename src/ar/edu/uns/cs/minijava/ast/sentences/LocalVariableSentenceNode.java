package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.LocalVariable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class LocalVariableSentenceNode extends SentenceNode {
    private final LocalVariable localVariable;

    public LocalVariableSentenceNode(LocalVariable localVariable) {
        super(localVariable.getIdentifierToken());
        this.localVariable = localVariable;
    }

    @Override
    public void check() throws SemanticException {
        ExpressionNode expressionAssigned = localVariable.getExpressionAssigned();
        if(expressionAssigned != null){
            Type expressionAssignedType = expressionAssigned.check();

            if(!expressionAssignedType.isSubtypeOf(localVariable.getType())){
                throw new SemanticException(sentenceToken, "Incompatibilidad de tipos. " +
                        "La variable local esperaba un subtipo del tipo '" +
                        localVariable.getType().getType() +
                        "'. Sin embargo, se le está asginando una expresión con el tipo '" +
                        expressionAssignedType.getType() + "'");
            }
        }
    }

    public LocalVariable getLocalVariable() {
        return localVariable;
    }
}
