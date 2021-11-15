package ar.edu.uns.cs.minijava.codegenerator;

import ar.edu.uns.cs.minijava.codegenerator.instructions.Instruction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator {
    private final FileWriter fileWriter;

    public CodeGenerator(String pathname) throws IOException {
        File file = new File(pathname);
        this.fileWriter = new FileWriter(file);
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
}
