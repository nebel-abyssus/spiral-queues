package abyssus.nebel.spiral.spiraltests;

import java.util.*;

class ThreadStatistics {

// instance fields

	private final int threadId;
	private final ElemType productionType;
	private int producedElems;
	private List<Element> storage = new ArrayList<Element>();

// constructors

	public ThreadStatistics (
		final int threadId,
		final ElemType productionType
	) { // method body
		this.threadId = threadId;
		this.productionType = Objects.<ElemType>requireNonNull(productionType);
	} // ThreadStatistics()

// instance methods

	public int getThreadId (
	) { // method body
		return threadId;
	} // getThreadId()

	public ElemType getProductionType (
	) { // method body
		return productionType;
	} // getProductionType()

	public int getProducedElems (
	) { // method body
		return producedElems;
	} // getProducedElems()

	public void setProducedElems (
		final int value
	) { // method body
		producedElems = value;
	} // setProducedElems()

	public List<Element> getStorage (
	) { // method body
		return storage;
	} // getStorage()

	public void addElement (
		final Element element
	) { // method body
		storage.add(Objects.<Element>requireNonNull(element));
	} // addElement()

} // ThreadStatistics
