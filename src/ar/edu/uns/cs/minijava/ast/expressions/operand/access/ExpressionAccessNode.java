package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class ExpressionAccessNode extends AccessNode {
    private final ExpressionNode expression;

    public ExpressionAccessNode(Token sentenceToken, ExpressionNode expression) {
        super(sentenceToken);
        this.expression = expression;
    }

    @Override
    public Type check() throws SemanticException {
        Type expressionType = expression.check();

        if(chained != null){
            return checkCasting(chained.check(expressionType));
        }

        //TODO: pensar que podríamos retornar una excepción sino existe el encadenado.
        //Habría que analizar bien cuándo se usa el acceso.

        return checkCasting(expressionType);
    }

    @Override
    public boolean isCallable() {
        return false;
    }

    @Override
    public boolean isAssignable() {
        return false;
    }
}
