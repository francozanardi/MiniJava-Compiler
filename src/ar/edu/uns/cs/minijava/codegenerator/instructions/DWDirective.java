package ar.edu.uns.cs.minijava.codegenerator.instructions;


import java.util.function.UnaryOperator;

public class DWDirective {
    private static final String DIRECTIVE = "DW";
    private final String argument;

    public DWDirective(Integer[] integers) {
        this.argument = getArgumentsFromList(integers, elem -> elem);
    }

    public DWDirective(Character[] characters) {
        this.argument = getArgumentsFromList(characters, elem -> "'" + elem + "'");
    }
    public DWDirective(Label[] labels) {
        this.argument = getArgumentsFromList(labels, elem -> elem);
    }

    public DWDirective(int cellNumber, int value){
        this.argument = cellNumber + "DUP(" + value + ")";
    }

    public DWDirective(String str){
        this.argument = "\"" + str + "\", 0";
    }

    public DWDirective() {
        this.argument = "0";
    }

    private <E> String getArgumentsFromList(E[] list, UnaryOperator<String> listElementToArg){
        if(list.length == 0){
            return "";
        }

        StringBuilder arguments = new StringBuilder();

        for(int i = 0; i < list.length-1; i++){
            arguments.append(listElementToArg.apply(list[i].toString())).append(", ");
        }

        arguments.append(listElementToArg.apply(list[list.length-1].toString()));

        return arguments.toString();
    }

    public String getDirective(){
        return DIRECTIVE + " " + argument;
    }
}
