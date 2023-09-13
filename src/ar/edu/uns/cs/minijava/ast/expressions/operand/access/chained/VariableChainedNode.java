package ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained;

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
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.access.Visibility;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class VariableChainedNode extends ChainedNode {
    private final BlockSentenceNode blockWhereIsUsed;
    private Attribute attributeUsed;

    public VariableChainedNode(Token variableToken, BlockSentenceNode blockWhereIsUsed) {
        super(variableToken);
        this.blockWhereIsUsed = blockWhereIsUsed;
    }

    @Override
    public Type check(Type previousType) throws SemanticException {
        Class classAssociatedToType = getClassAssociatedToType(previousType);
        attributeUsed = searchAttribute(classAssociatedToType);

        if(nextChained != null){
            return nextChained.check(attributeUsed.getType());
        }

        return attributeUsed.getType();
    }

    private Attribute searchAttribute(Class classWhereToSearch) throws SemanticException {
        Attribute attributeFound = classWhereToSearch.getAttributeById(sentenceToken.getLexema());

        if(attributeFound != null){
            if(attributeFound.getVisibility().equals(Visibility.PUBLIC)){
                return attributeFound;
            }

            if( attributeFound.getVisibility().equals(Visibility.PRIVATE) &&
                attributeFound.getClassContainer().equals(blockWhereIsUsed.getContainerMethod().getClassContainer())){
                return attributeFound;
            }

            throw new SemanticException(sentenceToken, "El atributo " + sentenceToken.getLexema() +
                    " es inaccesible desde la clase " +
                    blockWhereIsUsed.getContainerMethod().getClassContainer().getIdentifierToken().getLexema());
        }

        throw new SemanticException(sentenceToken, "El atributo " + sentenceToken.getLexema() +
                " no está declarado en la clase " + classWhereToSearch.getIdentifierToken().getLexema());
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
        if(isWriteMode){
            SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.SWAP));
            SymbolTable.getInstance().appendInstruction(
                    new Instruction(OneArgumentInstruction.STOREREF, attributeUsed.getOffset()));
        } else {
            SymbolTable.getInstance().appendInstruction(
                    new Instruction(OneArgumentInstruction.LOADREF, attributeUsed.getOffset()));
        }

        if(nextChained != null){
            nextChained.generate();
        }
    }
}
