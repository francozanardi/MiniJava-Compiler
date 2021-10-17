:- use_module(reglas).

writeDerivations([]):- !.

writeDerivations([nt(NT) | Ds]):-
	write('<'),
	write(NT),
	write('> '),
	writeDerivations(Ds).
	
writeDerivations([t(T) | Ds]):-
	write(T),
	write(' '),
	writeDerivations(Ds).

writeGrammarNotation(NT, D):-
	write('<'),
	write(NT),
	write('> ::= '),
	writeDerivations(D),
	nl.

getGrammarInBNFNotation:-
	foreach(regla(NT, D), writeGrammarNotation(NT, D)).