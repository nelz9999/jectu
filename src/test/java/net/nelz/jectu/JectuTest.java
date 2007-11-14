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
	
	@Test
	public void shouldIgnoreObjectAndTest() {
		new Jectu(PrimitivePlusObjectPOJO.class)
			.execute();
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

	
	@Test
	public void shouldSucceedObjectsAndPrimitivesGenerated() {
		final Date date1 = new Date();
		final Date date2 = (Date) date1.clone();
		final Date date3 = new Date(date1.getTime() - 1000000);

		new Jectu(ObjectsAndPrimitivesGenerated.class)
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
	
	@Test
	public void shouldFailFrozenIgnoredList() {
		final Date date1 = new Date();
		final Date date2 = (Date) date1.clone();
		final Date date3 = new Date(date1.getTime() - 1000000);

		final Jectu jectu = new Jectu(ObjectsAndPrimitivesAndIgnoredFields.class)
			.setEffectiveByDefault(true)
			.addEffectiveField("testDouble")
			.addEffectiveField("testDate", date1, date3, date2)
			.addIneffectiveField("xInt")
			.addIneffectiveField("xString", "shallow", "hal")
			.addIgnoredField("STATIC1")
			.freezeIgnoredFields();
		
		try {
			jectu.execute();
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf("frozen") != -1);
		}
	}

	
	@Test
	public void shouldSucceedFrozenIgnoredList() {
		final Date date1 = new Date();
		final Date date2 = (Date) date1.clone();
		final Date date3 = new Date(date1.getTime() - 1000000);

		new Jectu(ObjectsAndPrimitivesAndIgnoredFields.class)
			.setEffectiveByDefault(true)
			.addEffectiveField("testDouble")
			.addEffectiveField("testDate", date1, date3, date2)
			.addIneffectiveField("xInt")
			.addIneffectiveField("xString", "shallow", "hal")
			.addIgnoredField("STATIC1")
			.addIgnoredField("STATIC2")
			.freezeIgnoredFields()
			.execute();
	}

	@Test
	public void shouldThrowExceptionForNonNullConstructor() {
		try {
			new Jectu(MultiArgumentConstructorObject.class);
			fail("Expected Exception");
		} catch (RuntimeException ex) {
			assertTrue(ex.getMessage().indexOf("instantiate") != -1);
		}
	}
	
	@Test
	public void shouldHandleNonNullConstructor() {
		final Date date1 = new Date();
		final Date date2 = (Date) date1.clone();
		final Date date3 = new Date(date1.getTime() - 1000000);

		final MultiArgumentConstructorObject o1 = new MultiArgumentConstructorObject(date1, 4);
		final MultiArgumentConstructorObject o2 = new MultiArgumentConstructorObject(date1, 4);
		final MultiArgumentConstructorObject o3 = new MultiArgumentConstructorObject(date1, 4);
		
		new Jectu(o1, o2, o3)
			.setEffectiveByDefault(true)
			.addEffectiveField("testDouble")
			.addEffectiveField("testDate", date1, date3, date2)
			.addIneffectiveField("xInt")
			.addIneffectiveField("xString", "shallow", "hal")
			.addIgnoredField("STATIC1")
			.addIgnoredField("STATIC2")
			.freezeIgnoredFields()
			.execute();
	}
	
	@Test
	public void shouldThrowExceptionForDisparateTypes() {
		try {
			new Jectu(new String(), new String(), new Date());
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf("same type") != -1);
		}

		try {
			new Jectu(new String(), new Date(), new String());
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf("same type") != -1);
		}
	}
}
