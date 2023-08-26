package abyssus.nebel.spiral.spiraltests;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

record Synchronizer (
	Lock startLock,
	Signal endSignal,
	AtomicInteger enqueuedElems,
	AtomicInteger supplierCount,
	AtomicInteger consumerCount
) { // record body

// constructors

	Synchronizer {
		Objects.requireNonNull(startLock);
		Objects.requireNonNull(endSignal);
		Objects.requireNonNull(enqueuedElems);
		Objects.requireNonNull(supplierCount);
		Objects.requireNonNull(consumerCount);
	} // Synchronizer()

} // Synchronizer
