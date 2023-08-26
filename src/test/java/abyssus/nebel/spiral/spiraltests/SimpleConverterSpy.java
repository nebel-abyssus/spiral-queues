package abyssus.nebel.spiral.spiraltests;

import abyssus.nebel.spiral.converter.IIndexConverter;

import java.util.Objects;

class SimpleConverterSpy implements IIndexConverter {

// instance fields

	private final IIndexConverter converter;

	private boolean hasInteractions;

// constructors

	public SimpleConverterSpy (
		final IIndexConverter converter
	) { // method body
		this.converter = Objects.<IIndexConverter>requireNonNull(converter);
	} // SimpleConverterSpy()

// instance methods

	@Override
	public int getBlockSize (
	) { // method body
		hasInteractions = true;
		return converter.getBlockSize();
	} // getBlockSize()

	@Override
	public int convert (
		final int idx
	) { // method body
		hasInteractions = true;
		return converter.convert(idx);
	} // convert()

	public boolean hasInteractions (
	) { // method body
		return hasInteractions;
	} // hasInteractions()

} // SimpleConverterSpy
