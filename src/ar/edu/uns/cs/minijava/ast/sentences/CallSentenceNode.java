package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.ast.expressions.operand.access.AccessNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;

public class CallSentenceNode extends SentenceNode {
    private AccessNode nodeToInvoke;
    //TODO: pensar si es correcto que esto sea AccessNode en lugar de MethodAccessNode

    public CallSentenceNode(Token sentenceToken, AccessNode nodeToInvoke) {
        super(sentenceToken);
        this.nodeToInvoke = nodeToInvoke;
    }

    @Override
    public void check() throws SemanticException {
        nodeToInvoke.check();

        if(!nodeToInvoke.isLastElementChainedCallable()){
            throw new SemanticException(sentenceToken, "Se espereba una entidad invocable.");
        }
    }
}
