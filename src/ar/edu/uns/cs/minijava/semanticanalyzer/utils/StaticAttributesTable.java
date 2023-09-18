package ar.edu.uns.cs.minijava.semanticanalyzer.utils;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Attribute;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;

public class StaticAttributesTable extends TableWithOffsets<Attribute> {

    private static final String LABEL_PREFIX = "SAC_";
    private final Label label;

    public StaticAttributesTable(Class classContainer) {
        super();
        this.label = new Label(LABEL_PREFIX + classContainer.getIdentifierToken().getLexema());
    }

    @Override
    protected Integer getOffsetFromPositionInTable(int position) {
        return position;
    }

    public Label getLabel() {
        return this.label;
    }
}
