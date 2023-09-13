package ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.system.methods;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.classes.PredefinedMethod;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.IntType;

public class ReadMethod extends PredefinedMethod {
    public ReadMethod(Class classContainer) {
        super("read", new IntType(), MethodForm.STATIC, classContainer);
    }

    @Override
    protected void generateInstructions() throws CodeGeneratorException {
        SymbolTable.getInstance().appendInstruction(new Instruction(ZeroArgumentInstruction.READ));
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.STORE, 3));
    }
}
