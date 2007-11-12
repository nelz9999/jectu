package net.nelz.jectu;

import static org.testng.AssertJUnit.*;
import net.nelz.jectu.primitive.*;

import org.testng.annotations.*;

public class JectuTest {

	@Test
	public void shouldPassPrimitivePOJO() {
		new Jectu(PrimitivePOJO.class).execute();
	}
//	
//	@Test
//	public void shouldFailPrimitiveIneffectiveDoublePOJO() {
//		try {
//			new Jectu(PrimitiveIneffectiveDoublePOJO.class).execute();
//			fail("Expected Exception.");
//		} catch (AssertionError ex) {
//			assertTrue(ex.getMessage().indexOf("testDouble") != -1);
//		}
//	}
//	
//	@Test
//	public void shouldFailPrimitiveIneffectiveBytePOJO() {
//		try {
//			new Jectu(PrimitiveIneffectiveBytePOJO.class).execute();
//			fail("Expected Exception.");
//		} catch (AssertionError ex) {
//			assertTrue(ex.getMessage(), ex.getMessage().indexOf("testByte") != -1);
//		}
//	}
//	
//	@Test
//	public void shouldSucceedPrimitivePurposefulIneffectiveBytePOJO() {
//		new Jectu(PrimitiveIneffectiveBytePOJO.class)
//				.addIneffectiveFieldName("testByte")
//				.execute();
//		new Jectu(PrimitiveIneffectiveDoublePOJO.class)
//				.addIneffectiveFieldName("testDouble")
//				.execute();
//	}
	
	@Test
	public void shouldIgnoreObjectAndTest() {
		new Jectu(PrimitivePlusObjectPOJO.class)
			.execute();
	}
//	No Longer Valid, as we can work with Arrays and Objects.
//	@Test
//	public void shouldIgnoreArray() {
//		new Jectu(PrimitivePlusPrimitiveArrayPOJO.class)
//			.execute();
//	}
	
	@Test
	public void shouldFailDisallowedIgnored() {
		try {
			new Jectu(PrimitivePlusObjectPOJO.class)
				.setAllowIgnoredFields(false)
				.execute();
		} catch (AssertionError ex) {
			assertTrue(ex.getMessage().indexOf("ignore") != -1);
		}
	}
}
