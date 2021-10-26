package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.syntaxanalyzer.CurrentTokenHandler;
import ar.edu.uns.cs.minijava.syntaxanalyzer.exception.SyntaxException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class NoTerminal<I, S> extends Estado<I, S> {
    protected final List<String> primeros;
    protected final List<Derivacion<S>> derivaciones;

    protected NoTerminal(List<String> primeros, List<Derivacion<S>> derivaciones) {
        this.primeros = primeros;
        this.derivaciones = derivaciones;
    }

    public static <TI, TS> Builder<TI, TS> create(Class<TI> classInherited, Class<TS> classSynthesized){
        return new Builder<>();
    }

    public static Builder<Void, Void> create(){
        return new Builder<>();
    }

    @Override
    public void run(Estado<?, ?> parent, CurrentTokenHandler currentTokenHandler) throws SyntaxException, LexicalException {
        boolean derivacionFound = false;
        Iterator<Derivacion<S>> iteratorDerivacion = derivaciones.iterator();

        while(iteratorDerivacion.hasNext() && !derivacionFound){
            Derivacion<S> derivacion = iteratorDerivacion.next();
            if(derivacion.getPrimeros().contains(currentTokenHandler.getCurrentTokenName())){
                for(Supplier<Estado<?, ?>> lazyEstado : derivacion.getLazyEstados()){
                    lazyEstado.get().run(this, currentTokenHandler);
                    lazyEstado.get().runAction();
                    lazyEstado.get();
                }

                derivacionFound = true;
            }
        }

        if(!derivacionFound){
            runElse(currentTokenHandler);
        }
    }


    protected void runElse(CurrentTokenHandler currentTokenHandler) throws SyntaxException {
        throw new SyntaxException(currentTokenHandler.getCurrentToken(), primeros);
    }

    public static class Builder<TI, TS> {
        protected final List<String> primeros;
        protected final List<Derivacion<TS>> derivaciones;

        protected Builder() {
            primeros = new ArrayList<>();
            derivaciones = new ArrayList<>();
        }

        public Builder<TI, TS> appendDerivacion(Derivacion<TS> derivacion){
            primeros.addAll(derivacion.getPrimeros());
            derivaciones.add(derivacion);
            return this;
        }

        public NoTerminal<TI, TS> build(){
            return new NoTerminal<>(primeros, derivaciones);
        }

    }
}


