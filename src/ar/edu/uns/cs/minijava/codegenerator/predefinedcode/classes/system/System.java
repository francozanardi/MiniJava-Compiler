package ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.PredefinedClass;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.PredefinedMethod;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods.ReadMethod;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods.print.PrintBMethod;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods.print.PrintCMethod;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods.print.PrintIMethod;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods.print.PrintSMethod;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods.println.*;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.*;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;

import java.util.Collections;
import java.util.List;

public class System extends PredefinedClass {

    public System() {
        super(new Class(new Token(TokenName.IDENTIFICADOR_DE_CLASE, "System", 0)));
    }

    @Override
    protected List<PredefinedMethod> createMethods() throws CodeGeneratorException {
        return List.of(
                new ReadMethod(classCreated),
                new PrintBMethod(classCreated),
                new PrintCMethod(classCreated),
                new PrintIMethod(classCreated),
                new PrintSMethod(classCreated),
                new PrintLnMethod(classCreated),
                new PrintBLnMethod(classCreated),
                new PrintCLnMethod(classCreated),
                new PrintILnMethod(classCreated),
                new PrintSLnMethod(classCreated)
        );
    }

    @Override
    protected List<Attribute> createAttributes() {
        return Collections.emptyList();
    }
}
