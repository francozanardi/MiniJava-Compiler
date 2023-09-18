package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.Main;
import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNodeImpl;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.*;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.AttributeForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.ReferenceType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.ClassInstanceRecord;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.EntityTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.StaticAttributesTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.VirtualTable;

import java.util.*;
import java.util.stream.Collectors;

public class Class extends Entity {

    private final EntityTable<String, Method> methods;
    private final EntityTable<String, Attribute> attributes;
    private Token tokenOfParentClass;
    private Constructor constructor;
    private boolean classConsolidated;
    private final VirtualTable virtualTable;
    private final ClassInstanceRecord classInstanceRecord;
    private final StaticAttributesTable staticAttributesTable;

    public Class(Token identifierToken) {
        super(identifierToken);
        methods = new EntityTable<>();
        attributes = new EntityTable<>();
        tokenOfParentClass = null;
        constructor = null;
        classConsolidated = false;
        virtualTable = new VirtualTable(identifierToken.getLexema());
        classInstanceRecord = new ClassInstanceRecord();
        staticAttributesTable = new StaticAttributesTable(this);
    }

    @Override
    public void checkDeclarations() throws SemanticException, CodeGeneratorException {
        tryToCreateDefaultConstructor();
        checkCircularInheritance();
        consolidate();
        virtualTable.assignOffsetToEntities();
        classInstanceRecord.assignOffsetToEntities();
        staticAttributesTable.assignOffsetToEntities();
        addVirtualTableLabels();

        for(Entity method : methods.values()){
            method.checkDeclarations();
        }

        for(Entity attribute : attributes.values()){
            attribute.checkDeclarations();
        }
    }

    public void checkSentences() throws SemanticException {
        constructor.checkSentences();

        for(Method method : methods.values()){
            method.checkSentences();
        }

    }

    private void tryToCreateDefaultConstructor() throws SemanticException {
        if(constructor == null){
            constructor = new Constructor(identifierToken, new ReferenceType(identifierToken.getLexema()));
            constructor.setBodyBlock(new BlockSentenceNodeImpl(identifierToken, constructor, null));
            constructor.setClassContainer(this);
        }
    }

    private void checkCircularInheritance() throws SemanticException {
        if(tokenOfParentClass != null && hasThisAncestor(this)){
            throw new SemanticException(identifierToken, "La clase " +
                    identifierToken.getLexema() +
                    " hereda de sí misma.");
        }
    }

    public boolean hasThisAncestor(Class ancestor) throws SemanticException {
        Class parentClass = getParentClass();

        if(parentClass == null){
            return false;
        }

        if(parentClass == ancestor){
            return true;
        }

        return parentClass.hasThisAncestor(ancestor);
    }

    private Class getParentClass() throws SemanticException {
        if(tokenOfParentClass == null){
            return null;
        }

        Class parent = SymbolTable.getInstance().getClassById(tokenOfParentClass.getLexema());

        if(parent == null){
            throw new SemanticException(tokenOfParentClass, "La clase padre " +
                    tokenOfParentClass.getLexema() +
                    " de " +
                    identifierToken.getLexema() +
                    " no está definida");
        }

        return parent;
    }

    private void consolidate() throws SemanticException {
        Class parent = getParentClass();

        if(parent != null){
            if(!parent.hasBeenConsolidated()){
                parent.consolidate();
            }

            classInstanceRecord.addTableToBegin(parent.classInstanceRecord);

            consolidateMethods(parent);
            consolidateAttributes(parent);
        }

        classConsolidated = true;
    }

    private void addVirtualTableLabels() throws CodeGeneratorException {
        if (Main.UNTIL_STAGE_2 || Main.UNTIL_STAGE_3 || Main.UNTIL_STAGE_4) {
            return;
        }

        Instruction addVtInstruction;

        if(!virtualTable.isEmpty()){
            SymbolTable.getInstance().appendInstruction(new Instruction(CodeSection.DATA));
            DWDirective dw = new DWDirective(virtualTable.assignAndGetLabels());
            addVtInstruction = new Instruction(dw);
        } else {
            addVtInstruction = new Instruction(ZeroArgumentInstruction.NOP);
        }

        addVtInstruction.setLabel(virtualTable.getLabel());
        SymbolTable.getInstance().appendInstruction(addVtInstruction);
    }

    private boolean hasBeenConsolidated(){
        return classConsolidated;
    }

    private void consolidateMethods(Class parent) throws SemanticException {
        VirtualTable newVirtualTable = new VirtualTable(identifierToken.getLexema());

        for(Map.Entry<String, Method> entry : parent.methods.entrySet()){
            try {
                this.methods.putAndCheck(entry.getKey(), entry.getValue());
                newVirtualTable.setEntityInPosition(entry.getValue(),
                        parent.virtualTable.getEntityPosition(entry.getValue()));

            } catch (EntityAlreadyExistsException unused) {
                Method methodRedefined = this.methods.get(entry.getKey());

                if (isMainMethod(methodRedefined) && !Main.UNTIL_STAGE_3) {
                    SymbolTable.getInstance().setMainMethod(this, methodRedefined);
                }

                if(!methodRedefined.haveEqualHeaders(entry.getValue())){
                    throw new EntityAlreadyExistsException(methodRedefined.identifierToken,
                                    "El método " +
                                    methodRedefined.identifierToken.getLexema() +
                                    " ya fue previamente definido en la clase padre " +
                                    parent.identifierToken.getLexema()
                            );
                }

                newVirtualTable.setEntityInPosition(methodRedefined,
                        parent.virtualTable.getEntityPosition(entry.getValue()));
                virtualTable.removeEntity(methodRedefined);
            }
        }

        virtualTable.addTableToBegin(newVirtualTable);
    }

    private void consolidateAttributes(Class parent) throws EntityAlreadyExistsException {
        for (Map.Entry<String, Attribute> entry : parent.attributes.entrySet()) {
            if (entry.getValue().getAttributeForm().equals(AttributeForm.STATIC)) {
                continue;
            }
            try {
                this.attributes.putAndCheck(entry.getKey(), entry.getValue());
            } catch (EntityAlreadyExistsException unused) {
                Attribute attributeRepeatedInChildClass = this.attributes.get(entry.getKey());
                throw new EntityAlreadyExistsException(attributeRepeatedInChildClass.getIdentifierToken(),
                        "El atributo " + attributeRepeatedInChildClass.getIdentifierToken().getLexema() +
                        " ya ha sido previamente definido o heredado en la clase padre " + parent.getIdentifierToken());
            }
        }
    }


    public Method getMethodById(String identifier){
        return methods.get(identifier);
    }

    public void addMethod(String identifier, Method method) throws SemanticException {
        if (isMainMethod(method)) {
            SymbolTable.getInstance().setMainMethod(this, method);
        }

        methods.putAndCheck(identifier, method);
        virtualTable.appendEntity(method);
    }

    private boolean isMainMethod(Method method) {
        return  Objects.equals(method.getIdentifierToken().getLexema(), "main") &&
                Objects.equals(method.getMethodForm(), MethodForm.STATIC) &&
                Objects.equals(method.getType(), new VoidType()) &&
                method.getParameterNumber() == 0;
    }

    public Attribute getAttributeById(String identifier){
        return attributes.get(identifier);
    }

    public void addAttribute(String identifier, Attribute attribute) throws EntityAlreadyExistsException {
        attributes.putAndCheck(identifier, attribute);
        if (attribute.getAttributeForm().equals(AttributeForm.DEFAULT)) {
            classInstanceRecord.appendEntity(attribute);
        } else {
            staticAttributesTable.appendEntity(attribute);
        }
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor newConstructor) throws SemanticException {
        if(this.constructor == null){
            this.constructor = newConstructor;
        } else {
            throw new SemanticException(newConstructor.getIdentifierToken(), "La clase " +
                    identifierToken.getLexema() +
                    " ya tiene un constructor definido en la línea " +
                    this.constructor.getIdentifierToken().getLineNumber());
        }
    }

    public Token getTokenOfParentClass() {
        return tokenOfParentClass;
    }

    public void setTokenOfParentClass(Token tokenOfParentClass) {
        this.tokenOfParentClass = tokenOfParentClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return Objects.equals(this.identifierToken.getLexema(), aClass.identifierToken.getLexema());
    }

    public int getAttributesNumber(){
        return attributes.size();
    }

    public void generate() throws CodeGeneratorException {
        this.generateStaticAttributesCode();
        SymbolTable.getInstance().appendInstruction(new Instruction(CodeSection.CODE));
        for(Method method : methods.values()){
            method.generate();
        }
        constructor.generate();
    }

    private void generateStaticAttributesCode() throws CodeGeneratorException {
        List<Attribute> staticAttributes = this.attributes
                .values()
                .stream()
                .filter(attribute -> attribute.getAttributeForm().equals(AttributeForm.STATIC))
                .collect(Collectors.toList());
        if (staticAttributes.isEmpty()) {
            return;
        }
        SymbolTable symbolTable = SymbolTable.getInstance();
        symbolTable.appendInstruction(new Instruction(CodeSection.DATA));
        DWDirective dwDirective = new DWDirective();
        Instruction instruction = new Instruction(dwDirective);
        instruction.setLabel(this.staticAttributesTable.getLabel());
        for (Attribute ignored : staticAttributes) {
            symbolTable.appendInstruction(instruction);
            instruction.setLabel(null);
        }
    }

    public Label getVirtualTableLabel(){
        return virtualTable.getLabel();
    }

    public Label getStaticAttributesTableLabel(){
        return this.staticAttributesTable.getLabel();
    }
}
