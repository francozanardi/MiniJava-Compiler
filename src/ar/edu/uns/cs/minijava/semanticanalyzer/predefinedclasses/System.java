package ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.*;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.CharType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.StringType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;

import java.util.Collections;
import java.util.List;

public class System extends PredefinedClass {


    public System() {
        super(new Class(new Token(TokenName.IDENTIFICADOR_DE_CLASE, "System", 0)));
    }

    @Override
    protected List<Method> createMethods() throws EntityAlreadyExistsException {
        Method read = createMethod("read", new IntType());

        Method printB = createMethod("printB", new VoidType());
        createAndAddParameter("b", new BooleanType(), printB);

        Method printC = createMethod("printC", new VoidType());
        createAndAddParameter("c", new CharType(), printC);

        Method printI = createMethod("printI", new VoidType());
        createAndAddParameter("i", new IntType(), printI);

        Method printS = createMethod("printS", new VoidType());
        createAndAddParameter("s", new StringType(), printS);

        Method println = createMethod("println", new VoidType());

        Method printBln = createMethod("printBln", new VoidType());
        createAndAddParameter("b", new BooleanType(), printBln);

        Method printCln = createMethod("printCln", new VoidType());
        createAndAddParameter("c", new CharType(), printCln);

        Method printIln = createMethod("printIln", new VoidType());
        createAndAddParameter("i", new IntType(), printIln);

        Method printSln = createMethod("printSln", new VoidType());
        createAndAddParameter("s", new StringType(), printSln);

        return List.of(read, printB, printC, printI, printS, println, printBln, printCln, printIln, printSln);
    }

    private Method createMethod(String lexeme, Type type){
        return new Method(
                new Token(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE, lexeme, 0),
                type,
                MethodForm.STATIC);
    }

    private void createAndAddParameter(String lexeme, Type type, Method method) throws EntityAlreadyExistsException {
        Parameter parameter = new Parameter(
                new Token(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE, lexeme, 0),
                type
        );

        method.appendParameter(lexeme, parameter);
    }

    @Override
    protected List<Attribute> createAttributes() {
        return Collections.emptyList();
    }

}
