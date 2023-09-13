package ar.edu.uns.cs.minijava.ast.expressions.unaryexpressions;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.OperandNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public abstract class UnaryExpressionNode extends ExpressionNode {
    protected OperandNode operand;

    public UnaryExpressionNode(Token sentenceToken) {
        super(sentenceToken);
    }

    public OperandNode getOperand() {
        return operand;
    }

    public void setOperand(OperandNode operand) {
        this.operand = operand;
    }

    @Override
    public Type check() throws SemanticException {
        Type typeOfOperand = operand.check();
        Type typeSupported = getTypeSupported();

        if(!typeOfOperand.equals(typeSupported)){
            throw new SemanticException(sentenceToken, "Incompatibilidad de tipos. " +
                    "Se esperaba un operando de tipo " + typeSupported.getType() +
                    " pero se encontró un operando de tipo " + typeOfOperand);
        }

        return typeSupported;
    }

    protected abstract Type getTypeSupported();
}
