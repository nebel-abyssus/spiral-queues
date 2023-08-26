package abyssus.nebel.spiral.block;

import abyssus.nebel.spiral.converter.IIndexConverter;
import abyssus.nebel.spiral.converter.IdentityConverter;
import abyssus.nebel.spiral.utils.OneTimeNumbers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class DisposableBlockTests {

// static fields

	private static final int MIN_BLOCK_SIZE = 1;
	private static final int MAX_BLOCK_SIZE = 2048;

	private static final Random rng = new Random();

// instance methods

	@Test
	public void constructorThrowsTest (
	) { // method body
		Assertions.assertThrows(NullPointerException.class, (() -> new DisposableBlock<Object>(null)));
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, (MAX_BLOCK_SIZE + 1));
		Assertions.assertDoesNotThrow(() -> new DisposableBlock<Integer>(new IdentityConverter(blockSize)));
	} // constructorThrowsTest()

	@Test
	public void getBlockSizeTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, (MAX_BLOCK_SIZE + 1));
		final IIndexConverter converter = new IdentityConverter(blockSize);
		final DisposableBlock<Object> block = new DisposableBlock<>(converter);
		Assertions.assertEquals(blockSize, block.getBlockSize());
	} // getBlockSizeTest()

	@Test
	public void getOrPutThrowsTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, (MAX_BLOCK_SIZE + 1));
		final IIndexConverter converter = new IdentityConverter(blockSize);
		final DisposableBlock<Integer> block = new DisposableBlock<>(converter);
		final int someIdx = rng.nextInt(blockSize);
		final Integer someNumber = rng.nextInt();
		Assertions.assertThrows(IndexOutOfBoundsException.class, (() -> block.getOrPut(-1, someNumber)));
		Assertions.assertThrows(IndexOutOfBoundsException.class, (() -> block.getOrPut(blockSize, someNumber)));
		Assertions.assertThrows(IndexOutOfBoundsException.class, (() -> block.getOrPut(-1, null)));
		Assertions.assertThrows(IndexOutOfBoundsException.class, (() -> block.getOrPut(blockSize, null)));
		Assertions.assertThrows(NullPointerException.class, (() -> block.getOrPut(someIdx, null)));
		Assertions.assertDoesNotThrow(() -> block.getOrPut(someIdx, someNumber));
	} // getOrPutThrowsTest()

	@Test
	public void getOrPutTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, (MAX_BLOCK_SIZE + 1));
		final Integer[] numbers = new Integer[blockSize];
		final OneTimeNumbers indexes = new OneTimeNumbers(blockSize);
		final DisposableBlock<Integer> block = new DisposableBlock<>(new IdentityConverter(blockSize));
		for (int i = blockSize; i > 0; i--) {
			final Integer someNumber = rng.nextInt();
			final int idx = indexes.randomNumber();
			numbers[idx] = someNumber;
			Assertions.assertNull(block.getOrPut(idx, someNumber));
		} // for
		indexes.reset();
		final Integer someNumber = rng.nextInt();
		for (int i = blockSize; i > 0; i--) {
			final int idx = indexes.randomNumber();
			Assertions.assertSame(numbers[idx], block.getOrPut(idx, someNumber));
		} // for
	} // getOrPutTest()

} // DisposableBlockTests
