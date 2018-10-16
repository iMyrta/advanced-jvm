package secret.formula.beer;

import beer.mathematics.BeerMathResult;
import beer.mathematics.BeerParameter;

public class VerySecretDarkBeerFormula {

	public static BeerMathResult calculate(BeerParameter... parameters) {
		StringBuilder builder = new StringBuilder();
		for (BeerParameter param : parameters) {
			builder.append(param.getName()).append(":").append(param.getValue()).append("+");
		}

		builder.deleteCharAt(builder.length() - 1);
		return new BeerMathResult(builder.toString());
	}

}
