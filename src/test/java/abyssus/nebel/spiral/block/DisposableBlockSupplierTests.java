package abyssus.nebel.spiral.block;

import abyssus.nebel.spiral.converter.IIndexConverter;
import abyssus.nebel.spiral.converter.IdentityConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class DisposableBlockSupplierTests {

// static fields

	private static final int MIN_BLOCK_SIZE = 1;
	private static final int MAX_BLOCK_SIZE = 2048;

	private static final Random rng = new Random();

// instance methods

	@Test
	public void constructorThrowsTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, (MAX_BLOCK_SIZE + 1));
		final IIndexConverter validConverter = new IdentityConverter(blockSize);
		Assertions.assertThrows(NullPointerException.class, (() -> new DisposableBlockSupplier<String>(null)));
		Assertions.assertDoesNotThrow(() -> new DisposableBlockSupplier<String>(validConverter));
	} // constructorThrowsTest()

	@Test
	public void getTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, (MAX_BLOCK_SIZE + 1));
		final IIndexConverter converter = new IdentityConverter(blockSize);
		final DisposableBlockSupplier<Thread> supplier = new DisposableBlockSupplier<Thread>(converter);
		final IBlock<Thread> block = supplier.get();
		Assertions.assertEquals(blockSize, block.getBlockSize());
	} // getTest()

	@Test
	public void setConverterThrowsTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, (MAX_BLOCK_SIZE + 1));
		final IIndexConverter validConverter = new IdentityConverter(blockSize);
		final IIndexConverter anotherConverter = new IdentityConverter(blockSize);
		final DisposableBlockSupplier<Long> supplier = new DisposableBlockSupplier<Long>(validConverter);
		Assertions.assertThrows(NullPointerException.class, (() -> supplier.setConverter(null)));
		Assertions.assertDoesNotThrow(() -> supplier.setConverter(anotherConverter));
	} // setConverterThrowsTest()

	@Test
	public void setConverterTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, (MAX_BLOCK_SIZE + 1));
		final IIndexConverter converter = new IdentityConverter(blockSize);
		final int anotherSize = blockSize - rng.nextInt(blockSize);
		final IIndexConverter anotherConverter = new IdentityConverter(anotherSize);
		final DisposableBlockSupplier<Object> supplier = new DisposableBlockSupplier<Object>(converter);
		supplier.setConverter(anotherConverter);
		final IBlock<? extends Object> block = supplier.get();
		Assertions.assertEquals(anotherSize, block.getBlockSize());
	} // setConverterTest()

} // DisposableBlockSupplierTests
