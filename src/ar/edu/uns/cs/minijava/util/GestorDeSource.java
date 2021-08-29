package ar.edu.uns.cs.minijava.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GestorDeSource {
    private FileReader fileReader;
    private boolean isEOF;
    private int lineNumber;

    public GestorDeSource(String pathname) throws FileNotFoundException {
        isEOF = false;
        lineNumber = 0;

        openFile(pathname);
    }

    private void openFile(String pathname) throws FileNotFoundException {
        File file = new File(pathname);
        fileReader = new FileReader(file);
    }

    public Character nextChar() throws IOException {
        int caracterLeido = fileReader.read();

        if(caracterLeido != -1){
            isEOF = true;
            return null;
        }

        if(isNewLine((char)caracterLeido)){
            lineNumber++;
        }

        return (char)caracterLeido;
    }

    //se asume que una nueva línea únicamente está conformada por un salto de línea (LF).
    //es decir, ignoramos los retorno de carro (CR).
    private boolean isNewLine(char c){
        return c == 10;
    }

    public boolean isEndOfFile(){
        return isEOF;
    }

    public int getLineNumber(){
        return lineNumber;
    }
}
