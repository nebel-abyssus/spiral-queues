package abyssus.nebel.spiral.utils;

import java.util.BitSet;
import java.util.Random;

public class OneTimeNumbers {

// static fields

	private static final Random rng = new Random();

// instance fields

	private final int limit;
	private int counter;
	private final BitSet usedNumbers;

// constructors

	public OneTimeNumbers (
		final int limit
	) { // method body
		if (limit <= 0) throw new IllegalArgumentException();
		this.limit = limit;
		usedNumbers = new BitSet(limit);
	} // OneTimeNumbers()

// instance methods

	public int randomNumber (
	) { // method body
		if (counter == limit) throw new IllegalStateException();
		final int num = rng.nextInt(limit);
		int result = usedNumbers.nextClearBit(num);
		if (result == limit) {
			result = usedNumbers.previousClearBit(num - 1);
		} // if
		usedNumbers.set(result);
		counter++;
		return result;
	} // randomNumber()

	public void reset (
	) { // method body
		usedNumbers.clear();
		counter = 0;
	} // reset()

} // OneTimeNumbers
