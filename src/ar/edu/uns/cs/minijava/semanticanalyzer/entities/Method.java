package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNodeImpl;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
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

    public Method(Token identifierToken, Type returnType, MethodForm methodForm) {
        super(identifierToken, returnType);
        this.methodForm = methodForm;
        this.parameters = new EntityTable<>();
        this.parametersInOrder = new ArrayList<>();
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
}
