package abyssus.nebel.spiral.spiraltests;

import org.junit.jupiter.api.Test;

public class SpiralTests {

// instance methods

	@Test
	public void spscTest (
	) { // method body
		final ThreadAggregator aggregator = new ThreadAggregator(1, 1, 32768);
		aggregator.run();
		final ThreadStatistics[] statistics = aggregator.getResults();
		Tester.testStatistics(statistics);
	} // spscTest()

	@Test
	public void mpscTest (
	) { // method body
		final ThreadAggregator aggregator = new ThreadAggregator(7, 1, 8192);
		aggregator.run();
		final ThreadStatistics[] statistics = aggregator.getResults();
		Tester.testStatistics(statistics);
	} // mpscTest()

	@Test
	public void spmcTest (
	) { // method body
		final ThreadAggregator aggregator = new ThreadAggregator(1, 7, 32768);
		aggregator.run();
		final ThreadStatistics[] statistics = aggregator.getResults();
		Tester.testStatistics(statistics);
	} // spmcTest()

	@Test
	public void mpmcTest (
	) { // method body
		final ThreadAggregator aggregator = new ThreadAggregator(4, 4, 32768);
		aggregator.run();
		final ThreadStatistics[] statistics = aggregator.getResults();
		Tester.testStatistics(statistics);
	} // mpmcTest()

} // SpiralTests
