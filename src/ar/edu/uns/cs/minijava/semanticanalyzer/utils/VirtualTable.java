package ar.edu.uns.cs.minijava.semanticanalyzer.utils;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Label;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;

public class VirtualTable extends TableWithOffsets<Method>{
    private final Label label;
    private final String suffix;

    public VirtualTable(String suffix) {
        this.suffix = suffix;
        this.label = new Label("VT_" + suffix);
    }

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

    public Label[] assignAndGetLabels(){
        Label[] labels = new Label[table.size()];

        for(int i = 0; i < table.size(); i++){
            table.get(i).createLabelIfDoesNotExist();
            labels[i] = table.get(i).getLabel();
        }

        return labels;
    }

    public Label getLabel() {
        return label;
    }
}
