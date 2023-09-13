package ar.edu.uns.cs.minijava.codegenerator.predefinedcode.routines;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;

import java.util.List;

import static ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction.*;
import static ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction.*;

public class MallocRoutine extends PredefinedRoutine {
    public MallocRoutine() {
        super(generateInstructions(), generateLabel());
    }

    private static List<Instruction> generateInstructions() {
        return List.of(
                new Instruction(LOADFP),
                new Instruction(LOADSP),
                new Instruction(STOREFP),
                new Instruction(LOADHL),
                new Instruction(DUP),
                new Instruction(PUSH, 1),
                new Instruction(ADD),
                new Instruction(STORE, 4),
                new Instruction(LOAD, 3),
                new Instruction(ADD),
                new Instruction(STOREHL),
                new Instruction(STOREFP),
                new Instruction(RET, 1)
        );
    }

    private static Label generateLabel() {
        return new Label("simple_malloc");
    }
}
