package abyssus.nebel.spiral.spiraltests;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Signal {

// instance fields

	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();

// instance methods

	public ReentrantLock lock (
	) { // method body
		return lock;
	} // lock()

	public Condition condition (
	) { // method body
		return condition;
	} // condition()

} // Signal
