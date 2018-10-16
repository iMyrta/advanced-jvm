package brewing.factory.impl;

import java.util.Iterator;
import java.util.ServiceLoader;

import brewing.factory.api.BrewingService;

public class BrewingFactoryMain {

	public static void main(String[] args) {
		// look up all services implementing this interface from all resolved modules.
		ServiceLoader<BrewingService> sl = ServiceLoader.load(BrewingService.class);
		Iterator<BrewingService> iter = sl.iterator();
		while (iter.hasNext()) {
			BrewingService bs = iter.next();
			bs.registerBrewingProgressListener(new ConsolePrintingProgressListener());
			bs.start(5); // brew some beer, yeey!
		}
	}
}
