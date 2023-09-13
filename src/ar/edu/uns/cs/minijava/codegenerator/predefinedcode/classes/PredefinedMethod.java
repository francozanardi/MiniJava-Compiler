package ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes;

import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNodeImpl;
import ar.edu.uns.cs.minijava.codegenerator.CodeGenerator;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Parameter;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

import java.util.List;

import static ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction.*;

public abstract class PredefinedMethod {
    protected Method method;

    public PredefinedMethod(String methodName, Type methodType, MethodForm methodForm, Class classContainer){
        method = new Method(
                    new Token(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE, methodName, 0),
                    methodType,
                    methodForm);

        method.setClassContainer(classContainer);
        method.setBodyBlock(getBlockWithInstructions());
    }

    protected BlockSentenceNodeImpl getBlockWithInstructions(){
        return new BlockSentenceNodeImpl(method.getIdentifierToken(), method, null) {
            @Override
            public void check() {

            }

            @Override
            public void generate() throws CodeGeneratorException {
                generateInstructions();
            }
        };
    }

    protected abstract void generateInstructions() throws CodeGeneratorException;

    protected void appendParameter(String parameterName, Type parameterType) throws CodeGeneratorException {
        try {
            method.appendParameter(parameterName, new Parameter(
                    new Token(TokenName.IDENTIFICADOR_DE_METODO_O_VARIABLE, parameterName, 0),
                    parameterType
            ));
        } catch (EntityAlreadyExistsException error) {
            throw new CodeGeneratorException(error.getMessage());
        }
    }

    public void generate() throws CodeGeneratorException {
        method.generate();
    }

    public Method getMethod() {
        return method;
    }
}
