package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.operand.access.AccessNode;
import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;

public class CallSentenceNode extends SentenceNode {

    private Type nodeInvokedType;
    private final AccessNode nodeToInvoke;
    //TODO: pensar si es correcto que esto sea AccessNode en lugar de MethodAccessNode

    public CallSentenceNode(Token sentenceToken, AccessNode nodeToInvoke) {
        super(sentenceToken);
        this.nodeToInvoke = nodeToInvoke;
    }

    @Override
    public void check() throws SemanticException {
        nodeInvokedType = nodeToInvoke.check();
        if(!nodeToInvoke.isLastElementChainedCallable()){
            throw new SemanticException(sentenceToken, "Se espereba una entidad invocable.");
        }
    }

    @Override
    public void generate() throws CodeGeneratorException {
        nodeToInvoke.generate();
        if (nodeInvokedType != null && !nodeInvokedType.equals(new VoidType())) {
            SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.POP));
        }
    }
}
