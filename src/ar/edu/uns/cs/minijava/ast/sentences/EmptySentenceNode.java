package ar.edu.uns.cs.minijava.ast.sentences;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;

public class EmptySentenceNode extends SentenceNode {
    public EmptySentenceNode(Token emptySentenceToken) {
        super(emptySentenceToken);
    }

    @Override
    public void check() throws SemanticException {

    }

    @Override
    public void generate() throws CodeGeneratorException {

    }
}
