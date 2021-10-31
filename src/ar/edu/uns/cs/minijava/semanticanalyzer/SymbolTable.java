package ar.edu.uns.cs.minijava.semanticanalyzer;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses.Object;
import ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses.PredefinedClass;
import ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses.System;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.EntityTable;

import java.util.AbstractMap;
import java.util.Map;

public class SymbolTable {
    private static final SymbolTable instance = new SymbolTable();
    private Context context;
    private EntityTable<String, Class> classes;
    private Map.Entry<Class, Method> mainMethod;

    private SymbolTable() {
        context = new Context();
        classes = new EntityTable<>();
        mainMethod = null;
        addPredefinedClasses();
    }

    private void addPredefinedClasses() {
        PredefinedClass object = new Object();
        classes.put(object.getClassCreated().getIdentifierToken().getLexema(), object.getClassCreated());

        PredefinedClass system = new System();
        classes.put(system.getClassCreated().getIdentifierToken().getLexema(), system.getClassCreated());
        system.getClassCreated().setTokenOfParentClass(object.getClassCreated().getIdentifierToken());
    }

    public static SymbolTable getInstance(){
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public Class getClassById(String classId){
        return classes.get(classId);
    }

    public void addClass(String identifier, Class clazz) throws EntityAlreadyExistsException {
        classes.putAndCheck(identifier, clazz);
    }

    public void checkDeclarations() throws SemanticException {
        if(mainMethod == null){
            throw new SemanticException(new Token(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE, "main", 0),
                    "No se encontró el método main definido en ninguna clase.");
        }

        for(Class clazz : classes.values()){
            clazz.checkDeclarations();
        }
    }

    public void emptySymbolTable(){
        classes = new EntityTable<>();
        addPredefinedClasses();
        context = new Context();
        mainMethod = null;
    }

    public void setMainMethod(Class classContainer, Method method) throws SemanticException {
        if(mainMethod != null){
            throw new SemanticException(method.getIdentifierToken(),
                    "El método main ya se encontraba previamente definido en la clase "
                            + mainMethod.getKey().getIdentifierToken());
        }

        mainMethod = new AbstractMap.SimpleEntry<>(classContainer, method);
    }
}
