package ar.edu.uns.cs.minijava.semanticanalyzer.entities;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.access.Visibility;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.AttributeForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import org.w3c.dom.Attr;

public class Attribute extends EntityWithType {

    private final AttributeForm attributeForm;
    private Visibility visibility;
    private Class classContainer;

    public Attribute(Token identifierToken, Type type, Visibility visibility, AttributeForm attributeForm) {
        super(identifierToken, type);
        this.visibility = visibility;
        this.attributeForm = attributeForm;
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

    public AttributeForm getAttributeForm() {
        return this.attributeForm;
    }
}
