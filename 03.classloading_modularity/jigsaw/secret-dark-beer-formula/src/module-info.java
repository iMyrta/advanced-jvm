module secret.dark.beer.formula {
	exports secret.formula.beer;
	// this means that any module which imports us will also be able to use
	// the classes in the beer.mathematics module, even if it doesn't 
	// require it explicitly
	requires transitive beer.mathematics;
}