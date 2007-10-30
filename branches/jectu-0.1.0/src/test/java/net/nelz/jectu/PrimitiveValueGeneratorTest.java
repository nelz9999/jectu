package net.nelz.jectu;

import static org.testng.AssertJUnit.*;
import org.testng.annotations.*;

public class PrimitiveValueGeneratorTest {

	@Test
	public void shouldGenerateBooleans() {

		boolean [] result = Jectu.PrimitiveValueGenerator.generateBooleanValues();
		assertEquals(2, result.length);
		assertTrue(result[0] != result[1]);
	}

	@Test
	public void shouldGenerateBytes() {
		byte [] result = Jectu.PrimitiveValueGenerator.generateByteValues();
		assertEquals(2, result.length);
		assertTrue(result[0] != result[1]);
	}
	
	@Test
	public void shouldGenerateShorts() {
		short [] result = Jectu.PrimitiveValueGenerator.generateShortValues();
		assertEquals(2, result.length);
		assertTrue(result[0] != result[1]);
	}
	
	@Test
	public void shouldGenerateInts() {
		int [] result = Jectu.PrimitiveValueGenerator.generateIntValues();
		assertEquals(2, result.length);
		assertTrue(result[0] != result[1]);
	}
	
	@Test
	public void shouldGenerateLongs() {
		long [] result = Jectu.PrimitiveValueGenerator.generateLongValues();
		assertEquals(2, result.length);
		assertTrue(result[0] != result[1]);
	}
	
	@Test
	public void shouldGenerateChars() {
		char [] result = Jectu.PrimitiveValueGenerator.generateCharValues();
		assertEquals(2, result.length);
		assertTrue(result[0] != result[1]);
	}
	
	@Test
	public void shouldGenerateFloats() {
		float [] result = Jectu.PrimitiveValueGenerator.generateFloatValues();
		assertEquals(2, result.length);
		assertTrue(result[0] != result[1]);
	}
	
	@Test
	public void shouldGenerateDoubles() {
		double [] result = Jectu.PrimitiveValueGenerator.generateDoubleValues();
		assertEquals(2, result.length);
		assertTrue(result[0] != result[1]);
	}
}
