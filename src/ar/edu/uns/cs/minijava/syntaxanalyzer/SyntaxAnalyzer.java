package ar.edu.uns.cs.minijava.syntaxanalyzer;

import ar.edu.uns.cs.minijava.ast.expressions.ExpressionNode;
import ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.*;
import ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.classtype.ComparatorBinaryExpressionNode;
import ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.classtype.DistinctBinaryExpressionNode;
import ar.edu.uns.cs.minijava.ast.expressions.binaryexpressions.primitivetype.*;
import ar.edu.uns.cs.minijava.ast.expressions.operand.OperandNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.*;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained.ChainedNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained.MethodChainedNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.access.chained.VariableChainedNode;
import ar.edu.uns.cs.minijava.ast.expressions.operand.literals.*;
import ar.edu.uns.cs.minijava.ast.expressions.unaryexpressions.*;
import ar.edu.uns.cs.minijava.ast.sentences.*;
import ar.edu.uns.cs.minijava.ast.sentences.assignments.AssignmentNode;
import ar.edu.uns.cs.minijava.ast.sentences.assignments.DecrementAssignmentNode;
import ar.edu.uns.cs.minijava.ast.sentences.assignments.EqualAssignmentNode;
import ar.edu.uns.cs.minijava.ast.sentences.assignments.IncrementAssignmentNode;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalAnalyzer;
import ar.edu.uns.cs.minijava.lexicalanalyzer.LexicalException;
import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName;
import ar.edu.uns.cs.minijava.semanticanalyzer.SymbolTable;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.*;
import ar.edu.uns.cs.minijava.semanticanalyzer.entities.Class;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.EntityAlreadyExistsException;
import ar.edu.uns.cs.minijava.semanticanalyzer.exceptions.SemanticException;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.access.Visibility;
import ar.edu.uns.cs.minijava.semanticanalyzer.modifiers.form.AttributeForm;
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
        List<TokenName> primerosDerivacion1 = List.of(PUBLIC_PR, PRIVATE_PR, IDENTIFICADOR_DE_CLASE, BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, STATIC_PR, DYNAMIC_PR);
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
        List<TokenName> primerosDerivacion2 = List.of(IDENTIFICADOR_DE_CLASE, BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR);
        List<TokenName> primerosDerivacion3 = List.of(STATIC_PR);
        List<TokenName> primerosDerivacion4 = List.of(DYNAMIC_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);
        tokensExpected.addAll(primerosDerivacion4);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            atributoConVisibilidadExplicita();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            constructorOAtributoNoEstaticoConVisibilidadImplicita();
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            metodoEstaticoOAtributoEstaticoConVisibilidadImplicita();
        } else if(primerosDerivacion4.contains(currentToken.getTokenName())){
            metodoDinamico();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void atributoConVisibilidadExplicita() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(PUBLIC_PR, PRIVATE_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Visibility visibility = visibilidadExplicita();
            AttributeForm attributeForm = formaAtributo();
            Type type = tipo();
            List<Token> identifiers = identificadoresDeAtributo();
            this.addAttributesToCurrentClass(attributeForm, visibility, type, identifiers);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void addAttributesToCurrentClass(AttributeForm form, Visibility visibility, Type type, List<Token> identifiers) throws EntityAlreadyExistsException {
        Class currentClass = SymbolTable.getInstance().getContext().getCurrentClass();
        Attribute attribute;
        for(Token token : identifiers) {
            attribute = new Attribute(token, type, visibility, form);
            attribute.setClassContainer(currentClass);
            currentClass.addAttribute(token.getLexema(), attribute);
        }
    }

    private void constructorOAtributoNoEstaticoConVisibilidadImplicita() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_CLASE);
        List<TokenName> primerosDerivacion2 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token classIdentifier = currentToken;
            match(IDENTIFICADOR_DE_CLASE);
            constructorOAtributoAux(classIdentifier);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            Type type = tipoPrimitivo();
            List<Token> identifiers = identificadoresDeAtributo();
            this.addAttributesToCurrentClass(AttributeForm.DEFAULT, Visibility.PUBLIC, type, identifiers);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void constructorOAtributoAux(Token classIdentifier) throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);
        List<TokenName> primerosDerivacion2 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Class currentClass = SymbolTable.getInstance().getContext().getCurrentClass();
            Type constructorType = new ReferenceType(currentClass.getIdentifierToken().getLexema());
            Constructor constructor = new Constructor(classIdentifier, constructorType);
            SymbolTable.getInstance().getContext().setCurrentMethod(constructor);
            List<Parameter> parameters = argsFormales();
            for(Parameter parameter : parameters){
                constructor.appendParameter(parameter.getIdentifierToken().getLexema(), parameter);
            }

            BlockSentenceNodeImpl block = bloque();
            constructor.setBodyBlock(block);
            constructor.setClassContainer(currentClass);
            currentClass.setConstructor(constructor);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            Type type = new ReferenceType(classIdentifier.getLexema());
            List<Token> identifiers = identificadoresDeAtributo();
            this.addAttributesToCurrentClass(AttributeForm.DEFAULT, Visibility.PUBLIC, type, identifiers);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void metodoEstaticoOAtributoEstaticoConVisibilidadImplicita() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(STATIC_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(STATIC_PR);
            metodoOAtributoAux();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void metodoOAtributoAux() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(VOID_PR);
        List<TokenName> primerosDerivacion2 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(VOID_PR);
            identificadorParametrosYDefinicionDeMetodo(MethodForm.STATIC, new VoidType());
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            Type type = tipo();
            Token identifier = currentToken;
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            metodoOAtributoAuxAux(type, identifier);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void metodoOAtributoAuxAux(Type type, Token identifier) throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);
        List<TokenName> primerosDerivacion2 = List.of(COMA, PUNTO_Y_COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            parametrosYDefinicionDeMetodo(identifier, MethodForm.STATIC, type);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            List<Token> identifiers = new ArrayList<>();
            identifiers.add(identifier);
            listaDecAtrsAux(identifiers);
            match(PUNTO_Y_COMA);
            this.addAttributesToCurrentClass(AttributeForm.STATIC, Visibility.PUBLIC, type, identifiers);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void metodoDinamico() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(DYNAMIC_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(DYNAMIC_PR);
            Type type = tipoMetodo();
            identificadorParametrosYDefinicionDeMetodo(MethodForm.DYNAMIC, type);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void identificadorParametrosYDefinicionDeMetodo(MethodForm methodForm, Type type) throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token methodIdentifier = currentToken;
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            parametrosYDefinicionDeMetodo(methodIdentifier, methodForm, type);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void parametrosYDefinicionDeMetodo(Token identifier, MethodForm methodForm, Type type) throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Class currentClass = SymbolTable.getInstance().getContext().getCurrentClass();
            Method method = new Method(identifier, type, methodForm);
            method.setClassContainer(currentClass);
            SymbolTable.getInstance().getContext().setCurrentMethod(method);
            List<Parameter> parameters = argsFormales();
            for(Parameter parameter : parameters){
                method.appendParameter(parameter.getIdentifierToken().getLexema(), parameter);
            }
            BlockSentenceNodeImpl block = bloque();
            method.setBodyBlock(block);
            currentClass.addMethod(identifier.getLexema(), method);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AttributeForm formaAtributo() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(STATIC_PR);
        List<TokenName> siguientes = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(STATIC_PR);
            return AttributeForm.STATIC;
        } else if(siguientes.contains(currentToken.getTokenName())){
            return AttributeForm.DEFAULT;
        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }

        return AttributeForm.DEFAULT;
    }

    private List<Token> identificadoresDeAtributo() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            List<Token> identifiers = listaDecAtrs();
            match(PUNTO_Y_COMA);
            return identifiers;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private Visibility visibilidadExplicita() throws LexicalException, SyntaxException {
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
            Token classIdentifier = currentToken;
            match(IDENTIFICADOR_DE_CLASE);
            return new ReferenceType(classIdentifier.getLexema());
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

    private BlockSentenceNodeImpl bloque() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(LLAVE_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())) {
            BlockSentenceNode currentBlock = SymbolTable.getInstance().getContext().getCurrentBlock();
            Method currentMethod = SymbolTable.getInstance().getContext().getCurrentMethod();

            Token blockBegin = currentToken;
            match(LLAVE_ABRE);

            BlockSentenceNodeImpl block = new BlockSentenceNodeImpl(blockBegin, currentMethod, currentBlock);
            SymbolTable.getInstance().getContext().setCurrentBlock(block);

            List<SentenceNode> sentences = new ArrayList<>();
            listaSentencias(sentences);

            match(LLAVE_CIERRA);

            block.setSentences(sentences);

            //volvemos a poner el bloque que estaba (bloque padre) como actual en el contexto
            SymbolTable.getInstance().getContext().setCurrentBlock(currentBlock);

            return block;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaSentencias(List<SentenceNode> sentences) throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(PUNTO_Y_COMA, BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE, RETURN_PR, IF_PR, FOR_PR, LLAVE_ABRE, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);
        List<TokenName> siguientes = List.of(LLAVE_CIERRA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            sentences.add(sentencia());
            listaSentencias(sentences);
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private SentenceNode sentencia() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(PUNTO_Y_COMA);
        List<TokenName> primerosDerivacion2 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR);
        List<TokenName> primerosDerivacion3 = List.of(IDENTIFICADOR_DE_CLASE);
        List<TokenName> primerosDerivacion4 = List.of(RETURN_PR);
        List<TokenName> primerosDerivacion5 = List.of(IF_PR);
        List<TokenName> primerosDerivacion6 = List.of(FOR_PR);
        List<TokenName> primerosDerivacion7 = List.of(LLAVE_ABRE);
        List<TokenName> primerosDerivacion8 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);
        tokensExpected.addAll(primerosDerivacion4);
        tokensExpected.addAll(primerosDerivacion5);
        tokensExpected.addAll(primerosDerivacion6);
        tokensExpected.addAll(primerosDerivacion7);
        tokensExpected.addAll(primerosDerivacion8);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token emptySentence = currentToken;
            match(PUNTO_Y_COMA);
            return new EmptySentenceNode(emptySentence);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            LocalVariable localVariable = varLocalConTipoPrimitivo();
            match(PUNTO_Y_COMA);
            return localVariable.getSentence();
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            SentenceNode sentenceNode = varLocalConTipoDeClaseOAccesoVarMetEstatico();
            match(PUNTO_Y_COMA);
            return sentenceNode;
        } else if(primerosDerivacion4.contains(currentToken.getTokenName())){
            ReturnSentenceNode returnNode = return_();
            match(PUNTO_Y_COMA);
            return returnNode;
        } else if(primerosDerivacion5.contains(currentToken.getTokenName())){
            return if_();
        } else if(primerosDerivacion6.contains(currentToken.getTokenName())){
            return for_();
        } else if(primerosDerivacion7.contains(currentToken.getTokenName())){
            return bloque();
        } else if(primerosDerivacion8.contains(currentToken.getTokenName())){
            SentenceNode sentence = asignacionOLlamadaNoEstaticos();
            match(PUNTO_Y_COMA);
            return sentence;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private SentenceNode asignacionOLlamadaNoEstaticos() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            AccessNode access = accesoExcluyendoEstaticos();
            return asignacionOLlamadaAux(access);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private SentenceNode asignacionOLlamadaAux(AccessNode access) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(ASIGNACION, INCREMENTOR, DECREMENTOR);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            return tipoDeAsignacion(access);
        } else if(siguientes.contains(currentToken.getTokenName())){
            return new CallSentenceNode(access.getSentenceToken(), access);
        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }

        return null;
    }

    private AssignmentNode asignacion() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            AccessNode access = accesoIncluyendoEstaticos();
            return tipoDeAsignacion(access);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AssignmentNode tipoDeAsignacion(AccessNode access) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(ASIGNACION);
        List<TokenName> primerosDerivacion2 = List.of(INCREMENTOR);
        List<TokenName> primerosDerivacion3 = List.of(DECREMENTOR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);

        Token assignationToken;
        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            assignationToken = currentToken;
            match(ASIGNACION);
            ExpressionNode expression = expresion();
            return new EqualAssignmentNode(assignationToken, access, expression);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            assignationToken = currentToken;
            match(INCREMENTOR);
            return new IncrementAssignmentNode(assignationToken, access);
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            assignationToken = currentToken;
            match(DECREMENTOR);
            return new DecrementAssignmentNode(assignationToken, access);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private LocalVariable varLocal() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Type type = tipo();
            return varLocalIdentificadorYDefinicion(type);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private LocalVariable varLocalIdentificadorYDefinicion(Type type) throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token identifier = currentToken;
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            LocalVariable localVariable = new LocalVariable(identifier, type);
            varLocalAux(localVariable);
            SymbolTable
                    .getInstance()
                    .getContext()
                    .getCurrentBlock()
                    .addLocalVariable(localVariable);
            return localVariable;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private LocalVariable varLocalConTipoPrimitivo() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Type type = tipoPrimitivo();
            return varLocalIdentificadorYDefinicion(type);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void varLocalAux(LocalVariable localVariable) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(ASIGNACION);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(ASIGNACION);
            ExpressionNode expression = expresion();
            localVariable.setExpressionAssigned(expression);
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private SentenceNode varLocalConTipoDeClaseOAccesoVarMetEstatico() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token classIdentifier = currentToken;
            match(IDENTIFICADOR_DE_CLASE);
            return varLocalConTipoDeClaseOAccesoVarMetEstaticoAux(classIdentifier);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private SentenceNode varLocalConTipoDeClaseOAccesoVarMetEstaticoAux(Token classIdentifier) throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);
        List<TokenName> primerosDerivacion2 = List.of(PUNTO);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Type referenceType = new ReferenceType(classIdentifier.getLexema());
            return varLocalIdentificadorYDefinicion(referenceType).getSentence();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            AccessNode classAccess = accesoClaseConVariableOMetodoEncadenado(classIdentifier);
            return asignacionOLlamadaAux(classAccess);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private ReturnSentenceNode return_() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(RETURN_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token sentenceToken = currentToken;
            match(RETURN_PR);
            ExpressionNode expression = expresionOVacio();

            return new ReturnSentenceNode(sentenceToken, expression,
                    SymbolTable.getInstance().getContext().getCurrentMethod(),
                    SymbolTable.getInstance().getContext().getCurrentBlock()
            );
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private ExpressionNode expresionOVacio() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, IDENTIFICADOR_DE_CLASE);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            return expresion();
        } else if(siguientes.contains(currentToken.getTokenName())){
            return null;
        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }

        return null;
    }

    private IfSentenceNode if_() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(IF_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token sentenceToken = currentToken;
            Method currentMethod = SymbolTable.getInstance().getContext().getCurrentMethod();
            BlockSentenceNode currentBlock = SymbolTable.getInstance().getContext().getCurrentBlock();
            match(IF_PR);
            match(PARENTESIS_ABRE);
            ExpressionNode condition = expresion();
            match(PARENTESIS_CIERRA);

            //estos bloques son wrappers para sentencias como declaraciones de variables globales.
            //dado que si se declara una variable global como body de un if, no debería ser accesible fuera del scope de ese body.
            BlockSentenceNodeImpl ifBlock = new BlockSentenceNodeImpl(sentenceToken, currentMethod, currentBlock);
            SymbolTable.getInstance().getContext().setCurrentBlock(ifBlock);
            SentenceNode ifBody = sentencia();

            BlockSentenceNodeImpl elseBlock = new BlockSentenceNodeImpl(sentenceToken, currentMethod, currentBlock);
            SymbolTable.getInstance().getContext().setCurrentBlock(elseBlock);
            SentenceNode elseBody = else_();

            SymbolTable.getInstance().getContext().setCurrentBlock(currentBlock); //volvemos al bloque original
            return new IfSentenceNode(sentenceToken, condition, ifBody, elseBody);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private SentenceNode else_() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(ELSE_PR);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA, BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE, RETURN_PR, IF_PR, FOR_PR, LLAVE_ABRE, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, LLAVE_CIERRA, ELSE_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(ELSE_PR);
            return sentencia();
        } else if(siguientes.contains(currentToken.getTokenName())){
            return null; //TODO: pensar si conviene reemplazarlo por EmptySentenceNode
        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }

        return null;
    }

    private ForSentenceNode for_() throws LexicalException, SyntaxException, SemanticException {
        List<TokenName> primerosDerivacion1 = List.of(FOR_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            BlockSentenceNode currentBlock = SymbolTable.getInstance().getContext().getCurrentBlock();
            Method currentMethod = SymbolTable.getInstance().getContext().getCurrentMethod();


            Token forToken = currentToken;
            match(FOR_PR);

            ForSentenceNode forNode = new ForSentenceNode(forToken, currentMethod, currentBlock);
            SymbolTable.getInstance().getContext().setCurrentBlock(forNode);

            match(PARENTESIS_ABRE);
            varLocal();
            match(PUNTO_Y_COMA);
            forNode.setCondition(expresion());
            match(PUNTO_Y_COMA);
            forNode.setAssignment(asignacion());
            match(PARENTESIS_CIERRA);
            forNode.setBody(sentencia());

            SymbolTable.getInstance().getContext().setCurrentBlock(currentBlock);
            //volvemos a poner el bloque padre como bloque actual

            return forNode;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private ExpressionNode expresionSinAccesoEstatico() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            UnaryExpressionNode leftExpression = expresionUnariaSinAccesoEstatico();
            return expresionBinaria(leftExpression);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private ExpressionNode expresion() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            UnaryExpressionNode leftExpression = expresionUnariaConAccesoEstatico();
            return expresionBinaria(leftExpression);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private ExpressionNode expresionBinaria(UnaryExpressionNode leftExpression) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO);
        List<TokenName> siguientes = List.of(PUNTO_Y_COMA, PARENTESIS_CIERRA, COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            BinaryExpressionNode operator = operadorBinario();
            operator.setLeftExpression(leftExpression);

            UnaryExpressionNode rightExpression = expresionUnariaConAccesoEstatico();
            ExpressionNode extraExpression = expresionBinaria(rightExpression);
            operator.setRightExpression(extraExpression);

            return operator;
        } else if(siguientes.contains(currentToken.getTokenName())){
            return leftExpression;
        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }

        return null;
    }

    private BinaryExpressionNode operadorBinario() throws LexicalException, SyntaxException {
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

        Token operatorToken;
        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(OR);
            return new OrBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(AND);
            return new AndBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(COMPARACION);
            return new ComparatorBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion4.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(DISTINTO);
            return new DistinctBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion5.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(MENOR);
            return new LessBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion6.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(MAYOR);
            return new GreaterBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion7.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(MENOR_IGUAL);
            return new LessOrEqualBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion8.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(MAYOR_IGUAL);
            return new GreaterOrEqualBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion9.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(SUMA);
            return new PlusBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion10.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(RESTA);
            return new MinusBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion11.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(PRODUCTO);
            return new MultiplicationBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion12.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(DIVISION);
            return new DivisionBinaryExpressionNode(operatorToken);
        } else if(primerosDerivacion13.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(MODULO);
            return new ModuleBinaryExpressionNode(operatorToken);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private UnaryExpressionNode expresionUnariaConAccesoEstatico() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION);
        List<TokenName> primerosDerivacion2 = List.of(NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            UnaryExpressionNode operator = operadorUnario();
            OperandNode operand = operandoConAccesoEstatico();
            operator.setOperand(operand);
            return operator;
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            OperandNode operand = operandoConAccesoEstatico();
            UnaryExpressionNode unaryExpression = new WithoutOperatorUnaryExpressionNode(operand.getSentenceToken());
            unaryExpression.setOperand(operand);

            return unaryExpression;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private UnaryExpressionNode expresionUnariaSinAccesoEstatico() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION);
        List<TokenName> primerosDerivacion2 = List.of(NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            UnaryExpressionNode operator = operadorUnario();
            OperandNode operand = operandoConAccesoEstatico();
            operator.setOperand(operand);
            return operator;
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            OperandNode operand = operandoSinAccesoEstatico();
            UnaryExpressionNode unaryExpression = new WithoutOperatorUnaryExpressionNode(operand.getSentenceToken());
            unaryExpression.setOperand(operand);

            return unaryExpression;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private UnaryExpressionNode operadorUnario() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA);
        List<TokenName> primerosDerivacion2 = List.of(RESTA);
        List<TokenName> primerosDerivacion3 = List.of(NEGACION);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);

        Token operatorToken;
        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(SUMA);
            return new PlusUnaryExpressionNode(operatorToken);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(RESTA);
            return new MinusUnaryExpressionNode(operatorToken);
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            operatorToken = currentToken;
            match(NEGACION);
            return new NegationUnaryExpressionNode(operatorToken);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private OperandNode operandoSinAccesoEstatico() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING);
        List<TokenName> primerosDerivacion2 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            return literal();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            return accesoExcluyendoEstaticos();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private OperandNode operandoConAccesoEstatico() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING);
        List<TokenName> primerosDerivacion2 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            return literal();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            return accesoIncluyendoEstaticos();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private LiteralNode literal() throws LexicalException, SyntaxException {
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

        Token literalToken;
        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            literalToken = currentToken;
            match(NULL_PR);
            return new NullLiteralNode(literalToken);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            literalToken = currentToken;
            match(TRUE_PR);
            return new TrueLiteralNode(literalToken);
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            literalToken = currentToken;
            match(FALSE_PR);
            return new FalseLiteralNode(literalToken);
        } else if(primerosDerivacion4.contains(currentToken.getTokenName())){
            literalToken = currentToken;
            match(ENTERO);
            return new IntLiteralNode(literalToken);
        } else if(primerosDerivacion5.contains(currentToken.getTokenName())){
            literalToken = currentToken;
            match(CARACTER);
            return new CharLiteralNode(literalToken);
        } else if(primerosDerivacion6.contains(currentToken.getTokenName())){
            literalToken = currentToken;
            match(STRING);
            return new StringLiteralNode(literalToken);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode accesoIncluyendoEstaticos() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);
        List<TokenName> primerosDerivacion2 = List.of(IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            return accesoExcluyendoEstaticos();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            return accesoVarOMetodoEstatico();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode accesoExcluyendoEstaticos() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE);
        List<TokenName> primerosDerivacion2 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            AccessNode access = primarioSinExpresionParentizadaYSinAccesoEstatico();
            ChainedNode chained = encadenado();
            if (chained != null) {
                access.setChained(chained);
            }
            return access;
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            AccessNode access = castingOExpresionParentizada();
            ChainedNode chained = encadenado();
            if (chained != null) {
                access.setChained(chained);
            }
            return access;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode castingOExpresionParentizada() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PARENTESIS_ABRE);
            return castingOExpresionParentizadaAux();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode castingOExpresionParentizadaAux() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_CLASE);
        List<TokenName> primerosDerivacion2 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token className = currentToken;
            match(IDENTIFICADOR_DE_CLASE);
            return castingOExpresionParentizadaAuxAux(className);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            ExpressionNode expression = expresionSinAccesoEstatico();
            match(PARENTESIS_CIERRA);
            return new ExpressionAccessNode(expression.getSentenceToken(), expression);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode castingOExpresionParentizadaAuxAux(Token className) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_CIERRA);
        List<TokenName> primerosDerivacion2 = List.of(PUNTO);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PARENTESIS_CIERRA);
            AccessNode access = primarioConExpresionParentizada();
            access.setCastingType(new ReferenceType(className.getLexema()));
            return access;
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            AccessNode classAccess = accesoClaseConVariableOMetodoEncadenado(className);
            match(PARENTESIS_CIERRA);
            return classAccess;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode primarioConExpresionParentizada() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);
        List<TokenName> primerosDerivacion2 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            ExpressionNode expression = expresionParentizada();
            return new ExpressionAccessNode(expression.getSentenceToken(), expression);
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            return primarioSinExpresionParentizada();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode primarioSinExpresionParentizada() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE);
        List<TokenName> primerosDerivacion2 = List.of(IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            return primarioSinExpresionParentizadaYSinAccesoEstatico();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            return accesoVarOMetodoEstatico();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode accesoVarOMetodoEstatico() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token className = currentToken;
            match(IDENTIFICADOR_DE_CLASE);
            return accesoClaseConVariableOMetodoEncadenado(className);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode accesoClaseConVariableOMetodoEncadenado(Token className) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion2 = List.of(PUNTO);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion2);

        if (primerosDerivacion2.contains(currentToken.getTokenName())) {
            AccessNode classAccess = new ClassAccessNode(className);
            ChainedNode firstChained = varOMetodoEncadenado();
            ChainedNode extraChained = encadenado();
            firstChained.setNextChained(extraChained);
            classAccess.setChained(firstChained);
            return classAccess;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode primarioSinExpresionParentizadaYSinAccesoEstatico() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR);
        List<TokenName> primerosDerivacion2 = List.of(NEW_PR);
        List<TokenName> primerosDerivacion3 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(primerosDerivacion2);
        tokensExpected.addAll(primerosDerivacion3);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            return accesoThis();
        } else if(primerosDerivacion2.contains(currentToken.getTokenName())){
            return accesoConstructor();
        } else if(primerosDerivacion3.contains(currentToken.getTokenName())){
            return accesoVarOMetodo();
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private ExpressionNode expresionParentizada() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PARENTESIS_ABRE);
            ExpressionNode expression = expresion();
            match(PARENTESIS_CIERRA);
            return expression;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode accesoVarOMetodo() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(IDENTIFICADOR_DE_METODO_O_VARIABLE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token identifier = currentToken;
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            return accesoVarOMetodoAux(identifier);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private AccessNode accesoVarOMetodoAux(Token identifier) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);
        List<TokenName> siguientes = List.of(PUNTO, ASIGNACION, INCREMENTOR, DECREMENTOR, PUNTO_Y_COMA, OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO, PARENTESIS_CIERRA, COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            List<ExpressionNode> parameters = argsActuales();
            return new MethodAccessNode(identifier, SymbolTable.getInstance().getContext().getCurrentMethod(), parameters);
        } else if(siguientes.contains(currentToken.getTokenName())){
            return new VariableAccessNode(identifier, SymbolTable.getInstance().getContext().getCurrentBlock());
        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }

        return null;
    }

    private ThisAccessNode accesoThis() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(THIS_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            Token thisToken = currentToken;
            match(THIS_PR);
            return new ThisAccessNode(thisToken, SymbolTable.getInstance().getContext().getCurrentMethod());
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private ConstructorAccessNode accesoConstructor() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(NEW_PR);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(NEW_PR);
            Token identifier = currentToken;
            match(IDENTIFICADOR_DE_CLASE);

            List<ExpressionNode> arguments = argsActuales();

            return new ConstructorAccessNode(identifier, SymbolTable.getInstance().getContext().getCurrentMethod(), arguments);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private List<ExpressionNode> argsActuales() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        List<ExpressionNode> arguments = new ArrayList<>();

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PARENTESIS_ABRE);
            listaExpsOVacio(arguments);
            match(PARENTESIS_CIERRA);

            return arguments;
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaExpsOVacio(List<ExpressionNode> arguments) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, IDENTIFICADOR_DE_CLASE);
        List<TokenName> siguientes = List.of(PARENTESIS_CIERRA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            listaExps(arguments);
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaExps(List<ExpressionNode> arguments) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, IDENTIFICADOR_DE_CLASE);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            ExpressionNode expression = expresion();
            arguments.add(expression);
            listaExpsAux(arguments);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private void listaExpsAux(List<ExpressionNode> arguments) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(COMA);
        List<TokenName> siguientes = List.of(PARENTESIS_CIERRA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(COMA);
            ExpressionNode expression = expresion();
            arguments.add(expression);
            listaExpsAux(arguments);
        } else if(siguientes.contains(currentToken.getTokenName())){

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private ChainedNode encadenado() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PUNTO);
        List<TokenName> siguientes = List.of(ASIGNACION, INCREMENTOR, DECREMENTOR, PUNTO_Y_COMA, OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO, PARENTESIS_CIERRA, COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            ChainedNode firstChained = varOMetodoEncadenado();
            ChainedNode nextChained = encadenado();
            firstChained.setNextChained(nextChained);
            return firstChained;
        } else if(siguientes.contains(currentToken.getTokenName())){
            return null;
        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }

        return null;
    }

    private ChainedNode varOMetodoEncadenado() throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PUNTO);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            match(PUNTO);
            Token identifier = currentToken;
            match(IDENTIFICADOR_DE_METODO_O_VARIABLE);
            return varOMetodoEncadenadoAux(identifier);
        } else {
            throw new SyntaxException(currentToken, tokensExpected);
        }
    }

    private ChainedNode varOMetodoEncadenadoAux(Token identifier) throws LexicalException, SyntaxException {
        List<TokenName> primerosDerivacion1 = List.of(PARENTESIS_ABRE);
        List<TokenName> siguientes = List.of(PUNTO, ASIGNACION, INCREMENTOR, DECREMENTOR, PUNTO_Y_COMA, OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO, PARENTESIS_CIERRA, COMA);

        List<TokenName> tokensExpected = new ArrayList<>();
        tokensExpected.addAll(primerosDerivacion1);
        tokensExpected.addAll(siguientes);

        if(primerosDerivacion1.contains(currentToken.getTokenName())){
            List<ExpressionNode> parameters = argsActuales();
            return new MethodChainedNode(identifier, parameters);

        } else if(siguientes.contains(currentToken.getTokenName())){
            return new VariableChainedNode(identifier, SymbolTable.getInstance().getContext().getCurrentBlock());

        } else if(!currentToken.getTokenName().equals(EOF)){
            throw new SyntaxException(currentToken, tokensExpected);
        }

        return null;
    }

}
