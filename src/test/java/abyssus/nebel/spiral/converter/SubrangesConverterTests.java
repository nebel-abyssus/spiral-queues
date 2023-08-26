package abyssus.nebel.spiral.converter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.ParameterizedTest;

public class SubrangesConverterTests {

// static methods

	private static Stream<Arguments> constructorTestArgumentsSupplier () {
		final int validSubrangeSize = 7;
		final int validBlockSize = 245;
		final int negativeBlockSize = -validBlockSize;
		final int negativeSubrangeSize = -validSubrangeSize;
		final int invalidBlockSize = validBlockSize + 31;
		final Class<IllegalArgumentException> exception = IllegalArgumentException.class;
		// -------------------------------------------------------------------
		return Stream.<Arguments>of(
			Arguments.of(0, validSubrangeSize, exception),
			Arguments.of(negativeBlockSize, validSubrangeSize, exception),
			Arguments.of(invalidBlockSize, validSubrangeSize, exception),
			Arguments.of(validBlockSize, 0, exception),
			Arguments.of(validBlockSize, negativeSubrangeSize, exception),
			Arguments.of(validBlockSize, validSubrangeSize, null)
		); // stream
	} // constructorTestArgumentsSupplier()

// instance methods

	@ParameterizedTest(name=ParameterizedTest.ARGUMENTS_PLACEHOLDER)
	@MethodSource("constructorTestArgumentsSupplier")
	public void constructorTest (
		final int blockSize,
		final int subrangeSize,
		final Class<? extends Throwable> exception
	) { // method body
		if (exception != null) {
			Assertions.assertThrows(exception, () -> new SubrangesConverter(blockSize, subrangeSize));
		} else {
			new SubrangesConverter(blockSize, subrangeSize);
		} // if
	} // constructorTest()

	@Test
	public void getBlockSizeTest () {
		final Random rng = ThreadLocalRandom.current();
		final int factor = rng.nextInt(2048) + 1;
		final int subrangeSize = rng.nextInt(128) + 1;
		final int blockSize = subrangeSize * factor;
		final SubrangesConverter converter = new SubrangesConverter(blockSize, subrangeSize);
		// --------------------------------------------------------------------------------------------
		Assertions.assertEquals(blockSize, converter.getBlockSize());
		Assertions.assertEquals(blockSize, converter.getBlockSize());
	} // getBlockSizeTest()

	@Test
	public void convertTest () {
		final int TESTS_COUNT = 2048;
		// ----------------------------------------------------------------------------------------------
		final Random rng = ThreadLocalRandom.current();
		final int n = rng.nextInt(2048) + 1;
		final int subrangeSize = rng.nextInt(128) + 1;
		final int blockSize = subrangeSize * n;
		final SubrangesConverter converter = new SubrangesConverter(blockSize, subrangeSize);
		// ----------------------------------------------------------------------------------------------
		for (int i = TESTS_COUNT; i > 0; i--) {
			final int idx = rng.nextInt(blockSize);
			final int j = idx / n;
			final int k = idx % n;
			final int expected = subrangeSize * k + j;
			Assertions.assertEquals(expected, converter.convert(idx));
		} // for
	} // convertTest()

} // SubrangesConverterTests
