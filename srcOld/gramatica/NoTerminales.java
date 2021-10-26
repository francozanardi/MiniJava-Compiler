package ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica;

import ar.edu.uns.cs.minijava.lexicalanalyzer.Token;
import ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core.Derivacion;
import ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core.NoTerminal;
import ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core.NoTerminalConEpsilon;
import ar.edu.uns.cs.minijava.syntaxanalyzer.gramatica.core.Terminal;

import java.util.Objects;


import static ar.edu.uns.cs.minijava.lexicalanalyzer.TokenName.*;

public class NoTerminales {

    private final Terminales terminales = Terminales.getInstance();

    private NoTerminal<Void, Void> inicial;
    private NoTerminal listaClases;
    private NoTerminal listaClasesAux;
    private NoTerminal clase;
    private NoTerminal<Void, Token> herencia;
    private NoTerminal listaMiembros;
    private NoTerminal miembro;
    private NoTerminal atributo;
    private NoTerminal constructor;
    private NoTerminal metodo;
    private NoTerminal visibilidad;
    private NoTerminal tipo;
    private NoTerminal tipoPrimitivo;
    private NoTerminal listaDecAtrs;
    private NoTerminal listaDecAtrsAux;
    private NoTerminal formaMetodo;
    private NoTerminal tipoMetodo;
    private NoTerminal argsFormales;
    private NoTerminal listaArgsFormalesOVacio;
    private NoTerminal listaArgsFormales;
    private NoTerminal listaArgsFormalesAux;
    private NoTerminal argFormal;
    private NoTerminal bloque;
    private NoTerminal listaSentencias;
    private NoTerminal sentencia;
    private NoTerminal asignacionOLlamada;
    private NoTerminal asignacionOLlamadaAux;
    private NoTerminal asignacion;
    private NoTerminal tipoDeAsignacion;
    private NoTerminal varLocal;
    private NoTerminal varLocalAux;
    private NoTerminal return_;
    private NoTerminal expresionOVacio;
    private NoTerminal if_;
    private NoTerminal else_;
    private NoTerminal for_;
    private NoTerminal expresion;
    private NoTerminal expresionBinaria;
    private NoTerminal operadorBinario;
    private NoTerminal expresionUnaria;
    private NoTerminal operadorUnario;
    private NoTerminal operando;
    private NoTerminal literal;
    private NoTerminal acceso;
    private NoTerminal castingOExpresionParentizada;
    private NoTerminal castingOExpresionParentizadaAux;
    private NoTerminal primarioConExpresionParentizada;
    private NoTerminal primarioSinExpresionParentizada;
    private NoTerminal expresionParentizada;
    private NoTerminal accesoVarOMetodo;
    private NoTerminal accesoVarOMetodoAux;
    private NoTerminal accesoThis;
    private NoTerminal accesoConstructor;
    private NoTerminal argsActuales;
    private NoTerminal listaExpsOVacio;
    private NoTerminal listaExps;
    private NoTerminal listaExpsAux;
    private NoTerminal encadenado;
    private NoTerminal varOMetodoEncadenado;
    private NoTerminal varOMetodoEncadenadoAux;

    private final static NoTerminales instance = new NoTerminales();

    private NoTerminales(){

    }

    public static NoTerminales getInstance(){
        return instance;
    }

    public NoTerminal<Void, Void> inicial_(){
        return Objects.requireNonNullElse(inicial,
                inicial = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(CLASS_PR)
                                        .appendEstado(this::listaClases)
                        )
                        .build());
    }

    public NoTerminal inicial(){
        return Objects.requireNonNullElse(inicial,
                inicial = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(CLASS_PR)
                                        .appendEstado(this::listaClases)
                        )
                        .build());
    }

    private NoTerminal listaClases(){
        return Objects.requireNonNullElse(listaClases,
                listaClases = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(CLASS_PR)
                                        .appendEstado(this::clase)
                                        .appendEstado(this::listaClasesAux)
                        )
                        .build());
    }

    private NoTerminal listaClasesAux(){
        return Objects.requireNonNullElse(listaClasesAux,
                listaClasesAux = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(CLASS_PR)
                                        .appendEstado(this::clase)
                                        .appendEstadoRecursivo()
                        )
                        .build());
    }

    private NoTerminal clase(){
        return Objects.requireNonNullElse(clase,
                clase = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(CLASS_PR)
                                        .appendEstado(terminales.getTerminal(CLASS_PR))
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_CLASE))
                                        .appendEstado(this::herencia)
                                        .appendEstado(terminales.getTerminal(LLAVE_ABRE))
                                        .appendEstado(this::listaMiembros)
                                        .appendEstado(terminales.getTerminal(LLAVE_CIERRA))
                        )
                        .build());
    }

    private NoTerminal<Void, Token> herencia_(){
        return Objects.requireNonNullElse(herencia,
                herencia = NoTerminalConEpsilon
                        .create(Void.class, Token.class)
                        .appendDerivacion(
                                Derivacion.create(Token.class, EXTENDS_PR)
                                        .appendEstado(terminales.getTerminal(EXTENDS_PR))
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_CLASE))
                        )
                        .appendSiguientes(LLAVE_ABRE)
                        .build());
    }

    private NoTerminal herencia(){
        return Objects.requireNonNullElse(herencia,
                herencia = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(EXTENDS_PR)
                                        .appendEstado(terminales.getTerminal(EXTENDS_PR))
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_CLASE))
                        )
                        .appendSiguientes(LLAVE_ABRE)
                        .build());
    }

    private NoTerminal listaMiembros(){
        return Objects.requireNonNullElse(listaMiembros,
                listaMiembros = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PUBLIC_PR, PRIVATE_PR, IDENTIFICADOR_DE_CLASE, STATIC_PR, DYNAMIC_PR)
                                        .appendEstado(this::miembro)
                                        .appendEstadoRecursivo()
                        )
                        .appendSiguientes(LLAVE_CIERRA)
                        .build());
    }

    private NoTerminal miembro(){
        return Objects.requireNonNullElse(miembro,
                miembro = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PUBLIC_PR, PRIVATE_PR)
                                        .appendEstado(this::atributo)
                        )
                        .appendDerivacion(
                                Derivacion.create(IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(this::constructor)
                        )
                        .appendDerivacion(
                                Derivacion.create(STATIC_PR, DYNAMIC_PR)
                                        .appendEstado(this::metodo)
                        )
                        .build());
    }

    private NoTerminal atributo(){
        return Objects.requireNonNullElse(atributo,
                atributo = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PUBLIC_PR, PRIVATE_PR)
                                        .appendEstado(this::visibilidad)
                                        .appendEstado(this::tipo)
                                        .appendEstado(this::listaDecAtrs)
                                        .appendEstado(terminales.getTerminal(PUNTO_Y_COMA))
                        )
                        .build());
    }

    private NoTerminal constructor(){
        return Objects.requireNonNullElse(constructor,
                constructor = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_CLASE))
                                        .appendEstado(this::argsFormales)
                                        .appendEstado(this::bloque)
                        )
                        .build());
    }

    private NoTerminal metodo(){
        return Objects.requireNonNullElse(metodo,
                metodo = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(STATIC_PR, DYNAMIC_PR)
                                        .appendEstado(this::formaMetodo)
                                        .appendEstado(this::tipoMetodo)
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_METODO_O_VARIABLE))
                                        .appendEstado(this::argsFormales)
                                        .appendEstado(this::bloque)
                        )
                        .build());
    }

    private NoTerminal visibilidad(){
        return Objects.requireNonNullElse(visibilidad,
                visibilidad = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PUBLIC_PR)
                                        .appendEstado(terminales.getTerminal(PUBLIC_PR))
                        )
                        .appendDerivacion(
                                Derivacion.create(PRIVATE_PR)
                                        .appendEstado(terminales.getTerminal(PRIVATE_PR))
                        )
                        .build());
    }

    private NoTerminal tipo(){
        return Objects.requireNonNullElse(tipo,
                tipo = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR)
                                        .appendEstado(this::tipoPrimitivo)
                        )
                        .appendDerivacion(
                                Derivacion.create(IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_CLASE))
                        )
                        .build());
    }

    private NoTerminal tipoPrimitivo(){
        return Objects.requireNonNullElse(tipoPrimitivo,
                tipoPrimitivo = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(BOOLEAN_PR)
                                        .appendEstado(terminales.getTerminal(BOOLEAN_PR))
                        )
                        .appendDerivacion(
                                Derivacion.create(CHAR_PR)
                                        .appendEstado(terminales.getTerminal(CHAR_PR))
                        )
                        .appendDerivacion(
                                Derivacion.create(INT_PR)
                                        .appendEstado(terminales.getTerminal(INT_PR))
                        )
                        .appendDerivacion(
                                Derivacion.create(STRING_PR)
                                        .appendEstado(terminales.getTerminal(STRING_PR))
                        )
                        .build());
    }

    private NoTerminal listaDecAtrs(){
        return Objects.requireNonNullElse(listaDecAtrs,
                listaDecAtrs = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(IDENTIFICADOR_DE_METODO_O_VARIABLE)
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_METODO_O_VARIABLE))
                                        .appendEstado(this::listaDecAtrsAux)
                        )
                        .build());
    }

    private NoTerminal listaDecAtrsAux(){
        return Objects.requireNonNullElse(listaDecAtrsAux,
                listaDecAtrsAux = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(COMA)
                                        .appendEstado(terminales.getTerminal(COMA))
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_METODO_O_VARIABLE))
                                        .appendEstadoRecursivo()
                        )
                        .appendSiguientes(PUNTO_Y_COMA)
                        .build());
    }

    private NoTerminal formaMetodo(){
        return Objects.requireNonNullElse(formaMetodo,
                formaMetodo = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(STATIC_PR)
                                        .appendEstado(terminales.getTerminal(STATIC_PR))
                        )
                        .appendDerivacion(
                                Derivacion.create(DYNAMIC_PR)
                                        .appendEstado(terminales.getTerminal(DYNAMIC_PR))
                        )
                        .build());
    }

    private NoTerminal tipoMetodo(){
        return Objects.requireNonNullElse(tipoMetodo,
                tipoMetodo = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(this::tipo)
                        )
                        .appendDerivacion(
                                Derivacion.create(VOID_PR)
                                        .appendEstado(terminales.getTerminal(VOID_PR))
                        )
                        .build());
    }

    private NoTerminal argsFormales(){
        return Objects.requireNonNullElse(argsFormales,
                argsFormales = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PARENTESIS_ABRE)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_ABRE))
                                        .appendEstado(this::listaArgsFormalesOVacio)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_CIERRA))
                        )
                        .build());
    }

    private NoTerminal listaArgsFormalesOVacio(){
        return Objects.requireNonNullElse(listaArgsFormalesOVacio,
                listaArgsFormalesOVacio = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(this::listaArgsFormales)
                        )
                        .appendSiguientes(PARENTESIS_CIERRA)
                        .build());
    }

    private NoTerminal listaArgsFormales(){
        return Objects.requireNonNullElse(listaArgsFormales,
                listaArgsFormales = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(this::argFormal)
                                        .appendEstado(this::listaArgsFormalesAux)
                        )
                        .build());
    }

    private NoTerminal listaArgsFormalesAux(){
        return Objects.requireNonNullElse(listaArgsFormalesAux,
                listaArgsFormalesAux = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(COMA)
                                        .appendEstado(terminales.getTerminal(COMA))
                                        .appendEstado(this::argFormal)
                                        .appendEstadoRecursivo()
                        )
                        .appendSiguientes(PARENTESIS_CIERRA)
                        .build());
    }

    private NoTerminal argFormal(){
        return Objects.requireNonNullElse(argFormal,
                argFormal = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(this::tipo)
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_METODO_O_VARIABLE))
                        )
                        .build());
    }

    private NoTerminal bloque(){
        return Objects.requireNonNullElse(bloque,
                bloque = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(LLAVE_ABRE)
                                        .appendEstado(terminales.getTerminal(LLAVE_ABRE))
                                        .appendEstado(this::listaSentencias)
                                        .appendEstado(terminales.getTerminal(LLAVE_CIERRA))
                        )
                        .build());
    }

    private NoTerminal listaSentencias(){
        return Objects.requireNonNullElse(listaSentencias,
                listaSentencias = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PUNTO_Y_COMA, BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE, RETURN_PR, IF_PR, FOR_PR, LLAVE_ABRE, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::sentencia)
                                        .appendEstadoRecursivo()
                        )
                        .appendSiguientes(LLAVE_CIERRA)
                        .build());
    }

    private NoTerminal sentencia(){
        return Objects.requireNonNullElse(sentencia,
                sentencia = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PUNTO_Y_COMA)
                                        .appendEstado(terminales.getTerminal(PUNTO_Y_COMA))
                        )
                        .appendDerivacion(
                                Derivacion.create(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(this::varLocal)
                                        .appendEstado(terminales.getTerminal(PUNTO_Y_COMA))
                        )
                        .appendDerivacion(
                                Derivacion.create(RETURN_PR)
                                        .appendEstado(this::return_)
                                        .appendEstado(terminales.getTerminal(PUNTO_Y_COMA))
                        )
                        .appendDerivacion(
                                Derivacion.create(IF_PR)
                                        .appendEstado(this::if_)
                        )
                        .appendDerivacion(
                                Derivacion.create(FOR_PR)
                                        .appendEstado(this::for_)
                        )
                        .appendDerivacion(
                                Derivacion.create(LLAVE_ABRE)
                                        .appendEstado(this::bloque)
                        )
                        .appendDerivacion(
                                Derivacion.create(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::asignacionOLlamada)
                                        .appendEstado(terminales.getTerminal(PUNTO_Y_COMA))
                        )
                        .build());
    }

    private NoTerminal asignacionOLlamada(){
        return Objects.requireNonNullElse(asignacionOLlamada,
                asignacionOLlamada = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::acceso)
                                        .appendEstado(this::asignacionOLlamadaAux)
                        )
                        .build());
    }

    private NoTerminal asignacionOLlamadaAux(){
        return Objects.requireNonNullElse(asignacionOLlamadaAux,
                asignacionOLlamadaAux = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(ASIGNACION, INCREMENTOR, DECREMENTOR)
                                        .appendEstado(this::tipoDeAsignacion)
                        )
                        .appendSiguientes(PUNTO_Y_COMA)
                        .build());
    }

    private NoTerminal asignacion(){
        return Objects.requireNonNullElse(asignacion,
                asignacion = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::acceso)
                                        .appendEstado(this::tipoDeAsignacion)
                        )
                        .build());
    }

    private NoTerminal tipoDeAsignacion(){
        return Objects.requireNonNullElse(tipoDeAsignacion,
                tipoDeAsignacion = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(ASIGNACION)
                                        .appendEstado(terminales.getTerminal(ASIGNACION))
                                        .appendEstado(this::expresion)
                        )
                        .appendDerivacion(
                                Derivacion.create(INCREMENTOR)
                                        .appendEstado(terminales.getTerminal(INCREMENTOR))
                        )
                        .appendDerivacion(
                                Derivacion.create(DECREMENTOR)
                                        .appendEstado(terminales.getTerminal(DECREMENTOR))
                        )
                        .build());
    }

    private NoTerminal varLocal(){
        return Objects.requireNonNullElse(varLocal,
                varLocal = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(this::tipo)
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_METODO_O_VARIABLE))
                                        .appendEstado(this::varLocalAux)
                        )
                        .build());
    }

    private NoTerminal varLocalAux(){
        return Objects.requireNonNullElse(varLocalAux,
                varLocalAux = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(ASIGNACION)
                                        .appendEstado(terminales.getTerminal(ASIGNACION))
                                        .appendEstado(this::expresion)
                        )
                        .appendSiguientes(PUNTO_Y_COMA)
                        .build());
    }

    private NoTerminal return_(){
        return Objects.requireNonNullElse(return_,
                return_ = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(RETURN_PR)
                                        .appendEstado(terminales.getTerminal(RETURN_PR))
                                        .appendEstado(this::expresionOVacio)
                        )
                        .build());
    }

    private NoTerminal expresionOVacio(){
        return Objects.requireNonNullElse(expresionOVacio,
                expresionOVacio = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::expresion)
                        )
                        .appendSiguientes(PUNTO_Y_COMA)
                        .build());
    }

    private NoTerminal if_(){
        return Objects.requireNonNullElse(if_,
                if_ = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(IF_PR)
                                        .appendEstado(terminales.getTerminal(IF_PR))
                                        .appendEstado(terminales.getTerminal(PARENTESIS_ABRE))
                                        .appendEstado(this::expresion)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_CIERRA))
                                        .appendEstado(this::sentencia)
                                        .appendEstado(this::else_)
                        )
                        .build());
    }

    private NoTerminal else_(){
        return Objects.requireNonNullElse(else_,
                else_ = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(ELSE_PR)
                                        .appendEstado(terminales.getTerminal(ELSE_PR))
                                        .appendEstado(this::sentencia)
                        )
                        .appendSiguientes(PUNTO_Y_COMA, BOOLEAN_PR, CHAR_PR, INT_PR, STRING_PR, IDENTIFICADOR_DE_CLASE, RETURN_PR, IF_PR, FOR_PR, LLAVE_ABRE, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE, LLAVE_CIERRA, ELSE_PR)
                        .build());
    }

    private NoTerminal for_(){
        return Objects.requireNonNullElse(for_,
                for_ = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(FOR_PR)
                                        .appendEstado(terminales.getTerminal(FOR_PR))
                                        .appendEstado(terminales.getTerminal(PARENTESIS_ABRE))
                                        .appendEstado(this::varLocal)
                                        .appendEstado(terminales.getTerminal(PUNTO_Y_COMA))
                                        .appendEstado(this::expresion)
                                        .appendEstado(terminales.getTerminal(PUNTO_Y_COMA))
                                        .appendEstado(this::asignacion)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_CIERRA))
                                        .appendEstado(this::sentencia)
                        )
                        .build());
    }

    private NoTerminal expresion(){
        return Objects.requireNonNullElse(expresion,
                expresion = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::expresionUnaria)
                                        .appendEstado(this::expresionBinaria)
                        )
                        .build());
    }

    private NoTerminal expresionBinaria(){
        return Objects.requireNonNullElse(expresionBinaria,
                expresionBinaria = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO)
                                        .appendEstado(this::operadorBinario)
                                        .appendEstado(this::expresionUnaria)
                                        .appendEstadoRecursivo()
                        )
                        .appendSiguientes(PUNTO_Y_COMA, PARENTESIS_CIERRA, COMA)
                        .build());
    }

    private NoTerminal operadorBinario(){
        return Objects.requireNonNullElse(operadorBinario,
                operadorBinario = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(OR)
                                        .appendEstado(terminales.getTerminal(OR))
                        )
                        .appendDerivacion(
                                Derivacion.create(AND)
                                        .appendEstado(terminales.getTerminal(AND))
                        )
                        .appendDerivacion(
                                Derivacion.create(COMPARACION)
                                        .appendEstado(terminales.getTerminal(COMPARACION))
                        )
                        .appendDerivacion(
                                Derivacion.create(DISTINTO)
                                        .appendEstado(terminales.getTerminal(DISTINTO))
                        )
                        .appendDerivacion(
                                Derivacion.create(MENOR)
                                        .appendEstado(terminales.getTerminal(MENOR))
                        )
                        .appendDerivacion(
                                Derivacion.create(MAYOR)
                                        .appendEstado(terminales.getTerminal(MAYOR))
                        )
                        .appendDerivacion(
                                Derivacion.create(MENOR_IGUAL)
                                        .appendEstado(terminales.getTerminal(MENOR_IGUAL))
                        )
                        .appendDerivacion(
                                Derivacion.create(MAYOR_IGUAL)
                                        .appendEstado(terminales.getTerminal(MAYOR_IGUAL))
                        )
                        .appendDerivacion(
                                Derivacion.create(SUMA)
                                        .appendEstado(terminales.getTerminal(SUMA))
                        )
                        .appendDerivacion(
                                Derivacion.create(RESTA)
                                        .appendEstado(terminales.getTerminal(RESTA))
                        )
                        .appendDerivacion(
                                Derivacion.create(PRODUCTO)
                                        .appendEstado(terminales.getTerminal(PRODUCTO))
                        )
                        .appendDerivacion(
                                Derivacion.create(DIVISION)
                                        .appendEstado(terminales.getTerminal(DIVISION))
                        )
                        .appendDerivacion(
                                Derivacion.create(MODULO)
                                        .appendEstado(terminales.getTerminal(MODULO))
                        )
                        .build());
    }

    private NoTerminal expresionUnaria(){
        return Objects.requireNonNullElse(expresionUnaria,
                expresionUnaria = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(SUMA, RESTA, NEGACION)
                                        .appendEstado(this::operadorUnario)
                                        .appendEstado(this::operando)
                        )
                        .appendDerivacion(
                                Derivacion.create(NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::operando)
                        )
                        .build());
    }

    private NoTerminal operadorUnario(){
        return Objects.requireNonNullElse(operadorUnario,
                operadorUnario = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(SUMA)
                                        .appendEstado(terminales.getTerminal(SUMA))
                        )
                        .appendDerivacion(
                                Derivacion.create(RESTA)
                                        .appendEstado(terminales.getTerminal(RESTA))
                        )
                        .appendDerivacion(
                                Derivacion.create(NEGACION)
                                        .appendEstado(terminales.getTerminal(NEGACION))
                        )
                        .build());
    }

    private NoTerminal operando(){
        return Objects.requireNonNullElse(operando,
                operando = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING)
                                        .appendEstado(this::literal)
                        )
                        .appendDerivacion(
                                Derivacion.create(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::acceso)
                        )
                        .build());
    }

    private NoTerminal literal(){
        return Objects.requireNonNullElse(literal,
                literal = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(NULL_PR)
                                        .appendEstado(terminales.getTerminal(NULL_PR))
                        )
                        .appendDerivacion(
                                Derivacion.create(TRUE_PR)
                                        .appendEstado(terminales.getTerminal(TRUE_PR))
                        )
                        .appendDerivacion(
                                Derivacion.create(FALSE_PR)
                                        .appendEstado(terminales.getTerminal(FALSE_PR))
                        )
                        .appendDerivacion(
                                Derivacion.create(ENTERO)
                                        .appendEstado(terminales.getTerminal(ENTERO))
                        )
                        .appendDerivacion(
                                Derivacion.create(CARACTER)
                                        .appendEstado(terminales.getTerminal(CARACTER))
                        )
                        .appendDerivacion(
                                Derivacion.create(STRING)
                                        .appendEstado(terminales.getTerminal(STRING))
                        )
                        .build());
    }

    private NoTerminal acceso(){
        return Objects.requireNonNullElse(acceso,
                acceso = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE)
                                        .appendEstado(this::primarioSinExpresionParentizada)
                                        .appendEstado(this::encadenado)
                        )
                        .appendDerivacion(
                                Derivacion.create(PARENTESIS_ABRE)
                                        .appendEstado(this::castingOExpresionParentizada)
                                        .appendEstado(this::encadenado)
                        )
                        .build());
    }

    private NoTerminal castingOExpresionParentizada(){
        return Objects.requireNonNullElse(castingOExpresionParentizada,
                castingOExpresionParentizada = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PARENTESIS_ABRE)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_ABRE))
                                        .appendEstado(this::castingOExpresionParentizadaAux)
                        )
                        .build());
    }

    private NoTerminal castingOExpresionParentizadaAux(){
        return Objects.requireNonNullElse(castingOExpresionParentizadaAux,
                castingOExpresionParentizadaAux = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(IDENTIFICADOR_DE_CLASE)
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_CLASE))
                                        .appendEstado(terminales.getTerminal(PARENTESIS_CIERRA))
                                        .appendEstado(this::primarioConExpresionParentizada)
                        )
                        .appendDerivacion(
                                Derivacion.create(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::expresion)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_CIERRA))
                        )
                        .build());
    }

    private NoTerminal primarioConExpresionParentizada(){
        return Objects.requireNonNullElse(primarioConExpresionParentizada,
                primarioConExpresionParentizada = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PARENTESIS_ABRE)
                                        .appendEstado(this::expresionParentizada)
                        )
                        .appendDerivacion(
                                Derivacion.create(THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE)
                                        .appendEstado(this::primarioSinExpresionParentizada)
                        )
                        .build());
    }

    private NoTerminal primarioSinExpresionParentizada(){
        return Objects.requireNonNullElse(primarioSinExpresionParentizada,
                primarioSinExpresionParentizada = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(THIS_PR)
                                        .appendEstado(this::accesoThis)
                        )
                        .appendDerivacion(
                                Derivacion.create(NEW_PR)
                                        .appendEstado(this::accesoConstructor)
                        )
                        .appendDerivacion(
                                Derivacion.create(IDENTIFICADOR_DE_METODO_O_VARIABLE)
                                        .appendEstado(this::accesoVarOMetodo)
                        )
                        .build());
    }

    private NoTerminal expresionParentizada(){
        return Objects.requireNonNullElse(expresionParentizada,
                expresionParentizada = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PARENTESIS_ABRE)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_ABRE))
                                        .appendEstado(this::expresion)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_CIERRA))
                        )
                        .build());
    }

    private NoTerminal accesoVarOMetodo(){
        return Objects.requireNonNullElse(accesoVarOMetodo,
                accesoVarOMetodo = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(IDENTIFICADOR_DE_METODO_O_VARIABLE)
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_METODO_O_VARIABLE))
                                        .appendEstado(this::accesoVarOMetodoAux)
                        )
                        .build());
    }

    private NoTerminal accesoVarOMetodoAux(){
        return Objects.requireNonNullElse(accesoVarOMetodoAux,
                accesoVarOMetodoAux = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PARENTESIS_ABRE)
                                        .appendEstado(this::argsActuales)
                        )
                        .appendSiguientes(PUNTO, ASIGNACION, INCREMENTOR, DECREMENTOR, PUNTO_Y_COMA, OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO, PARENTESIS_CIERRA, COMA)
                        .build());
    }

    private NoTerminal accesoThis(){
        return Objects.requireNonNullElse(accesoThis,
                accesoThis = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(THIS_PR)
                                        .appendEstado(terminales.getTerminal(THIS_PR))
                        )
                        .build());
    }

    private NoTerminal accesoConstructor(){
        return Objects.requireNonNullElse(accesoConstructor,
                accesoConstructor = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(NEW_PR)
                                        .appendEstado(terminales.getTerminal(NEW_PR))
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_CLASE))
                                        .appendEstado(this::argsActuales)
                        )
                        .build());
    }

    private NoTerminal argsActuales(){
        return Objects.requireNonNullElse(argsActuales,
                argsActuales = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PARENTESIS_ABRE)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_ABRE))
                                        .appendEstado(this::listaExpsOVacio)
                                        .appendEstado(terminales.getTerminal(PARENTESIS_CIERRA))
                        )
                        .build());
    }

    private NoTerminal listaExpsOVacio(){
        return Objects.requireNonNullElse(listaExpsOVacio,
                listaExpsOVacio = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::listaExps)
                        )
                        .appendSiguientes(PARENTESIS_CIERRA)
                        .build());
    }

    private NoTerminal listaExps(){
        return Objects.requireNonNullElse(listaExps,
                listaExps = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(SUMA, RESTA, NEGACION, NULL_PR, TRUE_PR, FALSE_PR, ENTERO, CARACTER, STRING, THIS_PR, NEW_PR, IDENTIFICADOR_DE_METODO_O_VARIABLE, PARENTESIS_ABRE)
                                        .appendEstado(this::expresion)
                                        .appendEstado(this::listaExpsAux)
                        )
                        .build());
    }

    private NoTerminal listaExpsAux(){
        return Objects.requireNonNullElse(listaExpsAux,
                listaExpsAux = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(COMA)
                                        .appendEstado(terminales.getTerminal(COMA))
                                        .appendEstado(this::expresion)
                                        .appendEstadoRecursivo()
                        )
                        .appendSiguientes(PARENTESIS_CIERRA)
                        .build());
    }

    private NoTerminal encadenado(){
        return Objects.requireNonNullElse(encadenado,
                encadenado = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PUNTO)
                                        .appendEstado(this::varOMetodoEncadenado)
                                        .appendEstadoRecursivo()
                        )
                        .appendSiguientes(ASIGNACION, INCREMENTOR, DECREMENTOR, PUNTO_Y_COMA, OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO, PARENTESIS_CIERRA, COMA)
                        .build());
    }

    private NoTerminal varOMetodoEncadenado(){
        return Objects.requireNonNullElse(varOMetodoEncadenado,
                varOMetodoEncadenado = NoTerminal
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PUNTO)
                                        .appendEstado(terminales.getTerminal(PUNTO))
                                        .appendEstado(terminales.getTerminal(IDENTIFICADOR_DE_METODO_O_VARIABLE))
                                        .appendEstado(this::varOMetodoEncadenadoAux)
                        )
                        .build());
    }

    private NoTerminal varOMetodoEncadenadoAux(){
        return Objects.requireNonNullElse(varOMetodoEncadenadoAux,
                varOMetodoEncadenadoAux = NoTerminalConEpsilon
                        .create()
                        .appendDerivacion(
                                Derivacion.create(PARENTESIS_ABRE)
                                        .appendEstado(this::argsActuales)
                        )
                        .appendSiguientes(PUNTO, ASIGNACION, INCREMENTOR, DECREMENTOR, PUNTO_Y_COMA, OR, AND, COMPARACION, DISTINTO, MENOR, MAYOR, MENOR_IGUAL, MAYOR_IGUAL, SUMA, RESTA, PRODUCTO, DIVISION, MODULO, PARENTESIS_CIERRA, COMA)
                        .build());
    }

}
