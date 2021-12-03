package ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods.println;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.PredefinedMethod;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.BooleanType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.CharType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;

public class PrintCLnMethod extends PredefinedMethod {
    public PrintCLnMethod(Class classContainer) throws CodeGeneratorException {
        super("printCln", new VoidType(), MethodForm.STATIC, classContainer);

        appendParameter("c", new CharType());
    }

    @Override
    protected void generateInstructions() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.LOAD, 3));
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.CPRINT));
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.PRNLN));
    }
}
