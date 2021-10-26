package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.syntaxanalyzer.CurrentTokenHandler;
import ar.edu.uns.cs.minijava.syntaxanalyzer.exception.SyntaxException;

public abstract class Estado<I, S> {
    protected Action<I, S> action;
    protected I attributeInherited;
    protected S attributeSynthesized;

    public abstract void run(Estado<?, ?> parent, CurrentTokenHandler currentTokenHandler)
            throws SyntaxException, LexicalException;

    public Estado<I, S> setAction(Action<I, S> action){
        this.action = action;

        return this;
    }

    protected S runAction(){
        return action.execute(attributeInherited);
    }

    public Action<I, S> getAction() {
        return action;
    }

    public I getAttributeInherited() {
        return attributeInherited;
    }

    public S getAttributeSynthesized() {
        return attributeSynthesized;
    }
}
