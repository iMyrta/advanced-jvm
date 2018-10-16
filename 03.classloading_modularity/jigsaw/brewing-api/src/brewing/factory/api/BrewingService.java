package brewing.factory.api;

public interface BrewingService {

	public void start(int litersToBrew);

	public void stop();

	public void registerBrewingProgressListener(BrewingProgressListener listener);
}
