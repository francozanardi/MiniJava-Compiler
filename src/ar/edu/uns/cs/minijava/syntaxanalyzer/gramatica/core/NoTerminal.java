package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.syntaxanalyzer.CurrentTokenHandler;
import ar.edu.uns.cs.minijava.syntaxanalyzer.SyntaxException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

// TODO: falta crear NoTerminalConEpsilon

public class NoTerminal implements Estado {
    private final List<String> primeros;
    private final List<Derivacion> derivaciones;

    protected NoTerminal(List<String> primeros, List<Derivacion> derivaciones) {
        this.primeros = primeros;
        this.derivaciones = derivaciones;
    }

    public static Builder create(){
        return new Builder();
    }

    @Override
    public void run(Estado parent, CurrentTokenHandler currentTokenHandler) throws SyntaxException, LexicalException {
        boolean derivacionFound = false;
        Iterator<Derivacion> iteratorDerivacion = derivaciones.iterator();

        while(iteratorDerivacion.hasNext() && !derivacionFound){
            Derivacion derivacion = iteratorDerivacion.next();
            if(derivacion.getPrimeros().contains(currentTokenHandler.getCurrentTokenName())){
                for(Supplier<Estado> lazyEstado : derivacion.getLazyEstados()){
                    lazyEstado.get().run(this, currentTokenHandler);
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

    public static class Builder {
        protected final List<String> primeros;
        protected final List<Derivacion> derivaciones;

        protected Builder() {
            primeros = new ArrayList<>();
            derivaciones = new ArrayList<>();
        }

        public Builder appendDerivacion(Derivacion derivacion){
            primeros.addAll(derivacion.getPrimeros());
            derivaciones.add(derivacion);
            return this;
        }

        public NoTerminal build(){
            return new NoTerminal(primeros, derivaciones);
        }
    }
}


