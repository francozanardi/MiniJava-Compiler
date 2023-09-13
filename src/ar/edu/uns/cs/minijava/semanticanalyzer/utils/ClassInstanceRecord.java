package ar.edu.uns.cs.minijava.semanticanalyzer.utils;

import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Attribute;

public class ClassInstanceRecord extends TableWithOffsets<Attribute>{
    @Override
    protected Integer getOffsetFromPositionInTable(int position) {
        return position+1;
    }
}
