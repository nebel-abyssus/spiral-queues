package abyssus.nebel.spiral.spiraltests;

import abyssus.nebel.spiral.Spiral;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

abstract class AbstractThread implements Runnable {

// instance fields

	private boolean isStarted;

	private final Synchronizer synchronizer;
	private final ElementSupplier elementSupplier;
	private final ThreadStatistics statistics;

	private final Spiral<Element, Element> spiral;

// constructors

	protected AbstractThread (
		final Synchronizer synchronizer,
		final Spiral<Element, Element> spiral,
		final int threadId,
		final ElemType productionType
	) { // method body
		this.synchronizer = Objects.<Synchronizer>requireNonNull(synchronizer);
		this.spiral = Objects.<Spiral<Element, Element>>requireNonNull(spiral);
		this.elementSupplier = new ElementSupplier(productionType, threadId);
		this.statistics = new ThreadStatistics(threadId, productionType);
	} // AbstractThread()

// instance methods

	public ThreadStatistics getStatistics (
	) { // method body
		statistics.setProducedElems(elementSupplier.getSuppliedElementsCount() - 1);
		return statistics;
	} // getStatistics()

	protected void startState (
	) { // method body
		if (isStarted) throw new IllegalStateException();
		isStarted = true;
	} // startState()

	protected Synchronizer getSynchronizer (
	) { // method body
		return synchronizer;
	} // getSynchronizer()

	protected Element getNextElem (
	) { // method body
		return elementSupplier.get();
	} // getNextElem()

	protected void addToStatistics (
		final Element elem
	) { // method body
		statistics.addElement(elem);
	} // addToStatistics()

	protected Spiral<Element, Element> spiral (
	) { // method body
		return spiral;
	} // spiral()

} // AbstractThread
