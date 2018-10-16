module ipa.brewer {
	requires brewing.api;
	provides brewing.factory.api.BrewingService with brewing.factory.ipa.IPABrewingService;
	opens brewing.factory.ipa.audit to audit;
}