:- use_module(terminalNames).
:- use_module(reglas).
:- use_module(core).

:- dynamic derivNumber/1.




mostrarSiguientesAux([]):- !.

mostrarSiguientesAux([S | []]):-
	mostrarTokenName(S), !.
	
mostrarSiguientesAux([S | Ss]):-
	mostrarTokenName(S),
	write(', '),
	mostrarSiguientesAux(Ss).

mostrarSiguientes(NT):-
	tieneSiguientes(NT, Siguientes),
	!,
	write('List<String> siguientes = List.of('),
	mostrarSiguientesAux(Siguientes),
	write(');'), nl.
	
mostrarSiguientes(_).




mostrarTokenName(t('epsilon')):- !.

mostrarTokenName(t(T)):-
	terminalName(t(T), Name),
	write(Name).

mostrarTokenName(nt(NT)):-
	write(NT).
	
	

controlarEOFSiTieneEpsilon(NT):-
	primeros(NT, Primeros),
	member(t('epsilon'), Primeros),
	write('if(!currentToken.getTokenName().equals(EOF))'),
	!.

controlarEOFSiTieneEpsilon(_).


mostrarElse(NT):-
	controlarEOFSiTieneEpsilon(NT),
	write('{'),
	nl,
	write('throw new SyntaxException(currentToken, tokensExpected);'),
	nl,
	write('}').
	

tieneSiguientes(NT, Siguientes):-
	primeros(NT, Primeros),
	member(t('epsilon'), Primeros),
	siguientes(NT, Siguientes),
	Siguientes \= [].

mostrarIfSiguientes(NT):-
	tieneSiguientes(NT, _),
	!,
	write('if(siguientes.contains(currentToken.getTokenName())){'),
	nl,
	nl,
	write('} else ').
	
mostrarIfSiguientes(_).




mostrarEstado(nt(NT)):-
	write(NT), write('();'),
	nl, !.
	
mostrarEstado(t('epsilon')):- !.

mostrarEstado(t(T)):-
	write('match('),
	mostrarTokenName(t(T)),
	write(');'),
	nl.
	
mostrarEstadosEnDeriv([]):- !.

mostrarEstadosEnDeriv([E | Es]):- 
	mostrarEstado(E),
	mostrarEstadosEnDeriv(Es).
	




mostrarDeriv([t('epsilon')]):- !.

mostrarDeriv(Deriv):-
	write('if(primerosDerivacion'),
	derivNumber(N),
	write(N),
	write('.contains(currentToken.getTokenName())){'),
	nl,
	mostrarEstadosEnDeriv(Deriv),
	write('} else ').

	
	



mostrarTokensExpectedPorDeriv(N):-
	derivNumber(CantDerivNumber),
	N is CantDerivNumber+1,
	!.
	
mostrarTokensExpectedPorDeriv(I):-
	write('tokensExpected.addAll(primerosDerivacion'),
	write(I),
	write(');'),
	nl,
	ISig is I+1,
	mostrarTokensExpectedPorDeriv(ISig).
	
mostrarTokensExpectedSiguientes(NT):-
	tieneSiguientes(NT, _),
	!,
	write('tokensExpected.addAll(siguientes);'),
	nl.
	
mostrarTokensExpectedSiguientes(_).
	
mostrarTokensExpected(NT):-
	write('List<String> tokensExpected = new ArrayList<>();'),
	nl,
	mostrarTokensExpectedPorDeriv(1),
	mostrarTokensExpectedSiguientes(NT).
	




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


agregarPrimerosDeDeriv([t('epsilon')]):-
	!,
	retract(derivNumber(N)),
	NAnt is N-1,
	asserta(derivNumber(NAnt)).

agregarPrimerosDeDeriv(Deriv):-
	write('List<String> primerosDerivacion'),
	derivNumber(N),
	write(N),
	write(' = List.of('),
	mostrarPrimerosDeriv(Deriv),
	write(');'),
	nl.



aumentarDerivNumber:-
	retract(derivNumber(N)),
	NSig is N+1,
	asserta(derivNumber(NSig)).
	
reiniciarDerivNumber:-
	retract(derivNumber(_)),
	asserta(derivNumber(0)).



generate_method(nt(NT)):-
	write('private void '),
	write(NT),
	write('() throws LexicalException, SyntaxException {'), nl,
	foreach(regla(NT, Deriv), (aumentarDerivNumber, agregarPrimerosDeDeriv(Deriv))),
	mostrarSiguientes(nt(NT)),
	nl,
	mostrarTokensExpected(nt(NT)),
	nl,
	reiniciarDerivNumber,
	foreach(regla(NT, Deriv), (aumentarDerivNumber, mostrarDeriv(Deriv))),
	mostrarIfSiguientes(nt(NT)),
	mostrarElse(nt(NT)),
	nl,
	write('}'),
	reiniciarDerivNumber.
	
	
	
generate_all_methods:-
	asserta(derivNumber(0)),
	findall(NT, regla(NT, _), NTs),
	list_to_set(NTs, NTsSet),
	foreach(member(NT, NTsSet), (generate_method(nt(NT)), nl, nl)).
	

	