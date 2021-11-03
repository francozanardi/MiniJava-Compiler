package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;

public class EmptySentenceNode extends SentenceNode {
    public EmptySentenceNode() {
        super(null); //TODO: podría pasar el token del ;
    }

    @Override
    public void check() throws SemanticException {

    }
}
