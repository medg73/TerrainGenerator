/**
 * 
 */
package com.medg.terraingenerator.dice;

/**
 * @author Matthew Grossman
 * @version Jan 25, 2013
 */
public class Dice {

	private RandomNumberGenerator randomNumberGenerator;

	public Dice(RandomNumberGenerator randomNumberGenerator) {
		this.randomNumberGenerator = randomNumberGenerator;
	}

	int doubleToInt(int x, double d1) {
		if(d1 <= 0.0) {
			d1 = 0.001;
		}
		d1 *= x;
		return((int)Math.ceil(d1));
	}

	/**
	 *
	 * @param quantity either "xdx" or "x"
	 * @param multiplier
	 * @return
	 */
	public int getAmount(String quantity, int multiplier) {
		int numRolls;
		int diceType;
		int rv = 0;

		String[] str = quantity.split("d");
		if(str.length == 2) {
			numRolls = Integer.parseInt(str[0]);
			diceType = Integer.parseInt(str[1]);

			for(int i = 0; i < numRolls; i++) {
				rv += (doubleToInt(diceType, randomNumberGenerator.getNum()) * multiplier);
			}
		}
		else {
			rv = Integer.parseInt(quantity);
		}

		return rv;
	}

	public int getNumInLinearRange(int min, int max) {
		int diceType = max - min + 1;
		int roll = this.getAmount("1d"+diceType, 1);
		int rv = roll + min - 1;
		return rv;
	}

	public int rollPercent() {
		return this.getAmount("1d100", 1);
	}

	public int rollD20() {
		return this.getAmount("1d20", 1);
	}

	public int rollD10() {
		return this.getAmount("1d10", 1);
	}

	public int roll1D4() {
		return this.getAmount("1d4", 1);
	}

	public int roll1D6() {
		return this.getAmount("1d6", 1);
	}

	public int roll1D8() {
		return this.getAmount("1d8", 1);
	}

	public int roll1D12() {
		return this.getAmount("1d12", 1);
	}
}
