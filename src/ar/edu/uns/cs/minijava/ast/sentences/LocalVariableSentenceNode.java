package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
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
        checkTypeExistence();

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

    private void checkTypeExistence() throws SemanticException {
        Class classType;
        if(localVariable.getType().requireCheckExistence()){
            classType = SymbolTable.getInstance().getClassById(localVariable.getType().getType());
            if(classType == null){
                throw new SemanticException(sentenceToken, "La variable local " +
                        sentenceToken.getLexema() +
                        " se ha declarado de un tipo inexistente " +
                        localVariable.getType());
            }
        }
    }

    public LocalVariable getLocalVariable() {
        return localVariable;
    }
}
