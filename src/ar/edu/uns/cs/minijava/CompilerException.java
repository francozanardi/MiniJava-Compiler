package ar.edu.uns.cs.minijava;

public abstract class CompilerException extends Exception {
    public CompilerException(String msg) {
        super(msg);
    }

    public abstract String getErrorCodeMessage();
}
