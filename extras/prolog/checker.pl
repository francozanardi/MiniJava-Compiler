:- use_module(reglas).
:- use_module(core).

noHayInterseccionEnPrimeros(nt(NT)):-
    findall(Primeros, (
                     	 regla(NT, Derivaciones),
                         primerosDerivaciones(Derivaciones, PrimerosDeriv),
                         member(Primeros, PrimerosDeriv)
                      ),
            UnionPrimeros),
    write('Primeros: '), write(UnionPrimeros), nl,
    list_to_set(UnionPrimeros, UnionPrimeros).

buscarInterseccionEnPrimeros:-
    foreach(regla(NT, _), (write('Regla: '), write(NT), nl, noHayInterseccionEnPrimeros(nt(NT)))).

	
noHayRecursionAIzquierdaDirecta(_, []):- !.
	
noHayRecursionAIzquierdaDirecta(NT, [t('epsilon') | Ds]):-
    noHayRecursionAIzquierdaDirecta(NT, Ds).

noHayRecursionAIzquierdaDirecta(NT, [D | _]):-
    D \= NT.

buscarRecursionesAIzquierda:-
    foreach(regla(NT, D), (write('Regla: '), write(NT), nl, noHayRecursionAIzquierdaDirecta(nt(NT), D))).