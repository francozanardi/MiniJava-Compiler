package ar.edu.uns.cs.minijava.ast.expressions.operand.literals;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.*;
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
    public void generate() throws CodeGeneratorException {
        DWDirective dwDirective = new DWDirective(getStringWithoutQuotes());
        Instruction instruction = new Instruction(dwDirective);
        Label label = SymbolTable.getInstance().getUniqueLabel();
        instruction.setLabel(label);
        SymbolTable.getInstance().appendInstruction(new Instruction(CodeSection.DATA));
        SymbolTable.getInstance().appendInstruction(instruction);
        SymbolTable.getInstance().appendInstruction(new Instruction(CodeSection.CODE));
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.PUSH, label));
    }

    @Override
    protected Instruction generateInstruction() {
        return null;
    }

    public String getStringWithoutQuotes(){
        String quoted = sentenceToken.getLexema();

        if(quoted.startsWith("\"\"\"")){ //is block string
            return quoted.substring(3, quoted.length()-3);
        }

        return quoted.substring(1, quoted.length()-1);
    }
}
