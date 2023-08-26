package abyssus.nebel.spiral.spiraltests;

import org.junit.jupiter.api.Assertions;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

class Tester {

// static fields

	private static final Integer MINIMUM = Integer.MIN_VALUE;

// nested classes

	private record ThreadElems (
		BitSet itemValues,
		int itemCount
	) {} // ThreadElems

// static methods

	public static void testStatistics (
		final ThreadStatistics[] totalStatistics
	) { // method body
		final Map<Integer, ThreadElems> riMap = formRIMap(totalStatistics);
		for (final ThreadStatistics threadStatistics : totalStatistics) {
			processThreadStatistics(threadStatistics, riMap);
		} // for
		verifyItemCount(riMap);
	} // testStatistics()

	private static Map<Integer, ThreadElems> formRIMap (
		final ThreadStatistics[] statistics
	) { // method body
		final Map<Integer, ThreadElems> riMap = new HashMap<Integer, ThreadElems>(statistics.length);
		for (final ThreadStatistics thread : statistics) {
			final int itemCount = thread.getProducedElems();
			final BitSet valueSet = new BitSet(itemCount);
			final ThreadElems receivedItems = new ThreadElems(valueSet, itemCount);
			riMap.put(thread.getThreadId(), receivedItems);
		} // for
		return riMap;
	} // formRIMap()

	private static void processThreadStatistics (
		final ThreadStatistics statistics,
		final Map<Integer, ThreadElems> riMap
	) { // method body
		final ElemType expectedType = oppositeType(statistics.getProductionType());
		final Map<Integer, Integer> orderMap = new HashMap<Integer, Integer>();
		for (final Element elem : statistics.getStorage()) {
			final int producer = elem.producerId();
			final int value = elem.value();
			Assertions.assertSame(expectedType, elem.type());
			final Integer lastValue = orderMap.put(producer, value);
			if (lastValue != null) {
				Assertions.assertTrue(lastValue < value);
			} // if
			final BitSet values = riMap.get(producer).itemValues();
			Assertions.assertFalse(values.get(value));
			values.set(value);
		} // for
	} // processThreadStatistics()

	private static void verifyItemCount (
		final Map<Integer, ThreadElems> riMap
	) { // method body
		for (final ThreadElems items : riMap.values()) {
			Assertions.assertEquals(items.itemCount(), items.itemValues().cardinality());
		} // for
	} // verifyItemCount()

	private static ElemType oppositeType (
		final ElemType type
	) { // method body
		return (type == ElemType.ELEMENT) ? ElemType.REQUEST : ElemType.ELEMENT;
	} // oppositeType()

} // Tester
