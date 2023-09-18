package ar.edu.uns.cs.minijava.ast.expressions.operand.access;

import ar.edu.uns.cs.minijava.codegenerator.CodeGeneratorException;
import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;
import ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.ReferenceType;

public class ClassAccessNode extends AccessNode {

    public ClassAccessNode(Token sentenceToken) {
        super(sentenceToken);
    }

    @Override
    public boolean isAssignable() {
        return false;
    }

    @Override
    public boolean isCallable() {
        return false;
    }

    @Override
    public Type check() throws SemanticException {
        if (this.getChained() == null) {
            throw new SemanticException(this.sentenceToken, "Se esperaba un atributo de clase o un método estático.");
        }
        if (!this.getChained().isStatic(this.getCurrentClass())) {
            throw new SemanticException(this.sentenceToken, "Se esperaba un atributo de clase o un método estático.");
        }
        ReferenceType classType = new ReferenceType(this.sentenceToken.getLexema());
        return this.checkCasting(this.getChained().check(classType));
    }

    private Class getCurrentClass() throws SemanticException {
        Class currentClass = SymbolTable.getInstance().getClassById(this.sentenceToken.getLexema());
        if (currentClass == null) {
            throw new SemanticException(this.sentenceToken, "La clase " + this.sentenceToken.getLexema() + " no fue encontrada definida.");
        }
        return currentClass;
    }

    @Override
    public void generate() throws CodeGeneratorException {
        // Esto es raro y seguramente mejorable, pero es la única rápida solución que se me ocurrió.
        // Representa un dummy value que luego va a ser eliminado con un "pop" en el método estático encadenado o la variable estática encadenada.
        SymbolTable.getInstance().appendInstruction(new Instruction(OneArgumentInstruction.PUSH, 0));
        this.getChained().generate();
    }
}
