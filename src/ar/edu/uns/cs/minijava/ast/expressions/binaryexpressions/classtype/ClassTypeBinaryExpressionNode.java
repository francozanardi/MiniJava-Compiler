package ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.classtype;

import ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.BinaryExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public abstract class ClassTypeBinaryExpressionNode extends BinaryExpressionNode {
    public ClassTypeBinaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    protected abstract Type getReturnedType();

    @Override
    public Type check() throws SemanticException {
        Type leftExpressionType = getLeftExpression().check();
        Type rightExpressionType = getRightExpression().check();

        checkTypeCompatibility(leftExpressionType, rightExpressionType);

        return getReturnedType();
    }

    private void checkTypeCompatibility(Type type1, Type type2) throws SemanticException {
        if(!type1.isSubtypeOf(type2) && !type2.isSubtypeOf(type1)){
            throw new SemanticException(sentenceToken, "Incompatibilidad de tipos. " +
                    "No se pueden comparar los tipos " + type1 +
                    " y " + type2);
        }
    }
}
