package abyssus.nebel.spiral.spiraltests;

import abyssus.nebel.spiral.Spiral;

class ConsumerThread extends AbstractThread {

// instance fields

	private Element request = getNextElem();

// constructors

	public ConsumerThread (
		final Synchronizer synchronizer,
		final Spiral<Element, Element> spiral,
		final int threadId
	) { // method body
		super(synchronizer, spiral, threadId, ElemType.REQUEST);
	} // ConsumerThread()

// instance methods

	@Override
	public void run (
	) { // method body
		startState();
		final Synchronizer sync = getSynchronizer();
		sync.startLock().lock();
		sync.startLock().unlock();
		int maxRequests = getMaxRequests(0);
		while (maxRequests > 0) {
			final int processedRequests = processRequests(maxRequests);
			maxRequests = getMaxRequests(processedRequests);
		} // while
		final int consumers = sync.consumerCount().decrementAndGet();
		if (consumers == 0) {
			sync.endSignal().lock().lock();
			sync.endSignal().condition().signal();
			sync.endSignal().lock().unlock();
		} // if
	} // run()

	private int processRequests (
		final int maxRequests
	) { // method body
		Element answer = spiral().dequeue(request);
		int processedRequests = 1;
		while ((answer != null) && (processedRequests < maxRequests)) {
			addToStatistics(answer);
			answer = spiral().dequeue(request);
			processedRequests++;
		} // while
		if (answer != null) {
			addToStatistics(answer);
		} else {
			request = getNextElem();
		} // if
		return processedRequests;
	} // processRequests()

	private int getMaxRequests (
		final int processedRequests
	) { // method body
		final Synchronizer sync = getSynchronizer();
		int elems = sync.enqueuedElems().addAndGet(-processedRequests);
		if (elems <= 0) {
			while ((sync.supplierCount().get() > 0) && ((elems = sync.enqueuedElems().getPlain()) <= 0)) {
				Thread.yield();
			} // while
			if (elems <= 0) {
				elems = sync.enqueuedElems().getPlain();
			} // if
		} // if
		return elems;
	} // getMaxRequests()

} // ConsumerThread
