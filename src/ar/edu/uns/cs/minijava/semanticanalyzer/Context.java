package ar.edu.uns.cs.minijava.semanticanalyzer;

import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Method;

public class Context {
    private Class currentClass;
    private Method currentMethod;

    public Class getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(Class currentClass) {
        this.currentClass = currentClass;
        this.currentMethod = null;
    }

    public Method getCurrentMethod() {
        return currentMethod;
    }

    public void setCurrentMethod(Method currentMethod) {
        this.currentMethod = currentMethod;
    }
}
