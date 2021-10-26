package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.utils.EntityTable;

import java.util.Objects;

public class Method extends EntityWithType {
    protected final EntityTable<String, Parameter> parameters;
    protected final MethodForm methodForm;
    protected int parameterNumber;

    public Method(Token identifierToken, Type returnType, MethodForm methodForm) {
        super(identifierToken, returnType);
        this.methodForm = methodForm;
        this.parameters = new EntityTable<>();
        this.parameterNumber = 0;
    }

    public Parameter getParameterById(String parameterId) {
        return parameters.get(parameterId);
    }

    public void appendParameter(String identifier, Parameter parameter) throws EntityAlreadyExistsException {
        this.parameters.putAndCheck(identifier, parameter);
        parameter.setPosition(parameterNumber);
        parameterNumber++;
    }

    public MethodForm getMethodForm() {
        return methodForm;
    }

    public boolean haveEqualHeaders(Method another){
        return  Objects.equals(this.methodForm, another.methodForm) &&
                Objects.equals(this.type, another.type) &&
                Objects.equals(this.parameters, another.parameters);
    }

    @Override
    public void checkDeclarations() throws SemanticException {
        super.checkDeclarations();

        for(Parameter parameter : parameters.values()){
            parameter.checkDeclarations();
        }
    }
}
