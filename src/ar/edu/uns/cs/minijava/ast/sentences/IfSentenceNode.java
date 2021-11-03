package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;

public class IfSentenceNode extends SentenceNode {
    private final ExpressionNode condition;
    private final SentenceNode ifBody;
    private final SentenceNode elseBody;

    public IfSentenceNode(Token sentenceToken, ExpressionNode condition, SentenceNode ifBody, SentenceNode elseBody) {
        super(sentenceToken);
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    @Override
    public void check() throws SemanticException {
        Type expressionType = condition.check();
        if(!expressionType.equals(new BooleanType())){
            throw new SemanticException(sentenceToken,
                    "La expresión de la condición del if debe ser de tipo booleano, " +
                    "sin embargo se encontró una expresión de tipo '" + expressionType.getType() + "'");
        }

        ifBody.check();
        if(elseBody != null){
            elseBody.check();
        }
    }
}
