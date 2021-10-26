package ar.edu.uns.cs.minijava.semanticanalyzer.utils;

import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Entity;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;

import java.util.HashMap;

public class EntityTable<K extends String, V extends Entity> extends HashMap<K, V> {

    public void putAndCheck(K key, V newEntity) throws EntityAlreadyExistsException {
        V entityFound = get(key);

        if(entityFound == null){
            super.put(key, newEntity);
        } else {
            throw new EntityAlreadyExistsException(newEntity.getIdentifierToken(), "La entidad " +
                    newEntity.getIdentifierToken().getLexema() +
                    " ya ha sido previamente declarada en la línea " +
                    entityFound.getIdentifierToken().getLineNumber());
        }

    }
}
