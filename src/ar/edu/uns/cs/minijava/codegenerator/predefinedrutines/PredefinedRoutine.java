package ar.edu.uns.cs.minijava.codegenerator.predefinedrutines;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;

import java.util.List;

public abstract class PredefinedRoutine {
    protected final List<Instruction> instructions;
    protected final Label label;

    protected PredefinedRoutine(List<Instruction> instructions, Label label) {
        this.instructions = instructions;
        this.label = label;

        if(instructions.size() > 0){
            instructions.get(0).setLabel(label);
        }
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public Label getLabel() {
        return label;
    }
}
