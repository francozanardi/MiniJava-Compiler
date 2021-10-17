:- module(core,
	[
		primeros/2,
		primerosDerivaciones/2,
		siguientes/2
	]).

:- use_module(reglas).

:- dynamic siguientesCalculados/1. 
% siguientesCalculados/1 se usa para guardar los no terminales cuyos siguientes ya fueron calculados.
% Esto con el fin de evitar bucles por recursividad cruzada


% Se asume en todo concatPrimeros/3 que PrimerosParcial contiene a epsilon.
concatPrimeros(PrimerosParcial, [], PrimerosParcial):- !.

% Como el restante es vaciable, entonces Primeros contiene a epsilon
concatPrimeros(PrimerosParcial, PrimerosRestantes, PrimerosConEpsilon):-
    member(t('epsilon'), PrimerosRestantes),
    !,
    append(PrimerosParcial, PrimerosRestantes, PrimerosUnion),
    list_to_set(PrimerosUnion, PrimerosConEpsilon).

% Como el restante no es vaciable, entonces Primeros no contiene a epsilon.
concatPrimeros(PrimerosParcial, PrimerosRestantes, PrimerosSinEpsilon):-
    append(PrimerosParcial, PrimerosRestantes, PrimerosUnion),
    list_to_set(PrimerosUnion, PrimerosConEpsilon),
	delete(PrimerosConEpsilon, t('epsilon'), PrimerosSinEpsilon).

primerosDerivaciones([], []):- !.
primerosDerivaciones([Derivacion | Derivaciones], Primeros):-
    primeros(Derivacion, PrimerosParcial),
    primerosDerivacionesAux(Derivaciones, PrimerosParcial, Primeros).

primerosDerivacionesAux(Derivaciones, PrimerosParcial, PrimerosFinal):-
    member(t('epsilon'), PrimerosParcial),
    !,
    primerosDerivaciones(Derivaciones, PrimerosDerivaciones),
    concatPrimeros(PrimerosParcial, PrimerosDerivaciones, PrimerosFinal).

primerosDerivacionesAux(_, PrimerosParcial, PrimerosParcial).
    


primeros(t(Terminal), [t(Terminal)]).
primeros(nt(NoTerminal), PrimerosSet):-
    findall(Primero, (
                     	 regla(NoTerminal, Derivaciones),
                         primerosDerivaciones(Derivaciones, PrimerosDeriv),
                         member(Primero, PrimerosDeriv)
                     ), Primeros),
					 
	list_to_set(Primeros, PrimerosSet).



siguientes(E, S):-
	siguientesAux(E, S),
	retractall(siguientesCalculados(_)).

% Si ya calculamos los siguientes de E entonces ya lo estamos teniendo en cuenta en el conjunto resultante.
% Por lo tanto, podemos "mentir" diciendo que "no tiene siguientes".
siguientesAux(E, []):-
	siguientesCalculados(E),
	!.
	
siguientesAux(t(_), []):- !.

siguientesAux(nt(NT), SiguientesSet):-
	asserta(siguientesCalculados(nt(NT))),
	
	findall(Siguiente, 	(
							regla(R, Deriv),
							siguientesEnDeriv(R, nt(NT), Deriv, SiguientesAux),
							member(Siguiente, SiguientesAux)
						), Siguientes),
	
	list_to_set(Siguientes, SiguientesSet).
						

siguientesEnDeriv(Regla, NT, Deriv, Siguientes):-
	regla(Regla, Deriv),
	nth0(IndexNT, Deriv, NT), % Si NT aparece más de una vez en Deriv, debería esto generar múltiples ramas.
	Sig is IndexNT+1,
	siguientesEnDerivAux(Regla, Sig, Deriv, Siguientes).
	
	
siguientesEnDerivAux(_Regla, IndexSig, Deriv, Siguientes):-
	length(Deriv, DerivLength),
	IndexSig < DerivLength,
	getLastPartInDeriv(Deriv, IndexSig, LastPartInDeriv),
	primerosDerivaciones(LastPartInDeriv, Siguientes),
	not(member(t('epsilon'), Siguientes)),
	!.
	
siguientesEnDerivAux(Regla, IndexSig, Deriv, Siguientes):-
	length(Deriv, DerivLength),
	IndexSig < DerivLength,
	getLastPartInDeriv(Deriv, IndexSig, LastPartInDeriv),
	primerosDerivaciones(LastPartInDeriv, PrimerosDeriv),
	member(t('epsilon'), PrimerosDeriv),
	!,
	siguientesAux(nt(Regla), SiguientesParent),
	concatSiguientes(PrimerosDeriv, SiguientesParent, Siguientes).

%Deriv no tiene parte final luego de NT
siguientesEnDerivAux(Regla, _, _, Siguientes):-
	siguientesAux(nt(Regla), Siguientes).
	

getLastPartInDeriv(Deriv, Index, LastPart):-
	getLastPartInDerivAux(Deriv, 0, Index, LastPart).
	
getLastPartInDerivAux(LastPart, Index, Index, LastPart):- !.

getLastPartInDerivAux([_ | Ds], CurrentIndex, IndexSearched, LastPart):-
	NewIndex is CurrentIndex + 1,
	getLastPartInDerivAux(Ds, NewIndex, IndexSearched, LastPart).

% se asume que Primeros contiene a epsilon en todo concatSiguientes/3
concatSiguientes(Primeros, [], SiguientesResult):-
	delete(Primeros, t('epsilon'), SiguientesResult),
	!.
	
concatSiguientes(Primeros, Siguientes, SiguientesResult):-
	concatPrimeros(Primeros, Siguientes, SiguientesResult).
	
	