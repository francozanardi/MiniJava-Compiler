package ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods.println;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.PredefinedMethod;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;

public class PrintILnMethod extends PredefinedMethod {
    public PrintILnMethod(Class classContainer) throws CodeGeneratorException {
        super("printIln", new VoidType(), MethodForm.STATIC, classContainer);

        appendParameter("i", new IntType());
    }

    @Override
    protected void generateInstructions() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.LOAD, 3));
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.IPRINT));
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.PRNLN));
    }
}
