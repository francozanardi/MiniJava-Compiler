package ar.edu.uns.cs.minijava.codegenerator.instructions;

public enum ZeroArgumentInstruction {
    ADD("ADD"),
    SUB("SUB"),
    MUL("MUL"),
    DIV("DIV"),
    MOD("MOD"),
    NEG("NEG"),
    AND("AND"),
    OR("OR"),
    NOT("NOT"),
    EQ("EQ"),
    NE("NE"),
    LT("LT"),
    GT("GT"),
    LE("LE"),
    GE("GE"),
    DUP("DUP"),
    POP("POP"),
    SWAP("SWAP"),
    NOP("NOP"),
    READ("READ"),
    BPRINT("BPRINT"),
    CPRINT("CPRINT"),
    IPRINT("IPRINT"),
    SPRINT("SPRINT"),
    PRNLN("PRNLN"),
    DEREF("DEREF"),
    CALL("CALL"),
    HALT("HALT"),
    LOADPC("LOADPC"),
    STOREPC("STOREPC"),
    LOADFP("LOADFP"),
    STORESP("STORESP"),
    LOADSP("LOADSP"),
    STOREFP("STOREFP"),
    LOADHP("LOADHP"),
    STOREHP("STOREHP"),
    LOADHL("LOADHL"),
    STOREHL("STOREHL");

    private final String instruction;

    ZeroArgumentInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}
