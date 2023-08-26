package abyssus.nebel.spiral.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class FractalConverterTests {

// static fields

	private static final Random rng = new Random();

// instance methods

	@Test
	public void constructorThrowsTest (
	) { // method body
		Assertions.assertThrows(IllegalArgumentException.class, () -> new FractalConverter(-3));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new FractalConverter(-2));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new FractalConverter(0));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new FractalConverter(3));
		Assertions.assertDoesNotThrow(() -> new FractalConverter(1));
		Assertions.assertDoesNotThrow(() -> new FractalConverter(2));
		Assertions.assertDoesNotThrow(() -> new FractalConverter(32));
	} // constructorThrowsTest()

	@Test
	public void getBlockSizeTest (
	) { // method body
		final int blockSize = 1 << rng.nextInt(31);
		final FractalConverter converter = new FractalConverter(blockSize);
		Assertions.assertEquals(blockSize, converter.getBlockSize());
		Assertions.assertEquals(blockSize, converter.getBlockSize());
	} // getBlockSizeTest()

	@Test
	public void convertTest (
	) { // method body
		final int casesCount = 128;
		final int power = rng.nextInt(31);
		final int blockSize = 1 << power;
		final FractalConverter converter = new FractalConverter(blockSize);
		for (int i = casesCount; i > 0; i--) {
			final int idx = rng.nextInt(blockSize);
			final int expected = Integer.reverse(idx) >>> (32 - power);
			final int actual = converter.convert(idx);
			Assertions.assertEquals(expected, actual);
		} // for
	} // convertTest()

} // FractalConverterTests
