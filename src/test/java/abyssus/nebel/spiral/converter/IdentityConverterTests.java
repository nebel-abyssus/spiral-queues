package abyssus.nebel.spiral.converter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdentityConverterTests {

// instance methods

	@Test
	public void constructorTest () {
		final Random rng = ThreadLocalRandom.current();
		// ---------------------------------------------------------------------------
		Assertions.assertThrows(IllegalArgumentException.class, () -> new IdentityConverter(0));
		// ---------------------------------------------------------------------------
		final int negative = rng.nextInt(Integer.MIN_VALUE, 0);
		Assertions.assertThrows(IllegalArgumentException.class, () -> new IdentityConverter(negative));
		// ---------------------------------------------------------------------------
		final int positive = rng.nextInt(Integer.MAX_VALUE) + 1;
		new IdentityConverter(positive);
	} // constructorTest()

	@Test
	public void getBlockSizeTest () {
		final Random rng = ThreadLocalRandom.current();
		final int blockSize = rng.nextInt(Integer.MAX_VALUE) + 1;
		final IdentityConverter converter = new IdentityConverter(blockSize);
		// --------------------------------------------------------------------------------------------
		Assertions.assertEquals(blockSize, converter.getBlockSize());
		Assertions.assertEquals(blockSize, converter.getBlockSize());
	} // getBlockSizeTest()

	@Test
	public void convertTest () {
		final Random rng = ThreadLocalRandom.current();
		final int blockSize = rng.nextInt(Integer.MAX_VALUE) + 1;
		final int idx = rng.nextInt(blockSize);
		final IdentityConverter converter = new IdentityConverter(blockSize);
		// ----------------------------------------------------------------------------------------
		Assertions.assertEquals(idx, converter.convert(idx));
	} // convertTest()

} // IdentityConverterTests
