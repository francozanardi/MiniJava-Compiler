package ar.edu.uns.cs.minijava.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GestorDeSource {
    private FileReader fileReader;
    private boolean isEOF;
    private int lineNumber;
    private ArrayList<String> allLines;
    private int columnNumber;

    public GestorDeSource(String pathname) throws IOException {
        isEOF = false;
        lineNumber = 1;
        columnNumber = 0;
        allLines = new ArrayList<>();

        openFile(pathname);
    }

    private void openFile(String pathname) throws IOException {
        File file = new File(pathname);
        fileReader = new FileReader(file);

        readAllFile();

        fileReader.close();
    }

    private void readAllFile() throws IOException {
        StringBuilder currentLine = new StringBuilder();
        int currentChar = fileReader.read();

        while(currentChar != -1) {
            currentChar = readCharHandlingNewLine((char)currentChar, currentLine);
        }

        allLines.add(currentLine.toString());
    }

    private int readCharHandlingNewLine(Character currentChar, StringBuilder currentLine) throws IOException {
        if(currentChar.equals('\n')){
            addNewLine(currentLine);
            return fileReader.read();
        } else if(currentChar.equals('\r')){
            int nextChar = fileReader.read();
            if(nextChar != -1 && Character.toString(nextChar).equals("\n")){
                addNewLine(currentLine);
                return fileReader.read();
            } else {
                addNewLine(currentLine);
                return nextChar;
            }
        } else {
            currentLine.append(currentChar);
        }

        return fileReader.read();
    }

    private void addNewLine(StringBuilder currentLine){
        currentLine.append(getNewLineUniformRepresentation());
        allLines.add(currentLine.toString());
        currentLine.setLength(0);
    }

    public Character nextChar() {
        Character nextChar;
        String currentLine = allLines.get(lineNumber-1);

        if(columnNumber < currentLine.length()){
            nextChar = currentLine.charAt(columnNumber);
            columnNumber++;
        } else if(lineNumber < allLines.size()){
            lineNumber++;
            columnNumber = 0;
            nextChar = nextChar();
        } else {
            nextChar = null;
            isEOF = true;
        }

        return nextChar;
    }

    private Character getNewLineUniformRepresentation(){
        return '\n';
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

    public String getCurrentLine(){
        return allLines.get(lineNumber-1).replace("\n", "");
    }
}
