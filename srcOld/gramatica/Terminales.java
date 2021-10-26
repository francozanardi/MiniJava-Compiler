package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core.Estado;
import ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core.Terminal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Terminales {

    private final Map<String, Terminal> terminalMap;
    private static final Terminales instance = new Terminales();

    private Terminales() {
        terminalMap = new HashMap<>();

        fillTerminalesMap();
    }

    private void fillTerminalesMap() {
        Field[] fields = TokenName.class.getFields();

        try {
            for(Field field : fields){
                if(field.getType() == String.class){
                    int modifiers = field.getModifiers();
                    if(Modifier.isPublic(modifiers) && Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers)){
                        String fieldValue = (String)field.get(null);
                        terminalMap.put(fieldValue, new Terminal(fieldValue));
                    }
                }

            }
        } catch (IllegalAccessException ignored) {

        }
    }

    public static Terminales getInstance(){
        return instance;
    }

    public Supplier<Estado<?, ?>> getTerminal(String tokenName){
        return () -> terminalMap.get(tokenName);
    }
}
