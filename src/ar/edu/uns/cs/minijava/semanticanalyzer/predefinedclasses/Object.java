package ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.*;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;

import java.util.Collections;
import java.util.List;

public class Object extends PredefinedClass {

    public Object() {
        super(new Class(new Token(TokenName.IDENTIFICADOR_DE_CLASE, "Object", 0)));
    }

    @Override
    protected List<Method> createMethods() throws EntityAlreadyExistsException {
        Method debugPrint = new Method(
                new Token(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE, "debugPrint", 0),
                new VoidType(),
                MethodForm.STATIC
        );

        Parameter i = new Parameter(
                new Token(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE, "i", 0),
                new IntType()
        );

        debugPrint.appendParameter("i", i);

        return List.of(debugPrint);
    }

    @Override
    protected List<Attribute> createAttributes() {
        return Collections.emptyList();
    }
}
