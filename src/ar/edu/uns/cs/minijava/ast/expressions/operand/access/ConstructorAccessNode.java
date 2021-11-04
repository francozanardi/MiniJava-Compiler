package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;

import java.util.List;

public class ConstructorAccessNode extends MethodAccessNode {

    public ConstructorAccessNode(Token constructorToken, Method methodWhereIsUsed, List<ExpressionNode> arguments) {
        super(constructorToken, methodWhereIsUsed, arguments);
    }

    @Override
    protected Method findMethodCalled() {
        Class classToInstantiate = SymbolTable.getInstance().getClassById(sentenceToken.getLexema());

        if(classToInstantiate != null){
            return classToInstantiate.getConstructor();
        }

        return null;
    }
}
