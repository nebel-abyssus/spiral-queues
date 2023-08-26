package abyssus.nebel.spiral.spiraltests;

import abyssus.nebel.spiral.Spiral;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class ThreadAggregator {

// static fields

	private static final int AGGREGATOR_THREAD_ID = -1;
	private static final int MAX_CHUNK_SIZE = 32;

// instance fields

	private Synchronizer synchronizer;
	private Spiral<Element, Element> spiral;
	private AbstractThread[] threads;
	private ThreadStatistics[] result;

// static methods

	private static ThreadStatistics remainderProcessing (
		final Spiral<Element, Element> spiral,
		final int threadId
	) { // method body
		final ThreadStatistics statistics = new ThreadStatistics(threadId, ElemType.ELEMENT);
		final ElementSupplier supplier = new ElementSupplier(ElemType.ELEMENT, threadId);
		Element answer;
		while ((answer = spiral.enqueue(supplier.get())) != null) {
			statistics.addElement(answer);
		} // while
		return statistics;
	} // remainderProcessing()

// constructors

	public ThreadAggregator (
		final int supplierThreads,
		final int consumerThreads,
		final int elementCount
	) { // method body
		if (!((supplierThreads > 0)
			&& (consumerThreads > 0)
			&& (elementCount >= 0)
		)) throw new IllegalArgumentException();
		spiral = new Spiral<>();
		synchronizer = new Synchronizer(
			new ReentrantLock(),
			new Signal(),
			new AtomicInteger(),
			new AtomicInteger(supplierThreads),
			new AtomicInteger(consumerThreads)
		); // synchronizer
		threads = new AbstractThread[supplierThreads + consumerThreads];
		for (int i = 0; i < supplierThreads; i++) {
			threads[i] = new SupplierThread(synchronizer, spiral, i, elementCount, MAX_CHUNK_SIZE);
		} // for
		for (int i = supplierThreads; i < (supplierThreads + consumerThreads); i++) {
			threads[i] = new ConsumerThread(synchronizer, spiral, i);
		} // for
	} // ThreadAggregator()

// instance methods

	public void run (
	) { // method body
		if (result != null) throw new IllegalStateException();
		runThreads();
		prepareResults();
		cleanup();
	} // run()

	public ThreadStatistics[] getResults (
	) { // method body
		if (result == null) throw new IllegalStateException();
		return result;
	} // getResults()

	private void runThreads (
	) { // method body
		synchronizer.endSignal().lock().lock();
		try {
			synchronizer.startLock().lock();
			try {
				for (int i = 0; i < threads.length; i++) {
					new Thread(threads[i]).start();
				} // for
			} finally {
				synchronizer.startLock().unlock();
			} // try
			while (synchronizer.consumerCount().getPlain() > 0) {
				synchronizer.endSignal().condition().awaitUninterruptibly();
			} // while
		} finally {
			synchronizer.endSignal().lock().unlock();
		} // try
	} // runThreads()

	private void prepareResults (
	) { // method body
		result = new ThreadStatistics[threads.length + 1];
		int totalElements = 0;
		int totalRequests = 0;
		for (int i = 0; i < threads.length; i++) {
			final ThreadStatistics statistics = threads[i].getStatistics();
			result[i] = statistics;
			switch (statistics.getProductionType()) {
				case ELEMENT -> totalElements += statistics.getProducedElems();
				case REQUEST -> totalRequests += statistics.getProducedElems();
			} // switch
		} // for
		result[threads.length] = remainderProcessing(spiral, AGGREGATOR_THREAD_ID);
	} // prepareResult()

	private void cleanup (
	) { // method body
		synchronizer = null;
		spiral = null;
		Arrays.fill(threads, null);
		threads = null;
	} // cleanup()

	// todo
} // ThreadAggregator
