package net.nelz.jectu;

import static org.testng.AssertJUnit.*;

import java.util.*;

import net.nelz.jectu.objects.*;
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
	
	@Test
	public void shouldSucceedObject() {
		final Date date1 = new Date();
		final Date date2 = (Date) date1.clone();
		assertTrue(date1 != date2);
		assertTrue(date1.equals(date2));
		
		new Jectu(PrimitivePlusObjectPOJO.class)
			.setEffectiveByDefault(true)
			.addEffectiveField("testObject", date1, new StringBuffer(), date2)
			.execute();
	}
	
	@Test
	public void shouldSucceedObjectsAndPrimitives() {
		final Date date1 = new Date();
		final Date date2 = (Date) date1.clone();
		final Date date3 = new Date(date1.getTime() - 1000000);
		assertTrue(date1 != date2);
		assertTrue(date1.equals(date2));
		assertFalse(date1.equals(date3));

		new Jectu(ObjectsAndPrimitives.class)
			.setEffectiveByDefault(true)
			.addEffectiveField("testDate", date1, date3, date2)
			.addIneffectiveField("xBoolean")
			.addIneffectiveField("xByte")
			.addIneffectiveField("xChar")
			.addIneffectiveField("xDouble")
			.addIneffectiveField("xFloat")
			.addIneffectiveField("xInt")
			.addIneffectiveField("xLong")
			.addIneffectiveField("xShort")
			.addIneffectiveField("xDate", date1, date3)
			.addIneffectiveField("xString", "bubba", "gump")
			.execute();
	}

}
