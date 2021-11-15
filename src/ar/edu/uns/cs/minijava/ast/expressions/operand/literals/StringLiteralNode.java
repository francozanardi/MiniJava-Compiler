package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.DWDirective;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.StringType;

public class StringLiteralNode extends LiteralNode {
    public StringLiteralNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    public Type check() throws SemanticException {
        return new StringType();
    }

    @Override
    protected Instruction generateInstruction() {
        return new Instruction(new DWDirective(getStringWithoutQuotes()));
    }

    public String getStringWithoutQuotes(){
        String quoted = sentenceToken.getLexema();

        if(quoted.startsWith("\"\"\"")){ //is block string
            return quoted.substring(3, quoted.length()-3);
        }

        return quoted.substring(1, quoted.length()-1);
    }
}
