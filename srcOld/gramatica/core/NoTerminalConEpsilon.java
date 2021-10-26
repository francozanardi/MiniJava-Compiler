package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.syntaxanalyzer.CurrentTokenHandler;
import ar.edu.uns.cs.minijava.syntaxanalyzer.exception.SyntaxException;

import java.util.*;

public class NoTerminalConEpsilon<I, S> extends NoTerminal<I, S> {
    private final List<String> siguientes;

    private NoTerminalConEpsilon(List<String> primeros, List<Derivacion<S>> derivaciones, List<String> siguientes) {
        super(primeros, derivaciones);
        this.siguientes = siguientes;
    }

    public static <TI, TS> Builder<TI, TS> create(Class<TI> classInherited, Class<TS> classSynthesized){
        return new Builder<>();
    }

    public static Builder<Void, Void> create(){
        return new Builder<>();
    }


    @Override
    protected void runElse(CurrentTokenHandler currentTokenHandler) throws SyntaxException {
        Set<String> elementsExpected = new HashSet<>(primeros);
        elementsExpected.addAll(siguientes);

        if(!siguientes.isEmpty() || !currentTokenHandler.getCurrentTokenName().equals(TokenName.EOF)){
            if(!siguientes.contains(currentTokenHandler.getCurrentTokenName())){
                throw new SyntaxException(currentTokenHandler.getCurrentToken(), List.copyOf(elementsExpected));
            }
        }
    }

    public static class Builder<TI, TS> extends NoTerminal.Builder<TI, TS> {
        private final List<String> siguientes;

        private Builder() {
            super();
            this.siguientes = new ArrayList<>();
        }

        @Override
        public NoTerminalConEpsilon<TI, TS> build(){
            return new NoTerminalConEpsilon<>(primeros, derivaciones, siguientes);
        }

        @Override
        public Builder<TI, TS> appendDerivacion(Derivacion<TS> derivacion) {
            super.appendDerivacion(derivacion);
            return this;
        }

        public Builder<TI, TS> appendSiguientes(String ...siguientes){
            this.siguientes.addAll(List.of(siguientes));
            return this;
        }

        public Builder<TI, TS> setEpsilonAction(){

        }
    }
}
