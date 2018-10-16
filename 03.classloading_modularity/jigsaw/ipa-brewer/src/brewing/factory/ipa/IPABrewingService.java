package brewing.factory.ipa;

import brewing.factory.api.BrewingProgressListener;

import java.util.ArrayList;
import java.util.List;

public class IPABrewingService implements brewing.factory.api.BrewingService {

	private List<BrewingProgressListener> listenres = new ArrayList<>();

	@Override
	public void start(int litersToBrew) {
		for (int i = 0; i < litersToBrew; i++) {
			for (BrewingProgressListener listener : listenres) {
				listener.reportProgress(i, "IPA");
			}
		}
		
	}

	@Override
	public void stop() {
		System.out.println("IPA Brewing never really supported stop...");
	}

	@Override
	public void registerBrewingProgressListener(BrewingProgressListener listener) {
		listenres.add(listener);
	}

}
