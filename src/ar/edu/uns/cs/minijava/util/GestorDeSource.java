package ar.edu.uns.cs.minijava.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GestorDeSource {
    private FileReader fileReader;
    private boolean isEOF;
    private int lineNumber;
    private int currentChar;
    private boolean wasNewLine;
    private int columnNumber;

    public GestorDeSource(String pathname) throws IOException {
        isEOF = false;
        lineNumber = 1;
        currentChar = -1;
        wasNewLine = false;
        columnNumber = 0;

        openFile(pathname);
    }

    private void openFile(String pathname) throws IOException {
        File file = new File(pathname);
        fileReader = new FileReader(file);

        currentChar = fileReader.read();
    }

    public Character nextChar() throws IOException {
        int nextChar = fileReader.read();
        checkIfThereWasNewLine();

        if(currentChar == -1){
            isEOF = true;
            return null;
        }

        columnNumber++;
        char currentCharOld = normalizeNewLineIfExists(currentChar, nextChar);
        updateCurrentChar(nextChar);

        return currentCharOld;
    }

    private Character normalizeNewLineIfExists(int currentChar, int nextChar) {
        if(thereIsNewLine(currentChar, nextChar)){
            wasNewLine = true;
            return '\n';
        }

        return (char)currentChar;
    }

    private boolean thereIsNewLine(int currentCharReaded, int nextChar){
        String currentChar = readerResultToString(currentCharReaded);
        String charsConcatenated = currentChar + readerResultToString(nextChar);

        return  (System.lineSeparator().equals("\n") && currentChar.equals("\n")) ||
                (System.lineSeparator().equals("\r") && currentChar.equals("\r")) ||
                (System.lineSeparator().equals("\r\n") && charsConcatenated.equals("\r\n"));
    }

    private String readerResultToString(int nextChar){
        return nextChar == -1 ? "" : Character.toString(nextChar);
    }

    private void updateCurrentChar(int nextChar) throws IOException {
        if(thereIsNewLine(currentChar, nextChar) && thereIsCharacterToSkip()){
            currentChar = fileReader.read();
        } else {
            currentChar = nextChar;
        }
    }

    private boolean thereIsCharacterToSkip(){
        return System.lineSeparator().equals("\r\n");
    }

    private void checkIfThereWasNewLine(){
        if(wasNewLine){
            columnNumber = 0;
            lineNumber++;
            wasNewLine = false;
        }
    }

    public boolean isEndOfFile(){
        return isEOF;
    }

    public int getLineNumber(){
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
