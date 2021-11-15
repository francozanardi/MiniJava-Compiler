package ar.edu.uns.cs.minijava.codegenerator.instructions;

public enum OneArgumentInstruction {
    PUSH("PUSH"),
    BF("BF"),
    BT("BT"),
    JUMP("JUMP"),
    LOAD("LOAD"),
    STORE("STORE"),
    LOADREF("LOADREF"),
    STOREREF("STOREREF"),
    RMEM("RMEM"),
    FMEM("FMEM"),
    RET("RET");


    private final String instruction;

    OneArgumentInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}
