package brewing.factory.lager;

import java.util.ArrayList;
import java.util.List;

import brewing.factory.api.BrewingProgressListener;

public class LagerBrewingService implements brewing.factory.api.BrewingService {

	private List<BrewingProgressListener> listenres = new ArrayList<>();

	@Override
	public void start(int litersToBrew) {
		for (int i = 0; i < litersToBrew; i++) {
			for (BrewingProgressListener listener : listenres) {
				listener.reportProgress(i, "Lager");
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
