:- module(core,
	[
		primeros/2,
		primerosDerivaciones/2
	]).

:- use_module(reglas).

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