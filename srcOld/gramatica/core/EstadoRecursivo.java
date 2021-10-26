package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.syntaxanalyzer.CurrentTokenHandler;
import ar.edu.uns.cs.minijava.syntaxanalyzer.exception.SyntaxException;

class EstadoRecursivo<I, S> extends Estado<I, S> {

    EstadoRecursivo(){

    }

    @Override
    public void run(Estado<?, ?> parent, CurrentTokenHandler currentTokenHandler) throws SyntaxException, LexicalException {
        if(parent != null){
            parent.run(parent, currentTokenHandler);
        }
    }
}
