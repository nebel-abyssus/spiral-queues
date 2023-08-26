package abyssus.nebel.spiral;

import abyssus.nebel.spiral.block.DisposableBlock;
import abyssus.nebel.spiral.block.DisposableBlockSupplier;
import abyssus.nebel.spiral.block.IBlock;
import abyssus.nebel.spiral.converter.IIndexConverter;
import abyssus.nebel.spiral.converter.IdentityConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class UniversalHeadTests {

// static fields

	private static final Random rng = new Random();

// instance methods

	@Test
	public void constructorThrowsTest (
	) { // method body
		final AtomicInteger validIndex = new AtomicInteger();
		final int blockSize = rng.nextInt(1, 2048);
		final IIndexConverter converter = new IdentityConverter(blockSize);
		final DisposableBlockSupplier<Object> blockSupplier = new DisposableBlockSupplier<Object>(converter);
		final DisposableBlock<Object> block = blockSupplier.get();
		final LinkedNode<IBlock<Object>> validNode = new LinkedNode<IBlock<Object>>(block);
		Assertions.assertThrows(NullPointerException.class, (() -> new UniversalHead(null, null)));
		Assertions.assertThrows(NullPointerException.class, (() -> new UniversalHead(null, validNode)));
		Assertions.assertThrows(NullPointerException.class, (() -> new UniversalHead(validIndex, null)));
		Assertions.assertDoesNotThrow(() -> new UniversalHead(validIndex, validNode));
	} // constructorThrowsTest()

} // UniversalHeadTests
