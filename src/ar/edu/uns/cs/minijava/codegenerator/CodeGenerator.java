package ar.edu.uns.cs.minijava.codegenerator;

import ar.edu.uns.cs.minijava.codegenerator.instructions.*;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.routines.InitHeapRoutine;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.routines.MallocRoutine;
import ar.edu.uns.cs.minijava.codegenerator.predefinedcode.routines.PredefinedRoutine;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static ar.edu.uns.cs.minijava.codegenerator.instructions.OneArgumentInstruction.PUSH;
import static ar.edu.uns.cs.minijava.codegenerator.instructions.ZeroArgumentInstruction.*;

public class CodeGenerator {
    private final FileWriter fileWriter;
    private final PredefinedRoutine initHeap;
    private final PredefinedRoutine malloc;
    private int uniqueLabelNumber;

    public CodeGenerator(String pathname) throws IOException {
        File file = new File(pathname);
        this.fileWriter = new FileWriter(file);
        this.initHeap = new InitHeapRoutine();
        this.malloc = new MallocRoutine();
        this.uniqueLabelNumber = 0;
    }

    public void appendInstruction(Instruction instruction) throws CodeGeneratorException {
        try {
            fileWriter.write(instruction.getFullInstruction() + System.lineSeparator());
        } catch (IOException e) {
            throw new CodeGeneratorException("Se ha producido una excepción inesperada mientras se " +
                    "escribía el código compilado en el archivo de salida." +
                    "\nMás información acerca del error: " + e.getMessage());
        }
    }

    public void close() throws IOException {
        fileWriter.close();
    }

    public void init() throws CodeGeneratorException {
        appendInstruction(new Instruction(CodeSection.CODE));

        appendInstruction(new Instruction(PUSH, initHeap.getLabel()));
        appendInstruction(new Instruction(CALL));
        appendInstruction(new Instruction(PUSH, SymbolTable.getInstance().getMainMethod().getBeginMethodLabel()));
        appendInstruction(new Instruction(CALL));
        appendInstruction(new Instruction(HALT));

        appendAllInstructions(initHeap.getInstructions());
        appendAllInstructions(malloc.getInstructions());
    }

    public void appendAllInstructions(List<Instruction> instructions) throws CodeGeneratorException {
        for (Instruction instruction : instructions) {
            appendInstruction(instruction);
        }
    }

    public PredefinedRoutine getInitHeap() {
        return initHeap;
    }

    public PredefinedRoutine getMalloc() {
        return malloc;
    }

    public Label getUniqueLabel() {
        return new Label("label_" + uniqueLabelNumber++);
    }
}
