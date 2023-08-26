package abyssus.nebel.spiral.spiraltests;

import abyssus.nebel.spiral.Spiral;
import abyssus.nebel.spiral.converter.IIndexConverter;
import abyssus.nebel.spiral.converter.IdentityConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class SpiralThrowsTests {

// static fields

	private static final int MIN_BLOCK_SIZE = 1;
	private static final int MAX_BLOCK_SIZE = 2048;

	private static final Random rng = new Random();

// instance methods

	@Test
	public void constructorThrowsTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, MAX_BLOCK_SIZE);
		final IIndexConverter converter = new IdentityConverter(blockSize);
		Assertions.assertThrows(NullPointerException.class, (() -> new Spiral<Integer, Integer>(null)));
		Assertions.assertDoesNotThrow(() -> new Spiral(converter));
	} // constructorThrowsTest()

	@Test
	public void setConverterThrowsTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, MAX_BLOCK_SIZE);
		final IIndexConverter converter = new IdentityConverter(blockSize);
		final Spiral<Object, Object> spiral = new Spiral<Object, Object>(new IdentityConverter(1));
		Assertions.assertThrows(NullPointerException.class, (() -> spiral.setConverter(null)));
		Assertions.assertDoesNotThrow(() -> spiral.setConverter(converter));
	} // setConverterThrowsTest()

	@Test
	public void enqueueThrowsTest (
	) { // method body
		final Spiral<Integer, Integer> spiral = new Spiral<>();
		Assertions.assertThrows(NullPointerException.class, (() -> spiral.enqueue(null)));
		Assertions.assertDoesNotThrow(() -> spiral.enqueue(rng.nextInt()));
	} // enqueueThrowsTest()

	@Test
	public void dequeueThrowsTest (
	) { // method body
		final Spiral<Integer, Integer> spiral = new Spiral<>();
		Assertions.assertThrows(NullPointerException.class, (() -> spiral.dequeue(null)));
		Assertions.assertDoesNotThrow(() -> spiral.dequeue(rng.nextInt()));
	} // dequeueThrowsTest()

} // SpiralThrowsTests
