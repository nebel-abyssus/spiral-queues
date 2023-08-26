package abyssus.nebel.spiral.spiraltests;

import abyssus.nebel.spiral.Spiral;

import java.util.Random;

class SupplierThread extends AbstractThread {

// static fields

	private static final Random rng = new Random();

// instance fields

	private final int maxChunkSize;

	private int elementCounter;

// constructors

	public SupplierThread (
		final Synchronizer synchronizer,
		final Spiral<Element, Element> spiral,
		final int threadId,
		final int elementCount,
		final int maxChunkSize
	) { // method body
		super(synchronizer, spiral, threadId, ElemType.ELEMENT);
		if ((elementCount < 0) || (maxChunkSize < 1)) throw new IllegalArgumentException();
		this.maxChunkSize = maxChunkSize;
		this.elementCounter = elementCount;
	} // SupplierThread()

// instance methods

	@Override
	public void run (
	) { // method body
		startState();
		final Synchronizer sync = getSynchronizer();
		sync.startLock().lock();
		sync.startLock().unlock();
		Element elem = getNextElem();
		while (elementCounter > 0) {
			final int chunkSize = Math.min(elementCounter, (rng.nextInt(maxChunkSize) + 1));
			int enqueueCount = 0;
			for (int i = chunkSize; i > 0; i--) {
				Element request;
				while ((request = spiral().enqueue(elem)) != null) {
					addToStatistics(request);
					enqueueCount++;
				} // while
				enqueueCount++;
				elem = getNextElem();
			} // for
			sync.enqueuedElems().getAndAdd(enqueueCount);
			elementCounter -= chunkSize;
		} // while
		sync.supplierCount().getAndDecrement();
	} // run()

} // SupplierThread
