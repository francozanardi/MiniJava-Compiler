package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNodeImpl;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.*;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.EntityTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Method extends EntityWithType {
    protected final EntityTable<String, Parameter> parameters;
    protected final MethodForm methodForm;
    protected final List<Parameter> parametersInOrder;
    protected BlockSentenceNodeImpl bodyBlock;
    protected Class classContainer;
    protected Label beginMethodLabel;
    protected Label endMethodLabel;
    protected boolean codeWasGenerated;
    private boolean isPredefined;

    public Method(Token identifierToken, Type returnType, MethodForm methodForm) {
        super(identifierToken, returnType);
        this.methodForm = methodForm;
        this.parameters = new EntityTable<>();
        this.parametersInOrder = new ArrayList<>();
        this.beginMethodLabel = null;
        this.endMethodLabel = null;
        this.codeWasGenerated = false;
        this.isPredefined = false;
    }

    public Parameter getParameterById(String parameterId) {
        return parameters.get(parameterId);
    }

    public List<Parameter> getParametersInOrder(){
        return parametersInOrder;
    }

    public void appendParameter(String identifier, Parameter parameter) throws EntityAlreadyExistsException {
        this.parameters.putAndCheck(identifier, parameter);
        parameter.setPosition(parametersInOrder.size());

        if(methodForm.equals(MethodForm.STATIC)){
            parameter.setOffset(parametersInOrder.size() + 3);
        } else {
            parameter.setOffset(parametersInOrder.size() + 4);
        }

        parametersInOrder.add(parameter);
    }

    public MethodForm getMethodForm() {
        return methodForm;
    }

    public boolean haveEqualHeaders(Method another){
        return  Objects.equals(this.methodForm, another.methodForm) &&
                Objects.equals(this.type, another.type) &&
                Objects.equals(this.parametersInOrder, another.parametersInOrder);
    }

    @Override
    public void checkDeclarations() throws SemanticException {
        super.checkDeclarations();
        for(Parameter parameter : parameters.values()){
            parameter.checkDeclarations();
        }
    }

    public void checkSentences() throws SemanticException {
        bodyBlock.check();
        boolean shouldCheckIfMethodContainsReturnSentence = !this.methodForm.equals(MethodForm.CONSTRUCTOR) && !this.type.equals(new VoidType()) && !this.isPredefined;
        if (shouldCheckIfMethodContainsReturnSentence && !bodyBlock.containsReturnSentence()) {
            throw new SemanticException(
                    this.getIdentifierToken(),
                    "El método " + this.getIdentifierToken().getLexema() + " tiene al menos un posible camino de ejecución donde no tiene una sentencia de retorno."
            );
        }
    }

    public int getParameterNumber(){
        return parametersInOrder.size();
    }

    public BlockSentenceNodeImpl getBodyBlock() {
        return bodyBlock;
    }

    public void setBodyBlock(BlockSentenceNodeImpl bodyBlock) {
        this.bodyBlock = bodyBlock;
    }

    public Class getClassContainer() {
        return classContainer;
    }

    public void setClassContainer(Class classContainer) {
        this.classContainer = classContainer;
    }

    public boolean canHasReturn(){
        return true;
    }

    public void generate() throws CodeGeneratorException {
        if(!codeWasGenerated){
            createLabelIfDoesNotExist();

            initMethodRA();
            bodyBlock.generate();
            finishMethodRA();
            codeWasGenerated = true;
        }
    }

    private void initMethodRA() throws CodeGeneratorException {
        Instruction firstInstruction = new Instruction(ZeroArgumentInstruction.LOADFP);
        firstInstruction.setLabel(beginMethodLabel);
        SymbolTable.getInstance().appendInstruction(firstInstruction);

        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.LOADSP));
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.STOREFP));
    }

    private void finishMethodRA() throws CodeGeneratorException {
        int spacesToFree = parameters.size();

        if(!methodForm.equals(MethodForm.STATIC)){
            spacesToFree++;
        }

        Instruction beginToEndMethodInstruction = new Instruction(ZeroArgumentInstruction.STOREFP);
        beginToEndMethodInstruction.setLabel(endMethodLabel);
        SymbolTable.getInstance().appendInstruction(beginToEndMethodInstruction);
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.RET, spacesToFree));
    }

    public void createLabelIfDoesNotExist() {
        if(beginMethodLabel == null){
            beginMethodLabel = new Label(identifierToken.getLexema() +
                    "_" + classContainer.getIdentifierToken().getLexema());
        }

        if(endMethodLabel == null){
            endMethodLabel = new Label(beginMethodLabel.getName() + "_end");
        }
    }

    public Label getBeginMethodLabel() {
        createLabelIfDoesNotExist();
        return beginMethodLabel;
    }

    public Label getEndMethodLabel() {
        createLabelIfDoesNotExist();
        return endMethodLabel;
    }

    public void markAsPredefined() {
        this.isPredefined = true;
    }

    public boolean isPredefined() {
        return this.isPredefined;
    }
}