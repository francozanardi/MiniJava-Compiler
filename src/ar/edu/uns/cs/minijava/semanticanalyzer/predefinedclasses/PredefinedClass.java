package ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses;

import ar.edu.uns.cs.minijava.semanticanalyzer.entities.*;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.ReferenceType;

import java.util.List;

public abstract class PredefinedClass {
    protected Class classCreated;

    public PredefinedClass(Class classCreated) {
        this.classCreated = classCreated;

        try {
            create();
        } catch (SemanticException ignored) {
            //una clase bien predefinida no debería lanzar errores semánticos.
        }
    }

    protected void create() throws SemanticException {
        for(Attribute attribute : createAttributes()){
            classCreated.addAttribute(attribute.getIdentifierToken().getLexema(), attribute);
        }

        for(Method method : createMethods()){
            classCreated.addMethod(method.getIdentifierToken().getLexema(), method);
        }

        classCreated.setConstructor(createConstructor());
    }

    protected abstract List<Method> createMethods() throws EntityAlreadyExistsException;
    protected abstract List<Attribute> createAttributes() throws EntityAlreadyExistsException;

    protected Constructor createConstructor() throws SemanticException {
        return new Constructor(
                classCreated.getIdentifierToken(),
                new ReferenceType(classCreated.getIdentifierToken().getLexema())
        );
    }

    public Class getClassCreated() {
        return classCreated;
    }

    public void setClassCreated(Class classCreated) {
        this.classCreated = classCreated;
    }
}
