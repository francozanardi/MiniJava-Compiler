package ar.edu.uns.cs.minijava.semanticanalyzer.utils;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;

import java.util.ArrayList;
import java.util.List;

public class VirtualTable extends TableWithOffsets<Method>{
    @Override
    protected Integer getOffsetFromPositionInTable(int position) {
        return position;
    }

    @Override
    public void appendEntity(Method method) {
        if(method.getMethodForm().equals(MethodForm.DYNAMIC)){
            super.appendEntity(method);
        }
    }

    @Override
    public void setEntityInPosition(Method method, int position) {
        if(method.getMethodForm().equals(MethodForm.DYNAMIC)){
            super.setEntityInPosition(method, position);
        }
    }

    public Label[] getLabels(String prefix){
        Label[] labels = new Label[table.size()];

        for(int i = 0; i < table.size(); i++){
            labels[i] = new Label(prefix + "_" + table.get(i).getIdentifierToken().getLexema());
        }

        return labels;
    }
}
