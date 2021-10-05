package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core;

import ar.edu.uns.cs.minijava.syntaxanalyzer.CurrentTokenHandler;

import java.util.List;

public class NoTerminalConEpsilon extends NoTerminal {

    private NoTerminalConEpsilon(List<String> primeros, List<Derivacion> derivaciones) {
        super(primeros, derivaciones);
    }

    public static Builder create(){
        return new Builder();
    }

    @Override
    protected void runElse(CurrentTokenHandler currentTokenHandler) {

    }

    public static class Builder extends NoTerminal.Builder {
        private Builder() {
            super();
        }

        @Override
        public NoTerminalConEpsilon build(){
            return new NoTerminalConEpsilon(primeros, derivaciones);
        }
    }



}
