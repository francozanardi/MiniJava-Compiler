package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.ReferenceType;

public class ThisAccessNode extends AccessNode {
    private final Method methodContainer;

    public ThisAccessNode(Token sentenceToken, Method methodContainer) {
        super(sentenceToken);
        this.methodContainer = methodContainer;
    }

    @Override
    public Type check() throws SemanticException {
        if(methodContainer.getMethodForm().equals(MethodForm.STATIC)){
            throw new SemanticException(sentenceToken, "No se puede utilizar la palabra reservada 'this' en un método estático.");
        }

        ReferenceType typeOfClassContainer = new ReferenceType(methodContainer.getClassContainer().getIdentifierToken().getLexema());

        if(chained != null){
            return checkCasting(chained.check(typeOfClassContainer));
        }

        return checkCasting(typeOfClassContainer);
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
