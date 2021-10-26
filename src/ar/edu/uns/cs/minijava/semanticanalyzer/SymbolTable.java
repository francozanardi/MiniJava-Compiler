package ar.edu.uns.cs.minijava.semanticanalyzer;

import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses.Object;
import ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses.PredefinedClass;
import ar.edu.uns.cs.minijava.semanticanalyzer.predefinedclasses.System;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.EntityTable;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private static final SymbolTable instance = new SymbolTable();
    private final Context context;
    private EntityTable<String, Class> classes;

    private SymbolTable() {
        context = new Context();
        classes = new EntityTable<>();
        addPredefinedClasses();
    }

    private void addPredefinedClasses() {
        PredefinedClass object = new Object();
        classes.put(object.getClassCreated().getIdentifierToken().getLexema(), object.getClassCreated());

        PredefinedClass system = new System();
        classes.put(system.getClassCreated().getIdentifierToken().getLexema(), system.getClassCreated());
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
        for(Class clazz : classes.values()){
            clazz.checkDeclarations();
        }
    }

    public void emptyClasses(){
        classes = new EntityTable<>();
        addPredefinedClasses();
    }
}
