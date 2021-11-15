package ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.primitivetype;

import ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.BinaryExpressionNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public abstract class PrimitiveTypeBinaryExpressionNode extends BinaryExpressionNode {
    public PrimitiveTypeBinaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    protected abstract Type getTypeSupportedForLeftExpression();
    protected abstract Type getTypeSupportedForRightExpression();
    protected abstract Type getTypeReturned();

    @Override
    public Type check() throws SemanticException {
        Type leftExpressionTypeSupported = getTypeSupportedForLeftExpression();
        Type rightExpressionTypeSupported = getTypeSupportedForRightExpression();

        Type leftExpressionType = leftExpression.check();
        Type rightExpressionType = rightExpression.check();

        checkTypeCompatibility(leftExpressionTypeSupported, leftExpressionType);
        checkTypeCompatibility(rightExpressionTypeSupported, rightExpressionType);

        return getTypeReturned();
    }

    private void checkTypeCompatibility(Type typeSupported, Type typeObtained) throws SemanticException {
        if(!typeSupported.equals(typeObtained)){
            throw new SemanticException(sentenceToken, "Incompatibilidad de tipos. " +
                    "Se esperaba un operando del tipo " + typeSupported +
                    " pero se encontró una expresión con el tipo " + typeObtained);
        }
    }
}
