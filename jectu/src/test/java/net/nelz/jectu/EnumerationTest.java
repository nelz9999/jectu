package net.nelz.jectu;

import static org.testng.AssertJUnit.*;
import net.nelz.jectu.objects.*;
import org.testng.annotations.*;

public class EnumerationTest {

	@Test
	public void testAnyEnum() {
		new Jectu(EnumerationContainer.class)
			.setEffectiveByDefault(true)
			.addIgnoredField("testEmptyEnum")
			.addIgnoredField("xEmptyEnum")
			.addIneffectiveField("xMultipleEnum")
			.addIneffectiveField("xSingleEnum")
			.execute();
	}

	@Test
	public void testEmtpyEnum() {
		try {
			new Jectu(EnumerationContainer.class)
				.setEffectiveByDefault(true)
				.addIgnoredField("testSingleEnum")
				.addIgnoredField("testMultipleEnum")
				.addIneffectiveField("xMultipleEnum")
				.addIneffectiveField("xSingleEnum")
				.addIneffectiveField("xEmptyEnum")
				.execute();
			fail("Expected Exception.");
		} catch (IllegalStateException ex) {
			assertTrue(true);
		}
	}
}
