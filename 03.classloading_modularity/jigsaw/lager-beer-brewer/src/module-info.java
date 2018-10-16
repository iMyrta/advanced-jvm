module lager.beer.brewer {
	// in order to import the service interfaces.
	requires brewing.api;
	// this specifies that we provide the given service with the given implementation.
	// anyone module which uses the given interface can see our service, even if 
	// it doesn't declare an explicit dependency.
	provides brewing.factory.api.BrewingService with brewing.factory.lager.LagerBrewingService;
}