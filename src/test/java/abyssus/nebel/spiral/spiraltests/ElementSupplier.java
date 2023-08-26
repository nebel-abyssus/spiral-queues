package abyssus.nebel.spiral.spiraltests;

import java.util.Objects;
import java.util.function.Supplier;

class ElementSupplier implements Supplier<Element> {

// instance fields

	private final ElemType type;
	private final int threadId;
	private int value;

// constructors

	public ElementSupplier (
		final ElemType type,
		final int threadId
	) { // method body
		this.type = Objects.<ElemType>requireNonNull(type);
		this.threadId = threadId;
	} // ElementSupplier()

// instance methods

	@Override
	public Element get (
	) { // method body
		return new Element(type, threadId, value++);
	} // get()

	public int getSuppliedElementsCount (
	) { // method body
		return value;
	} // getSuppliedElementsCount()

} // ElementSupplier
