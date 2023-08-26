package abyssus.nebel.spiral.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;
import java.util.function.IntUnaryOperator;

public class IIndexConverterOfTests {

// static fields

	private static final Random rng = new Random();

// instance methods

	@Test
	public void ofThrowsTest (
	) { // method body
		final int negativeSize = rng.nextInt(Integer.MIN_VALUE, 0);
		final int zeroSize = 0;
		final int positiveSize = rng.nextInt(Integer.MAX_VALUE) + 1;
		final IntUnaryOperator identity = IntUnaryOperator.identity();
		Assertions.assertThrows(IllegalArgumentException.class, (() -> IIndexConverter.of(negativeSize, null)));
		Assertions.assertThrows(IllegalArgumentException.class, (() -> IIndexConverter.of(zeroSize, null)));
		Assertions.assertThrows(IllegalArgumentException.class, (() -> IIndexConverter.of(negativeSize, identity)));
		Assertions.assertThrows(IllegalArgumentException.class, (() -> IIndexConverter.of(zeroSize, identity)));
		Assertions.assertThrows(NullPointerException.class, (() -> IIndexConverter.of(positiveSize, null)));
		Assertions.assertDoesNotThrow(() -> IIndexConverter.of(positiveSize, identity));
	} // ofThrowsTest()

	@Test
	public void ofConverterGetBlockSizeTest (
	) { // method body
		final int blockSize = rng.nextInt(Integer.MAX_VALUE) + 1;
		final IIndexConverter converter = IIndexConverter.of(blockSize, IntUnaryOperator.identity());
		Assertions.assertEquals(blockSize, converter.getBlockSize());
		Assertions.assertEquals(blockSize, converter.getBlockSize());
	} // ofConverterGetBlockSizeTest()

	@Test
	public void ofConverterConvertTest (
	) { // method body
		final int blockSize = rng.nextInt(Integer.MAX_VALUE) + 1;
		final int idx = rng.nextInt(blockSize);
		final IntUnaryOperator mock = Mockito.mock(IntUnaryOperator.class);
		final IIndexConverter converter = IIndexConverter.of(blockSize, mock);
		converter.convert(idx);
		Mockito.verify(mock).applyAsInt(idx);
		Mockito.verifyNoMoreInteractions(mock);
	} // ofConverterConvertTest()

} // IIndexConverterOfTests
