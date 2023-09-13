package ar.edu.uns.cs.minijava.semanticanalyzer;

import ar.edu.uns.cs.minijava.codegenerator.CodeGenerator;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.routines.PredefinedRoutine;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.object.Object;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.PredefinedClass;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.System;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.EntityTable;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

public class SymbolTable {
    private static final SymbolTable instance = new SymbolTable();
    private Context context;
    private EntityTable<String, Class> classes;
    private Map.Entry<Class, Method> mainMethod;
    private CodeGenerator codeGenerator;

    private SymbolTable() {
        context = new Context();
        classes = new EntityTable<>();
        mainMethod = null;
    }

    public void initialize(String outputPath) throws IOException {
        codeGenerator = new CodeGenerator(outputPath);

        PredefinedClass object = new Object();
        object.addClassToSymbolTable();

        PredefinedClass system = new System();
        system.addClassToSymbolTable();
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

    public void checkDeclarations() throws SemanticException, CodeGeneratorException {
        if(mainMethod == null){
            throw new SemanticException(new Token(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE, "main", 0),
                    "No se encontró el método main definido en ninguna clase.");
        }

        for(Class clazz : classes.values()){
            clazz.checkDeclarations();
        }
    }

    public void checkSentences() throws SemanticException {
        for(Class clazz : classes.values()){
            clazz.checkSentences();
        }
    }

    public void reloadSymbolTable(String outputPath) throws IOException {
        classes = new EntityTable<>();
        context = new Context();
        mainMethod = null;
        initialize(outputPath);
    }

    public void setMainMethod(Class classContainer, Method method) throws SemanticException {
        if(mainMethod != null){
            throw new SemanticException(method.getIdentifierToken(),
                    "El método main ya se encontraba previamente definido en la clase "
                            + mainMethod.getKey().getIdentifierToken());
        }

        mainMethod = new AbstractMap.SimpleEntry<>(classContainer, method);
    }

    public Method getMainMethod(){
        return mainMethod.getValue();
    }

    public void appendInstruction(Instruction instruction) throws CodeGeneratorException {
        codeGenerator.appendInstruction(instruction);
    }

    public PredefinedRoutine getMalloc(){
        return codeGenerator.getMalloc();
    }

    public void generate() throws CodeGeneratorException, IOException {
        codeGenerator.init();

        for (Class clazz : classes.values()) {
            clazz.generate();
        }

        codeGenerator.close();
    }

    public Label getUniqueLabel() {
        return codeGenerator.getUniqueLabel();
    }
}
