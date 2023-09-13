package ar.edu.uns.cs.minijava.codegenerator.predefinedcode.routines;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;

import java.util.List;

public class InitHeapRoutine extends PredefinedRoutine {
    public InitHeapRoutine() {
        super(generateInstructions(), generateLabel());
    }

    private static List<Instruction> generateInstructions() {
        return List.of(new Instruction(OneArgumentInstruction.RET, 0));
    }

    private static Label generateLabel() {
        return new Label("init_heap");
    }
}
