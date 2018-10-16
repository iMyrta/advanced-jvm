module brewing.factory.impl {
	// in order to import the service interfaces.
	requires brewing.api;
	// this module consumes the beer brewing services.
	uses brewing.factory.api.BrewingService;
}