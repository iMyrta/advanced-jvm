module dark.beer.brewer {
	requires brewing.api;
	// along with this one, we get beer.mathematics transitively.
	requires secret.dark.beer.formula;
	provides brewing.factory.api.BrewingService with brewing.factory.dark.beer.DarkBeerBrewingService;
}