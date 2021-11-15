package ar.edu.uns.cs.minijava.codegenerator.instructions;

public class Instruction {
    private final String fullInstruction;
    private Label label;

    public Instruction(OneArgumentInstruction oneArgumentInstruction, int value) {
        this.fullInstruction = oneArgumentInstruction.getInstruction() + " " + value;
    }

    public Instruction(OneArgumentInstruction oneArgumentInstruction, Label label) {
        this.fullInstruction = oneArgumentInstruction.getInstruction() + " " + label.getName();
    }

    public Instruction(ZeroArgumentInstruction zeroArgumentInstruction) {
        this.fullInstruction = zeroArgumentInstruction.getInstruction();
    }

    public Instruction(CodeSection section){
        this.fullInstruction = section.getSection();
    }

    public Instruction(DWDirective dwDirective){
        this.fullInstruction = dwDirective.getDirective();
    }

    public Instruction(String fullInstruction) {
        this.fullInstruction = fullInstruction;
    }

    public String getFullInstruction() {
        return fullInstruction;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
