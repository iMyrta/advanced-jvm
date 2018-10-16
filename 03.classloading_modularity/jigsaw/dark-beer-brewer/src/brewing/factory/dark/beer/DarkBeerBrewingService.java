package brewing.factory.dark.beer;

import java.util.ArrayList;
import java.util.List;

import beer.mathematics.BeerMathResult;
import beer.mathematics.BeerParameter;
import brewing.factory.api.BrewingProgressListener;
import brewing.factory.api.BrewingService;
import secret.formula.beer.VerySecretDarkBeerFormula;

public class DarkBeerBrewingService implements BrewingService {

	private List<BrewingProgressListener> listenres = new ArrayList<>();

	@Override
	public void start(int litersToBrew) {
		for (int i = 0; i < litersToBrew; i++) {
			// the BeerParameter and BeerMathResult classes come transitively from the
			// beer.mathematics module
			BeerParameter param1 = new BeerParameter("ingredient1", "5");
			BeerParameter param2 = new BeerParameter("ingredient2", "asMuchAsYouCan!");
			BeerMathResult result = VerySecretDarkBeerFormula.calculate(param1, param2);
			System.out.println("[DarkBeerBrewingService] formula is : " + result.getResultWrapper());
			for (BrewingProgressListener listener : listenres) {
				listener.reportProgress(i, "Dark");
			}
		}
	}

	@Override
	public void stop() {
		System.out.println("You cannot stop the brewing once it has started!!");
	}

	@Override
	public void registerBrewingProgressListener(BrewingProgressListener listener) {
		listenres.add(listener);
	}
}
