:- use_module(terminalNames).
:- use_module(reglas).
:- use_module(core).


mostrarSiguientesAux([]):- !.

mostrarSiguientesAux([S | []]):-
	mostrarTokenName(S), !.
	
mostrarSiguientesAux([S | Ss]):-
	mostrarTokenName(S),
	write(', '),
	mostrarSiguientesAux(Ss).

mostrarSiguientes(NT):-
	primeros(NT, Primeros),
	member(t('epsilon'), Primeros),
	siguientes(NT, Siguientes),
	Siguientes \= [],
	!,
	write('.appendSiguientes('),
	mostrarSiguientesAux(Siguientes),
	write(')'), nl.
	
mostrarSiguientes(_).


mostrarTokenName(t('epsilon')):- !.

mostrarTokenName(t(T)):-
	terminalName(t(T), Name),
	write(Name).

mostrarTokenName(nt(NT)):-
	write(NT).

mostrarPrimerosDerivAux([]):- !.

mostrarPrimerosDerivAux([PD | []]):-
	mostrarTokenName(PD), !.
	
mostrarPrimerosDerivAux([PD | PDs]):-
	mostrarTokenName(PD),
	write(', '),
	mostrarPrimerosDerivAux(PDs).
	
mostrarPrimerosDeriv(Deriv):-
	primerosDerivaciones(Deriv, PrimerosDeriv),
	mostrarPrimerosDerivAux(PrimerosDeriv).
	
mostrarEstado(NTParent, nt(NTParent)):-
	write('.appendEstadoRecursivo()'), nl, !.
	
mostrarEstado(_, nt(NT)):-
	write('.appendEstado(this::'),
	write(NT), write(')'),
	nl, !.
	
mostrarEstado(_, t('epsilon')):- !.

mostrarEstado(_, t(T)):-
	write('.appendEstado(terminales.getTerminal('),
	mostrarTokenName(t(T)),
	write('))'),
	nl.
	
mostrarEstadosEnDeriv(_, []):- !.
mostrarEstadosEnDeriv(Parent, [E | Es]):- 
	mostrarEstado(Parent, E),
	mostrarEstadosEnDeriv(Parent, Es).
	
	
mostrarDeriv(_, [t('epsilon')]):- !.
mostrarDeriv(NT, Deriv):-
	write('.appendDerivacion('), nl,
	write('Derivacion.create('),
	mostrarPrimerosDeriv(Deriv),
	write(')'), nl,
	mostrarEstadosEnDeriv(NT, Deriv),
	write(')'), nl.


mostrarTipoDeNT(NT):-
	primeros(NT, Primeros),
	member(t('epsilon'), Primeros),
	!,
	write('NoTerminalConEpsilon').
	
mostrarTipoDeNT(_):-
	write('NoTerminal').

generate_method(nt(NT)):-
	write('private NoTerminal '),
	write(NT),
	write('(){'), nl,
	write('return Objects.requireNonNullElse('),
	write(NT), write(','), nl,
	write(NT), write(' = '),
	mostrarTipoDeNT(nt(NT)), nl,
	write('.create()'), nl,
	foreach(regla(NT, Deriv), mostrarDeriv(NT, Deriv)),
	mostrarSiguientes(nt(NT)),
	write('.build());'), nl,
	write('}').
	
generate_all_methods:-
	findall(NT, regla(NT, _), NTs),
	list_to_set(NTs, NTsSet),
	foreach(member(NT, NTsSet), (generate_method(nt(NT)), nl, nl)).
	
generate_field(nt(NT)):-
	write('private NoTerminal '),
	write(NT),
	write(';'), nl.
	
generate_all_fields:-
	findall(NT, regla(NT, _), NTs),
	list_to_set(NTs, NTsSet),
	foreach(member(NT, NTsSet), generate_field(nt(NT))).
	