package ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods.println;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.PredefinedMethod;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;

public class PrintLnMethod extends PredefinedMethod {
    public PrintLnMethod(Class classContainer) throws CodeGeneratorException {
        super("println", new VoidType(), MethodForm.STATIC, classContainer);
    }

    @Override
    protected void generateInstructions() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.PRNLN));
    }
}
