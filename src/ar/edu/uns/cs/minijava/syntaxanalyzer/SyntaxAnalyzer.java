package ar.edu.uns.cs.minijava.syntaxanalyzer;

import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalAnalyzer;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.*;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.access.Visibility;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.MethodForm;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.primitive.*;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.ReferenceType;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.Type;
import ar.edu.uns.cs.minijava.semanticanalyzer.types.reference.VoidType;
import ar.edu.uns.cs.minijava.syntaxanalyzer.exception.SyntaxException;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName.*;

public class SyntaxAnalyzer {
    private final LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;

    public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    public void initGrammar() throws LexicalException, SyntaxException, SemanticException {
        currentToken = lexicalAnalyzer.nextToken();
        inicial();
    }

    private void match(TokenName tokenName) throws LexicalException, SyntaxException{
        if(tokenName.equals(currentToken.getTokenName())){
            currentToken = lexicalAnalyzer.nextToken();
        } else {
            throw new SyntaxException(currentToken, tokenName);
        }
    }

    private void inicial() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(CLASS_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            listaClases();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaClases() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(CLASS_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            clase();
            listaClasesAux();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaClasesAux() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(CLASS_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            clase();
            listaClasesAux();
        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void clase() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(CLASS_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(CLASS_PR);

            Token classToken = currentToken;

            match(IDENTIFICADOR_DE_CLASE);

            Class newClass = new Class(classToken);
            SymbolTable.getInstance().getContext().setCurrentClass(newClass);

            Token parentClassToken = herencia();
            newClass.setTokenOfParentClass(parentClassToken);

            match(LLAVE_ABRE);
            listaMiembros();
            match(LLAVE_CIERRA);

            SymbolTable.getInstance().addClass(classToken.getLexema(), newClass);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private Token herencia() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(EXTENDS_PR);
        List<TokenName> siguientes = List.of(LLAVE_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(EXTENDS_PR);
            Token parentClassToken = currentToken;
            match(IDENTIFICADOR_DE_CLASE);

            return parentClassToken;
        } else if(siguientes.contains(currentToken.getTokenName())){
            return SymbolTable.getInstance().getClassById("Object").getIdentifierToken();
        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }

        return SymbolTable.getInstance().getClassById("Object").getIdentifierToken();
    }

    private void listaMiembros() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(PUBLIC_PR, PRIVATE_PR, IDENTIFICADOR_DE_CLASE, STATIC_PR, DYNAMIC_PR);
        List<TokenName> siguientes = List.of(LLAVE_CIERRA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            miembro();
            listaMiembros();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void miembro() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(PUBLIC_PR, PRIVATE_PR);
        List<TokenName> primerosDerivacion2 = List.of(IDENTIFICADOR_DE_CLASE);
        List<TokenName> primerosDerivacion3 = List.of(STATIC_PR, DYNAMIC_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            atributo();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            constructor();
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            metodo();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void atributo() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(PUBLIC_PR, PRIVATE_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Visibility visibility = visibilidad();
            Type type = tipo();
            List<Token> identifiers = listaDecAtrs();
            match(PUNTO_Y_COMA);

            Class currentClass = SymbolTable.getInstance().getContext().getCurrentClass();
            Attribute attribute;

            for(Token token : identifiers){
                attribute = new Attribute(token, type, visibility);
                currentClass.addAttribute(token.getLexema(), attribute);
            }
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void constructor() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token constructorIdentifier = currentToken;
            match(IDENTIFICADOR_DE_CLASE);
            List<Parameter> parameters = argsFormales();
            bloque();

            Class currentClass = SymbolTable.getInstance().getContext().getCurrentClass();
            Type constructorType = new ReferenceType(currentClass.getIdentifierToken().getLexema());
            Constructor constructor = new Constructor(constructorIdentifier, constructorType);

            for(Parameter parameter : parameters){
                constructor.appendParameter(parameter.getIdentifierToken().getLexema(), parameter);
            }

            currentClass.setConstructor(constructor);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void metodo() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(STATIC_PR, DYNAMIC_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            MethodForm methodForm = formaMetodo();
            Type type = tipoMetodo();
            Token methodIdentifier = currentToken;
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            List<Parameter> parameters = argsFormales();
            bloque();

            Class currentClass = SymbolTable.getInstance().getContext().getCurrentClass();
            Method method = new Method(methodIdentifier, type, methodForm);

            for(Parameter parameter : parameters){
                method.appendParameter(parameter.getIdentifierToken().getLexema(), parameter);
            }

            currentClass.addMethod(methodIdentifier.getLexema(), method);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private Visibility visibilidad() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PUBLIC_PR);
        List<TokenName> primerosDerivacion2 = List.of(PRIVATE_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PUBLIC_PR);
            return Visibility.PUBLIC;
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            match(PRIVATE_PR);
            return Visibility.PRIVATE;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private Type tipo() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR);
        List<TokenName> primerosDerivacion2 = List.of(IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            return tipoPrimitivo();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            Token classIndentifier = currentToken;
            match(IDENTIFICADOR_DE_CLASE);
            return new ReferenceType(classIndentifier.getLexema());
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private Type tipoPrimitivo() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(BOOLEAN_PR);
        List<TokenName> primerosDerivacion2 = List.of(CHAR_PR);
        List<TokenName> primerosDerivacion3 = List.of(INT_PR);
        List<TokenName> primerosDerivacion4 = List.of(STRING_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);
        tokensExpected.addAll(primerosDerivacion4);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(BOOLEAN_PR);
            return new BooleanType();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            match(CHAR_PR);
            return new CharType();
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            match(INT_PR);
            return new IntType();
        } else if(primerosDerivacion4.contains(currentToken.getTokenName())){
            match(STRING_PR);
            return new StringType();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private List<Token> listaDecAtrs() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        List<Token> identifiers = new ArrayList<>();

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token firstIdentifier = currentToken;
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            identifiers.add(firstIdentifier);

            listaDecAtrsAux(identifiers);

            return identifiers;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaDecAtrsAux(List<Token> identifiers) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(COMA);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(COMA);

            Token identifier = currentToken;
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            identifiers.add(identifier);

            listaDecAtrsAux(identifiers);
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private MethodForm formaMetodo() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(STATIC_PR);
        List<TokenName> primerosDerivacion2 = List.of(DYNAMIC_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(STATIC_PR);
            return MethodForm.STATIC;
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            match(DYNAMIC_PR);
            return MethodForm.DYNAMIC;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private Type tipoMetodo() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE);
        List<TokenName> primerosDerivacion2 = List.of(VOID_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            return tipo();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            match(VOID_PR);
            return new VoidType();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private List<Parameter> argsFormales() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        List<Parameter> parameters = new ArrayList<>();

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PARENTESIS_ABRE);
            listaArgsFormalesOVacio(parameters);
            match(PARENTESIS_CIERRA);

            return parameters;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaArgsFormalesOVacio(List<Parameter> parameters) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE);
        List<TokenName> siguientes = List.of(PARENTESIS_CIERRA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            listaArgsFormales(parameters);
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaArgsFormales(List<Parameter> parameters) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Parameter parameter = argFormal();
            parameters.add(parameter);
            listaArgsFormalesAux(parameters);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaArgsFormalesAux(List<Parameter> parameters) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(COMA);
        List<TokenName> siguientes = List.of(PARENTESIS_CIERRA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(COMA);
            Parameter parameter = argFormal();
            parameters.add(parameter);
            listaArgsFormalesAux(parameters);
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private Parameter argFormal() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Type type = tipo();
            Token parameterIndentifier = currentToken;
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);

            return new Parameter(parameterIndentifier, type);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void bloque() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(LLAVE_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(LLAVE_ABRE);
            listaSentencias();
            match(LLAVE_CIERRA);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaSentencias() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PUNTO_Y_COMA, BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE, RETURN_PR, IF_PR, FOR_PR, LLAVE_ABRE, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);
        List<TokenName> siguientes = List.of(LLAVE_CIERRA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            sentencia();
            listaSentencias();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void sentencia() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PUNTO_Y_COMA);
        List<TokenName> primerosDerivacion2 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE);
        List<TokenName> primerosDerivacion3 = List.of(RETURN_PR);
        List<TokenName> primerosDerivacion4 = List.of(IF_PR);
        List<TokenName> primerosDerivacion5 = List.of(FOR_PR);
        List<TokenName> primerosDerivacion6 = List.of(LLAVE_ABRE);
        List<TokenName> primerosDerivacion7 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);
        tokensExpected.addAll(primerosDerivacion4);
        tokensExpected.addAll(primerosDerivacion5);
        tokensExpected.addAll(primerosDerivacion6);
        tokensExpected.addAll(primerosDerivacion7);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PUNTO_Y_COMA);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            varLocal();
            match(PUNTO_Y_COMA);
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            return_();
            match(PUNTO_Y_COMA);
        } else if(primerosDerivacion4.contains(currentToken.getTokenName())){
            if_();
        } else if(primerosDerivacion5.contains(currentToken.getTokenName())){
            for_();
        } else if(primerosDerivacion6.contains(currentToken.getTokenName())){
            bloque();
        } else if(primerosDerivacion7.contains(currentToken.getTokenName())){
            asignacionOLlamada();
            match(PUNTO_Y_COMA);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void asignacionOLlamada() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            acceso();
            asignacionOLlamadaAux();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void asignacionOLlamadaAux() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(ASIGNACION, INCREMENTOR, DECREMENTOR);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            tipoDeAsignacion();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void asignacion() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            acceso();
            tipoDeAsignacion();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void tipoDeAsignacion() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(ASIGNACION);
        List<TokenName> primerosDerivacion2 = List.of(INCREMENTOR);
        List<TokenName> primerosDerivacion3 = List.of(DECREMENTOR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(ASIGNACION);
            expresion();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            match(INCREMENTOR);
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            match(DECREMENTOR);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void varLocal() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            tipo();
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            varLocalAux();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void varLocalAux() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(ASIGNACION);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(ASIGNACION);
            expresion();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void return_() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(RETURN_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(RETURN_PR);
            expresionOVacio();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void expresionOVacio() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            expresion();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void if_() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(IF_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(IF_PR);
            match(PARENTESIS_ABRE);
            expresion();
            match(PARENTESIS_CIERRA);
            sentencia();
            else_();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void else_() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(ELSE_PR);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA, BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE, RETURN_PR, IF_PR, FOR_PR, LLAVE_ABRE, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, LLAVE_CIERRA, ELSE_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(ELSE_PR);
            sentencia();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void for_() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(FOR_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(FOR_PR);
            match(PARENTESIS_ABRE);
            varLocal();
            match(PUNTO_Y_COMA);
            expresion();
            match(PUNTO_Y_COMA);
            asignacion();
            match(PARENTESIS_CIERRA);
            sentencia();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void expresion() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            expresionUnaria();
            expresionBinaria();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void expresionBinaria() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA, PARENTESIS_CIERRA, COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            operadorBinario();
            expresionUnaria();
            expresionBinaria();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void operadorBinario() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(OR);
        List<TokenName> primerosDerivacion2 = List.of(AND);
        List<TokenName> primerosDerivacion3 = List.of(COMPARACION);
        List<TokenName> primerosDerivacion4 = List.of(DISTINTO);
        List<TokenName> primerosDerivacion5 = List.of(MENOR);
        List<TokenName> primerosDerivacion6 = List.of(MAYOR);
        List<TokenName> primerosDerivacion7 = List.of(MENOR_IGUAL);
        List<TokenName> primerosDerivacion8 = List.of(MAYOR_IGUAL);
        List<TokenName> primerosDerivacion9 = List.of(SUMA);
        List<TokenName> primerosDerivacion10 = List.of(RESTA);
        List<TokenName> primerosDerivacion11 = List.of(PRODUCTO);
        List<TokenName> primerosDerivacion12 = List.of(DIVISION);
        List<TokenName> primerosDerivacion13 = List.of(MODULO);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);
        tokensExpected.addAll(primerosDerivacion4);
        tokensExpected.addAll(primerosDerivacion5);
        tokensExpected.addAll(primerosDerivacion6);
        tokensExpected.addAll(primerosDerivacion7);
        tokensExpected.addAll(primerosDerivacion8);
        tokensExpected.addAll(primerosDerivacion9);
        tokensExpected.addAll(primerosDerivacion10);
        tokensExpected.addAll(primerosDerivacion11);
        tokensExpected.addAll(primerosDerivacion12);
        tokensExpected.addAll(primerosDerivacion13);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(OR);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            match(AND);
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            match(COMPARACION);
        } else if(primerosDerivacion4.contains(currentToken.getTokenName())){
            match(DISTINTO);
        } else if(primerosDerivacion5.contains(currentToken.getTokenName())){
            match(MENOR);
        } else if(primerosDerivacion6.contains(currentToken.getTokenName())){
            match(MAYOR);
        } else if(primerosDerivacion7.contains(currentToken.getTokenName())){
            match(MENOR_IGUAL);
        } else if(primerosDerivacion8.contains(currentToken.getTokenName())){
            match(MAYOR_IGUAL);
        } else if(primerosDerivacion9.contains(currentToken.getTokenName())){
            match(SUMA);
        } else if(primerosDerivacion10.contains(currentToken.getTokenName())){
            match(RESTA);
        } else if(primerosDerivacion11.contains(currentToken.getTokenName())){
            match(PRODUCTO);
        } else if(primerosDerivacion12.contains(currentToken.getTokenName())){
            match(DIVISION);
        } else if(primerosDerivacion13.contains(currentToken.getTokenName())){
            match(MODULO);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void expresionUnaria() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION);
        List<TokenName> primerosDerivacion2 = List.of(NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            operadorUnario();
            operando();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            operando();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void operadorUnario() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA);
        List<TokenName> primerosDerivacion2 = List.of(RESTA);
        List<TokenName> primerosDerivacion3 = List.of(NEGACION);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(SUMA);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            match(RESTA);
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            match(NEGACION);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void operando() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING);
        List<TokenName> primerosDerivacion2 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            literal();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            acceso();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void literal() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(NULL_PR);
        List<TokenName> primerosDerivacion2 = List.of(TRUE_PR);
        List<TokenName> primerosDerivacion3 = List.of(FALSE_PR);
        List<TokenName> primerosDerivacion4 = List.of(ENTERO);
        List<TokenName> primerosDerivacion5 = List.of(CARACTER);
        List<TokenName> primerosDerivacion6 = List.of(STRING);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);
        tokensExpected.addAll(primerosDerivacion4);
        tokensExpected.addAll(primerosDerivacion5);
        tokensExpected.addAll(primerosDerivacion6);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(NULL_PR);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            match(TRUE_PR);
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            match(FALSE_PR);
        } else if(primerosDerivacion4.contains(currentToken.getTokenName())){
            match(ENTERO);
        } else if(primerosDerivacion5.contains(currentToken.getTokenName())){
            match(CARACTER);
        } else if(primerosDerivacion6.contains(currentToken.getTokenName())){
            match(STRING);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void acceso() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE);
        List<TokenName> primerosDerivacion2 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            primarioSinExpresionParentizada();
            encadenado();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            castingOExpresionParentizada();
            encadenado();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void castingOExpresionParentizada() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PARENTESIS_ABRE);
            castingOExpresionParentizadaAux();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void castingOExpresionParentizadaAux() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_CLASE);
        List<TokenName> primerosDerivacion2 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(IDENTIFICADOR_DE_CLASE);
            match(PARENTESIS_CIERRA);
            primarioConExpresionParentizada();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            expresion();
            match(PARENTESIS_CIERRA);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void primarioConExpresionParentizada() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);
        List<TokenName> primerosDerivacion2 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            expresionParentizada();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            primarioSinExpresionParentizada();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void primarioSinExpresionParentizada() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR);
        List<TokenName> primerosDerivacion2 = List.of(NEW_PR);
        List<TokenName> primerosDerivacion3 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            accesoThis();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            accesoConstructor();
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            accesoVarOMetodo();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void expresionParentizada() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PARENTESIS_ABRE);
            expresion();
            match(PARENTESIS_CIERRA);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void accesoVarOMetodo() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            accesoVarOMetodoAux();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void accesoVarOMetodoAux() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);
        List<TokenName> siguientes = List.of(PUNTO, ASIGNACION, INCREMENTOR, DECREMENTOR, PUNTO_Y_COMA, OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO, PARENTESIS_CIERRA, COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            argsActuales();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void accesoThis() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(THIS_PR);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void accesoConstructor() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(NEW_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(NEW_PR);
            match(IDENTIFICADOR_DE_CLASE);
            argsActuales();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void argsActuales() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PARENTESIS_ABRE);
            listaExpsOVacio();
            match(PARENTESIS_CIERRA);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaExpsOVacio() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);
        List<TokenName> siguientes = List.of(PARENTESIS_CIERRA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            listaExps();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaExps() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            expresion();
            listaExpsAux();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaExpsAux() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(COMA);
        List<TokenName> siguientes = List.of(PARENTESIS_CIERRA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(COMA);
            expresion();
            listaExpsAux();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void encadenado() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PUNTO);
        List<TokenName> siguientes = List.of(ASIGNACION, INCREMENTOR, DECREMENTOR, PUNTO_Y_COMA, OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO, PARENTESIS_CIERRA, COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            varOMetodoEncadenado();
            encadenado();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void varOMetodoEncadenado() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PUNTO);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PUNTO);
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            varOMetodoEncadenadoAux();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void varOMetodoEncadenadoAux() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);
        List<TokenName> siguientes = List.of(PUNTO, ASIGNACION, INCREMENTOR, DECREMENTOR, PUNTO_Y_COMA, OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO, PARENTESIS_CIERRA, COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            argsActuales();
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

}
