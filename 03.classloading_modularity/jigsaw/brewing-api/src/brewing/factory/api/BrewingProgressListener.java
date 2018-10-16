package brewing.factory.api;

public interface BrewingProgressListener {

	public void reportProgress(int litersBreweedSoFar, String typeOfBeer);

}
