package ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.object;

import ar.edu.uns.cs.minijava.codegenerator.CodeGenerator;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.PredefinedClass;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.PredefinedMethod;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.object.methods.DebugPrintMethod;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.*;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;

import java.util.Collections;
import java.util.List;

public class Object extends PredefinedClass {
    public Object() {
        super(new Class(new Token(TokenName.IDENTIFICADOR_DE_CLASE, "Object", 0)));
    }

    @Override
    protected List<PredefinedMethod> createMethods() throws CodeGeneratorException {
        return List.of(new DebugPrintMethod(classCreated));
    }

    @Override
    protected List<Attribute> createAttributes() {
        return Collections.emptyList();
    }
}
