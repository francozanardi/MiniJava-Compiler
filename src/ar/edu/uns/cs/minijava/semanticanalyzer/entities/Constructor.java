package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class Constructor extends Method {
    public Constructor(Token identifierToken, Type returnType) throws SemanticException {
        super(identifierToken, returnType, MethodForm.CONSTRUCTOR);
        checkConstructorName();
    }

    private void checkConstructorName() throws SemanticException {
        if(!identifierToken.getLexema().equals(type.getType())){
            throw new SemanticException(identifierToken, "El nombre del contructor " +
                    identifierToken.getLexema() +
                    " no conincide con el nombre de la clase " +
                    type.getType());
        }
    }

    @Override
    public boolean canHasReturn() {
        return false;
    }

    @Override
    public void setClassContainer(Class classContainer) {
        super.setClassContainer(classContainer);

        beginMethodLabel = new Label("constructor_" + classContainer.getIdentifierToken().getLexema());
    }
}
