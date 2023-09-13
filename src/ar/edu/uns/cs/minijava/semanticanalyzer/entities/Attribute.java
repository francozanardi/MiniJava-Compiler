package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.access.Visibility;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;

public class Attribute extends EntityWithType {
    private Visibility visibility;
    private Class classContainer;

    public Attribute(Token identifierToken, Type type, Visibility visibility) {
        super(identifierToken, type);
        this.visibility = visibility;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Class getClassContainer() {
        return classContainer;
    }

    public void setClassContainer(Class classContainer) {
        this.classContainer = classContainer;
    }
}
