package ar.edu.uns.cs.minijava.semanticanalyzer.utils;

import ar.edu.uns.cs.minijava.semanticanalyzer.entities.EntityWithType;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class TableWithOffsets<E extends EntityWithType> {
    protected List<E> table;

    public TableWithOffsets() {
        table = new ArrayList<>();
    }

    public void addTableToBegin(TableWithOffsets<E> begin){
        List<E> newTable = new ArrayList<>(begin.table);
        newTable.addAll(table);
        table = newTable;
    }

    public void addTableToEnd(TableWithOffsets<E> end){
        table.addAll(end.table);
    }

    public void assignOffsetToEntities(){
        for(int i = 0; i < table.size(); i++){
            table.get(i).setOffset(getOffsetFromPositionInTable(i));
        }
    }

    protected abstract Integer getOffsetFromPositionInTable(int position);

    public void appendEntity(E e){
        table.add(e);
    }

    public void setEntityInPosition(E e, int position){
        table.set(position, e);
    }

    public int getEntityPosition(E e){
        for(int p = 0; p < table.size(); p++){
            if(Objects.equals(e, table.get(p))){
                return p;
            }
        }

        return -1;
    }

    public Iterable<E> getIterable(){
        return table;
    }
}
