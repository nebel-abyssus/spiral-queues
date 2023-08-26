package abyssus.nebel.spiral.converter;

class AbstractConverterTestHelper extends AbstractConverter {

// constructors

	AbstractConverterTestHelper (final int blockSize) {
		super(blockSize);
	} // AbstractConverterTestHelper()

// instance methods

	@Override
	public int convert (final int idx) {
		return idx;
	} // convert()

} // AbstractConverterTestHelper
