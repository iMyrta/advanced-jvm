package brewing.factory.impl;

import brewing.factory.api.BrewingProgressListener;

public class ConsolePrintingProgressListener implements BrewingProgressListener {

	@Override
	public void reportProgress(int litersBreweedSoFar, String typeOfBeer) {
		System.out.println("We have brewed " + litersBreweedSoFar + " of " + typeOfBeer + " so far!");
	}

}
