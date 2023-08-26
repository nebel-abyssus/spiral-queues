package abyssus.nebel.spiral;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkedNodeTests {

// instance methods

	@Test
	public void constructorTest () {
		Assertions.assertThrows(NullPointerException.class, () -> new LinkedNode<Object>(null));
		new LinkedNode<Object>(new Object());
	} // constructorTest()

	@Test
	public void getContentTest () {
		final Object obj = new Object();
		final LinkedNode<Object> node = new LinkedNode<Object>(obj);
		Assertions.assertSame(obj, node.getContent());
	} // getContentTest()

	@Test
	public void getNextNodeTest () {
		final LinkedNode<Object> node = new LinkedNode<Object>(new Object());
		final LinkedNode<Object> anotherNode = new LinkedNode<Object>(new Object());
		Assertions.assertNull(node.getNextNode());
		node.trySetNext(anotherNode);
		Assertions.assertSame(anotherNode, node.getNextNode());
		Assertions.assertNull(anotherNode.getNextNode());
	} // getNextNodeTest()

	@Test
	public void trySetNextTest () {
		final LinkedNode<Object> node = new LinkedNode<Object>(new Object());
		final LinkedNode<Object> anotherNode = new LinkedNode<Object>(new Object());
		final LinkedNode<Object> yetAnotherNode = new LinkedNode<Object>(new Object());
		LinkedNode<Object> result;
		// ------------------------------------------------------------------------------------
		Assertions.assertThrows(NullPointerException.class, () -> node.trySetNext(null));
		// ------------------------------------------------------------------------------------
		result = node.trySetNext(anotherNode);
		Assertions.assertSame(anotherNode, result);
		Assertions.assertSame(anotherNode, node.getNextNode());
		// ------------------------------------------------------------------------------------
		result = node.trySetNext(yetAnotherNode);
		Assertions.assertSame(anotherNode, result);
		Assertions.assertSame(anotherNode, node.getNextNode());
	} // trySetNextTest()

} // LinkedNodeTests
