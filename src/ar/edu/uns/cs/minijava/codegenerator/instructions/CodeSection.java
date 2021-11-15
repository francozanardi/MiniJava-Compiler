package ar.edu.uns.cs.minijava.codegenerator.instructions;

public enum CodeSection {
    CODE(".CODE"),
    DATA(".DATA"),
    HEAP(".HEAP"),
    STACK(".STACK");

    private final String section;

    CodeSection(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }
}
