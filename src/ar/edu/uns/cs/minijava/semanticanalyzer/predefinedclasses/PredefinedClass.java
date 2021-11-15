package ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses;

import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNodeImpl;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.*;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.ReferenceType;

import java.util.List;

public abstract class PredefinedClass {
    protected final Class classCreated;

    public PredefinedClass(Class classCreated) {
        this.classCreated = classCreated;

        try {
            create();
        } catch (SemanticException ignored) {
            //una clase bien predefinida no debería lanzar errores semánticos.
        }
    }

    public Class getClassCreated() {
        return classCreated;
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
        Constructor constructor = new Constructor(
                classCreated.getIdentifierToken(),
                new ReferenceType(classCreated.getIdentifierToken().getLexema())
        );

        constructor.setBodyBlock(new BlockSentenceNodeImpl(classCreated.getIdentifierToken(), constructor, null));
        constructor.setClassContainer(classCreated);

        return constructor;
    }

    public void addClassToSymbolTable(){
        Class objectClass = SymbolTable.getInstance().getClassById("Object");

        try {
            SymbolTable.getInstance().addClass(classCreated.getIdentifierToken().getLexema(), classCreated);
        } catch (EntityAlreadyExistsException ignored) {
            //una clase predefinida no debería existir por lo que podemos asumir que no producirá esta excepción
        }

        if(objectClass != null){
            classCreated.setTokenOfParentClass(objectClass.getIdentifierToken());
        }
    }
}
