package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained.VariableChainedNode;
import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Attribute;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.EntityWithType;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.access.Visibility;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.AttributeForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class VariableAccessNode extends AccessNode {
    private final BlockSentenceNode blockWhereIsUsed;

    public VariableAccessNode(Token sentenceToken, BlockSentenceNode blockWhereIsUsed) {
        super(sentenceToken);
        this.blockWhereIsUsed = blockWhereIsUsed;
    }

    @Override
    public Type check() throws SemanticException {
        EntityWithType entityFound = searchLocalVariableOrParameterOrAttribute();

        if(chained != null){
            return checkCasting(chained.check(entityFound.getType()));
        }

        return checkCasting(entityFound.getType());
    }

    private EntityWithType searchLocalVariableOrParameterOrAttribute() throws SemanticException {
        EntityWithType entityFound;
        if( (entityFound = searchLocalVariable()) != null ||
            (entityFound = searchParameter()) != null ||
            (entityFound = searchAttribute()) != null){

            return entityFound;
        }

        throw new SemanticException(sentenceToken, "El identificador " + sentenceToken.getLexema() +
                " no se encuentra declarado como variable local, ni como parámetro, ni como atributo de instancia");
    }

    private EntityWithType searchLocalVariable(){
        return blockWhereIsUsed.getLocalVariable(sentenceToken.getLexema());
    }

    private EntityWithType searchParameter(){
        return blockWhereIsUsed.getContainerMethod().getParameterById(sentenceToken.getLexema());
    }

    private EntityWithType searchAttribute() throws SemanticException {
        Method currentMethod = blockWhereIsUsed.getContainerMethod();
        Class classContainer = currentMethod.getClassContainer();
        Attribute attributeFound = classContainer.getAttributeById(sentenceToken.getLexema());
        if (attributeFound == null) {
            return null;
        }
        boolean currentMethodIsStatic = currentMethod.getMethodForm().equals(MethodForm.STATIC);
        boolean isInstanceAttribute = attributeFound.getAttributeForm().equals(AttributeForm.DEFAULT);
        if (currentMethodIsStatic && isInstanceAttribute) {
            throw new SemanticException(sentenceToken, "No se puede acceder al atributo de instancia " +
                    sentenceToken.getLexema() + " desde un método estático");
        }
        boolean attributeIsPrivate = attributeFound.getVisibility().equals(Visibility.PRIVATE);
        boolean attributeAndCurrentMethodAreInTheSameClass = classContainer.equals(attributeFound.getClassContainer());
        if (attributeIsPrivate && !attributeAndCurrentMethodAreInTheSameClass) {
            throw new SemanticException(sentenceToken, "El atributo de instancia " +
                    sentenceToken.getLexema() +
                    " es inaccesible desde la clase " +
                    classContainer.getIdentifierToken().getLexema());
        }
        return attributeFound;
    }

    @Override
    public boolean isCallable() {
        return false;
    }

    @Override
    public boolean isAssignable() {
        return true;
    }

    @Override
    public void generate() throws CodeGeneratorException {
        if(!generateIfIAmLocalVariableOrParameter()){
            generateIfIAmAttribute();
        }

        if(chained != null){
            chained.generate();
        }
    }

    private boolean generateIfIAmLocalVariableOrParameter() throws CodeGeneratorException {
        EntityWithType variableOrParameter = searchLocalVariableOrParameter();
        if (variableOrParameter == null) {
            return false;
        }
        OneArgumentInstruction instructionType = isWriteMode ? OneArgumentInstruction.STORE : OneArgumentInstruction.LOAD;
        Instruction instruction = new Instruction(instructionType, variableOrParameter.getOffset());
        SymbolTable.getInstance().appendInstruction(instruction);
        return true;
    }

    private void generateIfIAmAttribute() throws CodeGeneratorException {
        SymbolTable symbolTable = SymbolTable.getInstance();
        Class classContainer = blockWhereIsUsed.getContainerMethod().getClassContainer();
        Attribute attributeFound = classContainer.getAttributeById(sentenceToken.getLexema());
        if (attributeFound == null) {
            return;
        }
        if (attributeFound.getAttributeForm().equals(AttributeForm.STATIC)) {
            Instruction instruction = new Instruction(OneArgumentInstruction.PUSH, classContainer.getStaticAttributesTableLabel());
            symbolTable.appendInstruction(instruction);
        } else {
            symbolTable.appendInstruction(new Instruction(OneArgumentInstruction.LOAD, 3));
        }
        if (isWriteMode) {
            symbolTable.appendInstruction(new Instruction(ZeroArgumentInstruction.SWAP));
            symbolTable.appendInstruction(
                    new Instruction(OneArgumentInstruction.STOREREF, attributeFound.getOffset()));
        } else {
            symbolTable.appendInstruction(
                    new Instruction(OneArgumentInstruction.LOADREF, attributeFound.getOffset()));
        }
    }

    private EntityWithType searchLocalVariableOrParameter(){
        EntityWithType localVariable = searchLocalVariable();
        if(localVariable != null){
            return localVariable;
        }

        return searchParameter();
    }
}
