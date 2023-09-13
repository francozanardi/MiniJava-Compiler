package ar.edu.uns.cs.minijava.codegenerator;

import ar.edu.uns.cs.minijava.CompilerException;

public class CodeGeneratorException extends CompilerException {
    public CodeGeneratorException(String msg) {
        super(msg);
    }

    @Override
    public String getErrorCodeMessage() {
        return null;
    }
}
