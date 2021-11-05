package ar.edu.uns.cs.minijava.ast.expressions.operand.access;


import ar.edu.uns.cs.minijava.ast.sentences.BlockSentenceNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Attribute;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.EntityWithType;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.access.Visibility;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import org.w3c.dom.Attr;

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
        Attribute attributeFound = blockWhereIsUsed.getContainerMethod().getClassContainer().getAttributeById(sentenceToken.getLexema());
        if(attributeFound != null){
            if(blockWhereIsUsed.getContainerMethod().getMethodForm().equals(MethodForm.STATIC)){
                throw new SemanticException(sentenceToken, "No se puede acceder al atributo de instancia " +
                        sentenceToken.getLexema() + " desde un método estático");
            }

            if( attributeFound.getVisibility().equals(Visibility.PRIVATE) &&
                !blockWhereIsUsed.getContainerMethod().getClassContainer().equals(attributeFound.getClassContainer())){
                throw new SemanticException(sentenceToken, "El atributo de instancia " +
                        sentenceToken.getLexema() +
                        " es inaccesible desde la clase " +
                        blockWhereIsUsed.getContainerMethod().getClassContainer().getIdentifierToken().getLexema());
            }

            return attributeFound;
        }

        return null;
    }

    @Override
    public boolean isCallable() {
        return false;
    }

    @Override
    public boolean isAssignable() {
        return true;
    }
}
