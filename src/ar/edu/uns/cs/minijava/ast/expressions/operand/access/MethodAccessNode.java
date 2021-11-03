package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained.ChainedNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

import java.util.ArrayList;
import java.util.List;

public class MethodAccessNode extends AccessNode {
    private List<ExpressionNode> parameters;
    private Class classContainer;

    public MethodAccessNode(Token methodToken, Class classContainer, List<ExpressionNode> parameters) {
        super(methodToken);
        this.parameters = parameters;
        this.classContainer = classContainer;
    }

    @Override
    public Type check() throws SemanticException {
        Method methodFound = classContainer.getMethodById(sentenceToken.getLexema());
        if(methodFound == null){
            //throw exception
        }

        if(chained != null){
            chained.check(methodFound.getType());
        }
        return methodFound.getType();
        //more validations...
    }

    @Override
    public boolean isCallable() {
        return true;
    }
}
