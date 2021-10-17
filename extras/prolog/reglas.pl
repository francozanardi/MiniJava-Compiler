:- module(reglas,
	[
		regla/2
	]).


regla(inicial, [nt(listaClases)]).

% regla(listaClases, [nt(clase), nt(listaClases)]).
% regla(listaClases, [nt(clase)]).

regla(listaClases, [nt(clase), nt(listaClasesAux)]).
regla(listaClasesAux, [nt(clase), nt(listaClasesAux)]).
regla(listaClasesAux, [t('epsilon')]).

regla(clase, [t('class'), t('idclase'), nt(herencia), t('{'), nt(listaMiembros), t('}')]).
regla(herencia, [t('extends'), t('idclase')]).
regla(herencia, [t('epsilon')]).
regla(listaMiembros, [nt(miembro), nt(listaMiembros)]).
regla(listaMiembros, [t('epsilon')]).
regla(miembro, [nt(atributo)]).
regla(miembro, [nt(constructor)]).
regla(miembro, [nt(metodo)]).
regla(atributo, [nt(visibilidad), nt(tipo), nt(listaDecAtrs), t(';')]).
regla(constructor, [t('idclase'), nt(argsFormales), nt(bloque)]).
regla(metodo, [nt(formaMetodo), nt(tipoMetodo), t('idmetvar'), nt(argsFormales), nt(bloque)]).
regla(visibilidad, [t('public')]).
regla(visibilidad, [t('private')]).
regla(tipo, [nt(tipoPrimitivo)]).
regla(tipo, [t('idclase')]).
regla(tipoPrimitivo, [t('boolean')]).
regla(tipoPrimitivo, [t('char')]).
regla(tipoPrimitivo, [t('int')]).
regla(tipoPrimitivo, [t('string')]).

% regla(listaDecAtrs, [t('idmetvar')]).
% regla(listaDecAtrs, [t('idmetvar'), t(','), nt(listaDecAtrs)]).

regla(listaDecAtrs, [t('idmetvar'), nt(listaDecAtrsAux)]).
regla(listaDecAtrsAux, [t(','), t('idmetvar'), nt(listaDecAtrsAux)]).
regla(listaDecAtrsAux, [t('epsilon')]).

regla(formaMetodo, [t('static')]).
regla(formaMetodo, [t('dynamic')]).
regla(tipoMetodo, [nt(tipo)]).
regla(tipoMetodo, [t('void')]).
regla(argsFormales, [t('('), nt(listaArgsFormalesOVacio), t(')')]).
regla(listaArgsFormalesOVacio, [nt(listaArgsFormales)]).
regla(listaArgsFormalesOVacio, [t('epsilon')]).

% regla(listaArgsFormales, [nt(argFormal)]).
% regla(listaArgsFormales, [nt(argFormal), t(','), nt(listaArgsFormales)]).

regla(listaArgsFormales, [nt(argFormal), nt(listaArgsFormalesAux)]).
regla(listaArgsFormalesAux, [t(','), nt(argFormal), nt(listaArgsFormalesAux)]).
regla(listaArgsFormalesAux, [t('epsilon')]).

regla(argFormal, [nt(tipo), t('idmetvar')]).
regla(bloque, [t('{'), nt(listaSentencias), t('}')]).
regla(listaSentencias, [nt(sentencia), nt(listaSentencias)]).
regla(listaSentencias, [t('epsilon')]).
regla(sentencia, [t(';')]).
regla(sentencia, [nt(varLocal), t(';')]).
regla(sentencia, [nt(return_), t(';')]).
regla(sentencia, [nt(if_)]).
regla(sentencia, [nt(for_)]).
regla(sentencia, [nt(bloque)]).

% regla(sentencia, [nt(asignacion), t(';')]).
% regla(sentencia, [nt(llamada), t(';')]).
% regla(asignacion, [nt(acceso), nt(tipoDeAsignacion)]).
% regla(llamada, [nt(acceso)]).

regla(sentencia, [nt(asignacionOLlamada), t(';')]).
regla(asignacionOLlamada, [nt(acceso), nt(asignacionOLlamadaAux)]).
regla(asignacionOLlamadaAux, [nt(tipoDeAsignacion)]).
regla(asignacionOLlamadaAux, [t('epsilon')]).
regla(asignacion, [nt(acceso), nt(tipoDeAsignacion)]). %necesario porque es llamado en el for

regla(tipoDeAsignacion, [t('='), nt(expresion)]).
regla(tipoDeAsignacion, [t('++')]).
regla(tipoDeAsignacion, [t('--')]).

% regla(varLocal, [nt(tipo), t('idmetvar')]).
% regla(varLocal, [nt(tipo), t('idmetvar'), t('='), nt(expresion)]).

regla(varLocal, [nt(tipo), t('idmetvar'), nt(varLocalAux)]).
regla(varLocalAux, [t('='), nt(expresion)]).
regla(varLocalAux, [t('epsilon')]).

regla(return_, [t('return'), nt(expresionOVacio)]).
regla(expresionOVacio, [nt(expresion)]).
regla(expresionOVacio, [t('epsilon')]).

% regla(if_, [t('if'), t('('), nt(expresion), t(')'), nt(sentencia)]).
% regla(if_, [t('if'), t('('), nt(expresion), t(')'), nt(sentencia), t('else'), nt(sentencia)]).

regla(if_, [t('if'), t('('), nt(expresion), t(')'), nt(sentencia), nt(else_)]).
regla(else_, [t('else'), nt(sentencia)]).
regla(else_, [t('epsilon')]).

regla(for_, [t('for'), t('('), nt(varLocal), t(';'), nt(expresion), t(';'), nt(asignacion), t(')'), nt(sentencia)]).

% regla(expresion, [nt(expresion), nt(operadorBinario), nt(expresionUnaria)]).
% regla(expresion, [nt(expresionUnaria)]).

regla(expresion, [nt(expresionUnaria), nt(expresionBinaria)]).
regla(expresionBinaria, [nt(operadorBinario), nt(expresionUnaria), nt(expresionBinaria)]).
regla(expresionBinaria, [t('epsilon')]).

regla(operadorBinario, [t('||')]).
regla(operadorBinario, [t('&&')]).
regla(operadorBinario, [t('==')]).
regla(operadorBinario, [t('!=')]).
regla(operadorBinario, [t('<')]).
regla(operadorBinario, [t('>')]).
regla(operadorBinario, [t('<=')]).
regla(operadorBinario, [t('>=')]).
regla(operadorBinario, [t('+')]).
regla(operadorBinario, [t('-')]).
regla(operadorBinario, [t('*')]).
regla(operadorBinario, [t('/')]).
regla(operadorBinario, [t('%')]).
regla(expresionUnaria, [nt(operadorUnario), nt(operando)]).
regla(expresionUnaria, [nt(operando)]).
regla(operadorUnario, [t('+')]).
regla(operadorUnario, [t('-')]).
regla(operadorUnario, [t('!')]).
regla(operando, [nt(literal)]).
regla(operando, [nt(acceso)]).
regla(literal, [t('null')]).
regla(literal, [t('true')]).
regla(literal, [t('false')]).
regla(literal, [t('intliteral')]).
regla(literal, [t('charliteral')]).
regla(literal, [t('stringliteral')]).

% regla(acceso, [nt(primario), nt(encadenado)]).
% regla(acceso, [nt(casting), nt(primario), nt(encadenado)]).
% regla(casting, [t('('), t('idclase'), t(')')]).
% regla(primario, [nt(expresionParentizada)]).
% regla(primario, [nt(accesoThis)]).
% regla(primario, [nt(accesoConstructor)]).
% regla(expresionParentizada, [t('('), nt(expresion), t(')')]).

regla(acceso, [nt(primarioSinExpresionParentizada), nt(encadenado)]).
regla(acceso, [nt(castingOExpresionParentizada), nt(encadenado)]).
regla(castingOExpresionParentizada, [t('('), nt(castingOExpresionParentizadaAux)]).
regla(castingOExpresionParentizadaAux, [t('idclase'), t(')'), nt(primarioConExpresionParentizada)]). % es casting
regla(castingOExpresionParentizadaAux, [nt(expresion), t(')')]). % es expresion parentizada
regla(primarioConExpresionParentizada, [nt(expresionParentizada)]).
regla(primarioConExpresionParentizada, [nt(primarioSinExpresionParentizada)]).
regla(primarioSinExpresionParentizada, [nt(accesoThis)]).
regla(primarioSinExpresionParentizada, [nt(accesoConstructor)]).
regla(expresionParentizada, [t('('), nt(expresion), t(')')]).

% regla(primario, [nt(accesovar)]).
% regla(primario, [nt(accesometodo)]).
% regla(accesovar, [t('idmetvar')]).
% regla(accesometodo, [t('idmetvar'), nt(argsActuales)]).

regla(primarioSinExpresionParentizada, [nt(accesoVarOMetodo)]).
regla(accesoVarOMetodo, [t('idmetvar'), nt(accesoVarOMetodoAux)]).
regla(accesoVarOMetodoAux, [nt(argsActuales)]).
regla(accesoVarOMetodoAux, [t('epsilon')]).

regla(accesoThis, [t('this')]).
regla(accesoConstructor, [t('new'), t('idclase'), nt(argsActuales)]).
regla(argsActuales, [t('('), nt(listaExpsOVacio), t(')')]).
regla(listaExpsOVacio, [nt(listaExps)]).
regla(listaExpsOVacio, [t('epsilon')]).

% regla(listaExps, [nt(expresion)]).
% regla(listaExps, [nt(expresion), t(','), nt(listaExps)]).

regla(listaExps, [nt(expresion), nt(listaExpsAux)]).
regla(listaExpsAux, [t(','), nt(expresion), nt(listaExpsAux)]).
regla(listaExpsAux, [t('epsilon')]).

regla(encadenado, [nt(varOMetodoEncadenado), nt(encadenado)]).
regla(encadenado, [t('epsilon')]).

% regla(varOMetodoEncadenado, [nt(varencadenada)]).
% regla(varOMetodoEncadenado, [nt(metodoencadenado)]).
% regla(varencadenada, [t('.'), t('idmetvar')]).
% regla(metodoencadenado, [t('.'), t('idmetvar'), nt(argsActuales)]).

regla(varOMetodoEncadenado, [t('.'), t('idmetvar'), nt(varOMetodoEncadenadoAux)]).
regla(varOMetodoEncadenadoAux, [nt(argsActuales)]). %método encadenado
regla(varOMetodoEncadenadoAux, [t('epsilon')]). %var encadenado