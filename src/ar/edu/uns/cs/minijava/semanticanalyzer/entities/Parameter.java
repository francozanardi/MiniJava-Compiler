package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

import java.util.Objects;

public class Parameter extends EntityWithType {
    private int position;

    public Parameter(Token identifierToken, Type type) {
        super(identifierToken, type);
        position = -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Parameter another = (Parameter) obj;

        return  Objects.equals(this.type, another.type) &&
                this.position == another.position;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
