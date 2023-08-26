package abyssus.nebel.spiral.converter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbstractConverterTests {

// instance methods

	@Test
	public void constructorTest () {
		final Random rng = ThreadLocalRandom.current();
		// -------------------------------------------------------------------------------------------
		Assertions.assertThrows(IllegalArgumentException.class, () -> new AbstractConverterTestHelper(0));
		// -------------------------------------------------------------------------------------------
		final int negative = rng.nextInt(Integer.MIN_VALUE, 0);
		Assertions.assertThrows(IllegalArgumentException.class, () -> new AbstractConverterTestHelper(negative));
		// -------------------------------------------------------------------------------------------
		final int positive = rng.nextInt(Integer.MAX_VALUE) + 1;
		new AbstractConverterTestHelper(positive);
	} // constructorTest()

	@Test
	public void getBlockSizeTest () {
		final Random rng = ThreadLocalRandom.current();
		final int blockSize = rng.nextInt(Integer.MAX_VALUE) + 1;
		final AbstractConverter converter = new AbstractConverterTestHelper(blockSize);
		// ----------------------------------------------------------------------------------------------------------------------
		Assertions.assertEquals(blockSize, converter.getBlockSize());
		Assertions.assertEquals(blockSize, converter.getBlockSize());
	} // getBlockSizeTest()

} // AbstractConverterTests
