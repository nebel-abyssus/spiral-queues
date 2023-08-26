package abyssus.nebel.spiral.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;
import java.util.function.IntUnaryOperator;

public class LambdaConverterHelperTests {

// static fields

	private static final Random rng = new Random();

// instance methods

	@Test
	public void constructorThrowsTest (
	) { // method body
		final int negativeSize = rng.nextInt(Integer.MIN_VALUE, 0);
		final int zeroSize = 0;
		final int positiveSize = rng.nextInt(Integer.MAX_VALUE) + 1;
		final IntUnaryOperator identity = IntUnaryOperator.identity();
		Assertions.assertThrows(IllegalArgumentException.class, (() -> new LambdaConverterHelper(negativeSize, null)));
		Assertions.assertThrows(IllegalArgumentException.class, (() -> new LambdaConverterHelper(zeroSize, null)));
		Assertions.assertThrows(IllegalArgumentException.class, (() -> new LambdaConverterHelper(negativeSize, identity)));
		Assertions.assertThrows(IllegalArgumentException.class, (() -> new LambdaConverterHelper(zeroSize, identity)));
		Assertions.assertThrows(NullPointerException.class, (() -> new LambdaConverterHelper(positiveSize, null)));
		Assertions.assertDoesNotThrow(() -> new LambdaConverterHelper(positiveSize, identity));
	} // constructorThrowsTest()

	@Test
	public void getBlockSizeTest (
	) { // method body
		final int blockSize = rng.nextInt(Integer.MAX_VALUE) + 1;
		final LambdaConverterHelper converter = new LambdaConverterHelper(blockSize, IntUnaryOperator.identity());
		Assertions.assertEquals(blockSize, converter.getBlockSize());
		Assertions.assertEquals(blockSize, converter.getBlockSize());
	} // getBlockSizeTest()

	@Test
	public void convertTest (
	) { // method body
		final int blockSize = rng.nextInt(Integer.MAX_VALUE) + 1;
		final int idx = rng.nextInt(blockSize);
		final IntUnaryOperator mock = Mockito.mock(IntUnaryOperator.class);
		final LambdaConverterHelper converter = new LambdaConverterHelper(blockSize, mock);
		converter.convert(idx);
		Mockito.verify(mock).applyAsInt(idx);
		Mockito.verifyNoMoreInteractions(mock);
	} // convertTest()

} // LambdaConverterHelperTests
