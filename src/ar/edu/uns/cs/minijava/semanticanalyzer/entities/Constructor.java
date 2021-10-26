package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

//TODO: pensar que quizás podríamos hacer que constructor no herede de método

public class Constructor extends Method {
    public Constructor(Token identifierToken, Type returnType) throws SemanticException {
        super(identifierToken, returnType, MethodForm.DYNAMIC);
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
}
