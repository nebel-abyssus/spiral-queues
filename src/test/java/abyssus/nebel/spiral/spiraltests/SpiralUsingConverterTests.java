package abyssus.nebel.spiral.spiraltests;

import abyssus.nebel.spiral.Spiral;
import abyssus.nebel.spiral.converter.IIndexConverter;
import abyssus.nebel.spiral.converter.IdentityConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class SpiralUsingConverterTests {

// static fields

	private static final int MIN_BLOCK_SIZE = 1;
	private static final int MAX_BLOCK_SIZE = 2048;

	private static final Random rng = new Random();

// instance methods

	@Test
	public void usingByConstructorTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, MAX_BLOCK_SIZE);
		final IIndexConverter converter = new IdentityConverter(blockSize);
		final SimpleConverterSpy spy = new SimpleConverterSpy(converter);
		final Spiral<Integer, Integer> spiral = new Spiral<Integer, Integer>(spy);
		final int elemsCount = rng.nextInt(blockSize) + 1;
		for (int i = elemsCount; i > 0; i--) {
			spiral.enqueue(rng.nextInt());
		} // for
		Assertions.assertTrue(spy.hasInteractions());
	} // usingByConstructorTest()

	@Test
	public void usingBySetConverterTest (
	) { // method body
		final int blockSize = rng.nextInt(MIN_BLOCK_SIZE, MAX_BLOCK_SIZE);
		final IIndexConverter initialConverter = new IdentityConverter(blockSize);
		final IIndexConverter spyBaseConverter = new IdentityConverter(blockSize);
		final SimpleConverterSpy spy = new SimpleConverterSpy(spyBaseConverter);
		final Spiral<Integer, Object> spiral = new Spiral<Integer, Object>(initialConverter);
		final int elemsCount = rng.nextInt((blockSize + 1), (blockSize * 7));
		spiral.setConverter(spy);
		for (int i = elemsCount; i > 0; i--) {
			spiral.enqueue(rng.nextInt());
		} // for
		Assertions.assertTrue(spy.hasInteractions());
	} // usingBySetConverterTest()

} // SpiralUsingConverterTests
