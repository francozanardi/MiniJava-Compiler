package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.syntaxanalyzer.CurrentTokenHandler;
import ar.edu.uns.cs.minijava.syntaxanalyzer.exception.SyntaxException;

import java.util.*;

public class NoTerminalConEpsilon extends NoTerminal {
    private final List<String> siguientes;

    private NoTerminalConEpsilon(List<String> primeros, List<Derivacion> derivaciones, List<String> siguientes) {
        super(primeros, derivaciones);
        this.siguientes = siguientes;
    }

    public static Builder create(){
        return new Builder();
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

    public static class Builder extends NoTerminal.Builder {
        private final List<String> siguientes;

        private Builder() {
            super();
            this.siguientes = new ArrayList<>();
        }

        @Override
        public NoTerminalConEpsilon build(){
            return new NoTerminalConEpsilon(primeros, derivaciones, siguientes);
        }

        @Override
        public Builder appendDerivacion(Derivacion derivacion) {
            super.appendDerivacion(derivacion);
            return this;
        }

        public Builder appendSiguientes(String ...siguientes){
            this.siguientes.addAll(List.of(siguientes));
            return this;
        }
    }
}
