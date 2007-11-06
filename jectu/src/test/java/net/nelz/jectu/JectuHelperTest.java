package net.nelz.jectu;

import static org.testng.AssertJUnit.*;

import java.lang.reflect.*;
import java.util.*;

import net.nelz.jectu.helper.*;
import net.nelz.jectu.primitive.*;

import org.apache.commons.lang.*;
import org.testng.annotations.*;

public class JectuHelperTest {

	private Jectu jectu;
	private String randomString;
	
	@BeforeMethod
	public void setup() {
		jectu = new Jectu(Object.class);
		randomString = RandomStringUtils.randomAlphanumeric(20);
	}
	
	@Test
	public void shouldGetRuntimeException() {
		try {
			jectu.createObjectsFromClass(Double.class);
			fail("Expected RuntimeException.");
		} catch (RuntimeException ex) {
			assertTrue(true);
		}
	}
	
	@Test
	public void shouldInstantiateClasses() {
		jectu.createObjectsFromClass(StringBuffer.class);
		
		// Check Object type by using a Cast to StringBuffer
		StringBuffer xx = (StringBuffer) jectu.getExampleX();
		StringBuffer yy = (StringBuffer) jectu.getExampleY();
		StringBuffer zz = (StringBuffer) jectu.getExampleZ();
	}

	@Test
	public void shouldFailReflexivity() {
		jectu.setExampleX(new ProgrammableEqualsPOJO(true));
		jectu.setExampleY(new ProgrammableEqualsPOJO(true));
		jectu.setExampleZ(new ProgrammableEqualsPOJO(false));
		
		try {
			jectu.testReflexive(randomString);
			fail("Expected Exception");
		} catch (AssertionError ex) {
			assertTrue(ex.getMessage().indexOf(randomString) != -1);
			assertTrue(ex.getMessage().indexOf(Jectu.REFLEXIVITY) != -1);
		}
	}

	@Test
	public void shouldFailSymmetry() {
		jectu.setExampleX(new ProgrammableEqualsPOJO(true));
		jectu.setExampleY(new ProgrammableEqualsPOJO(false));
		jectu.setExampleZ(new ProgrammableEqualsPOJO(false));
		
		try {
			jectu.testSymmetricEquality(randomString);
			fail("Expected Exception");
		} catch (AssertionError ex) {
			assertTrue(ex.getMessage().indexOf(randomString) != -1);
			assertTrue(ex.getMessage().indexOf(Jectu.SYMMETRY) != -1);
		}
	}

	@Test
	public void shouldFailSymmetryInequality() {
		jectu.setExampleX(new ProgrammableEqualsPOJO(false));
		jectu.setExampleY(new ProgrammableEqualsPOJO(true));
		jectu.setExampleZ(new ProgrammableEqualsPOJO(false));
		
		try {
			jectu.testSymmetricInequality(randomString);
			fail("Expected Exception");
		} catch (AssertionError ex) {
			assertTrue(ex.getMessage().indexOf(randomString) != -1);
			assertTrue(ex.getMessage().indexOf(Jectu.SYMMETRY) != -1);
		}
	}

	@Test
	public void shouldFailTransitivity() {
		jectu.setExampleX(new ProgrammableEqualsPOJO(true));
		jectu.setExampleY(new ProgrammableEqualsPOJO(true));
		jectu.setExampleZ(new ProgrammableEqualsPOJO(false));
		
		try {
			jectu.testTransitiveEquality(randomString);
			fail("Expected Exception");
		} catch (AssertionError ex) {
			assertTrue(ex.getMessage().indexOf(randomString) != -1);
			assertTrue(ex.getMessage().indexOf(Jectu.TRANSITIVITY) != -1);
		}
	}

	@Test
	public void shouldFailInconsistency() {
		jectu.setExampleX(new InconsistentEqualsPOJO(1));
		jectu.setExampleY(new ProgrammableEqualsPOJO(true));
		jectu.setExampleZ(new ProgrammableEqualsPOJO(false));
		
		try {
			jectu.testConsistentEquality(randomString);
			fail("Expected Exception");
		} catch (AssertionError ex) {
			assertTrue(ex.getMessage().indexOf(randomString) != -1);
			assertTrue(ex.getMessage().indexOf(Jectu.CONSISTENCY) != -1);
		}
	}


	@Test
	public void shouldFailInconsistencyUnequal() {
		jectu.setExampleX(new ProgrammableEqualsPOJO(true));
		jectu.setExampleY(new ProgrammableEqualsPOJO(true));
		jectu.setExampleZ(new ProgrammableEqualsPOJO(false));
		
		try {
			jectu.testConsistentInequality(randomString);
			fail("Expected Exception");
		} catch (AssertionError ex) {
			assertTrue(ex.getMessage().indexOf(randomString) != -1);
			assertTrue(ex.getMessage().indexOf(Jectu.CONSISTENCY) != -1);
		}
	}

	@Test
	public void shouldFailNullSensitivity() {
		jectu.setExampleX(new ProgrammableEqualsPOJO(false));
		jectu.setExampleY(new ProgrammableEqualsPOJO(false));
		jectu.setExampleZ(new ProgrammableEqualsPOJO(true));
		
		try {
			jectu.testNullSensitive(randomString);
			fail("Expected Exception");
		} catch (AssertionError ex) {
			assertTrue(ex.getMessage().indexOf(randomString) != -1);
			assertTrue(ex.getMessage().indexOf(Jectu.NULL_SENSITIVITY) != -1);
		}
	}

	@Test
	public void shouldFailHashcodeConsistency() {
		jectu.setExampleX(new ProgrammableHashCodePOJO(17));
		jectu.setExampleY(new ProgrammableHashCodePOJO(23));
		
		try {
			jectu.testHashcodeConsistentEquality(randomString);
			fail("Expected Exception");
		} catch (AssertionError ex) {
			assertTrue(ex.getMessage().indexOf(randomString) != -1);
			assertTrue(ex.getMessage().indexOf(Jectu.HASHCODE_CONSISTENCY) != -1);
		}
	}
	
	@Test
	public void shouldProcessFieldsNaturally() {
		jectu.setClassUnderTest(PrimitivePlusObjectPOJO.class);
		jectu.processFields();
		
		Set<Field> effective = jectu.getEffectiveFields();
		Set<Field> ineffective = jectu.getIneffectiveFields();
		Set<Field> ignored = jectu.getIgnoredFields();
		
		assertEquals(8, effective.size());
		assertEquals(0, ineffective.size());
		assertEquals(1, ignored.size());
	}
	
	@Test
	public void shouldProcessFieldsWithIneffective() {
		jectu.setClassUnderTest(PrimitivePlusObjectPOJO.class);
		jectu.addIneffectiveFieldName("testDouble");
		jectu.processFields();
		
		Set<Field> effective = jectu.getEffectiveFields();
		Set<Field> ineffective = jectu.getIneffectiveFields();
		Set<Field> ignored = jectu.getIgnoredFields();
		
		assertEquals(7, effective.size());
		assertEquals(1, ineffective.size());
		assertEquals(1, ignored.size());
		
		final Field field = ineffective.iterator().next();
		assertEquals("testDouble", field.getName());
	}
	
	@Test
	public void shouldProcessFieldsWithIgnored() {
		jectu.setClassUnderTest(PrimitivePlusObjectPOJO.class);
		jectu.addIgnoredFieldName("testDouble");
		jectu.processFields();
		
		Set<Field> effective = jectu.getEffectiveFields();
		Set<Field> ineffective = jectu.getIneffectiveFields();
		Set<Field> ignored = jectu.getIgnoredFields();
		
		assertEquals(7, effective.size());
		assertEquals(0, ineffective.size());
		assertEquals(2, ignored.size());		
	}

	@Test
	public void shouldProcessFieldsWithIgnoredObjectTrump() {
		jectu.setClassUnderTest(PrimitivePlusObjectPOJO.class);
		
		// This Object should get ignored, not ineffective'd
		jectu.addIneffectiveFieldName("testObject");
		
		// This primitive field should get ineffective'd
		jectu.addIneffectiveFieldName("testByte");
		jectu.processFields();
		
		Set<Field> effective = jectu.getEffectiveFields();
		Set<Field> ineffective = jectu.getIneffectiveFields();
		Set<Field> ignored = jectu.getIgnoredFields();
		
		assertEquals(7, effective.size());
		assertEquals(1, ineffective.size());
		assertEquals(1, ignored.size());		
	}
	
	@Test
	public void shouldMakeEffectivenessProgrammable() {
		assertTrue("Should be effective by default",jectu.isEffectiveByDefault());
		
		jectu.setEffectiveByDefault(false);
		assertFalse(jectu.isEffectiveByDefault());
		
		try {
			jectu.setEffectiveByDefault(false);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(true);
		}
	}
	
	@Test
	public void shouldCheckListsForEffective() {
		final String n1 = RandomStringUtils.randomAlphabetic(10);
		final String n2 = RandomStringUtils.randomAlphabetic(11);
		final String n3 = RandomStringUtils.randomAlphabetic(12);
		
		jectu.addIneffectiveFieldName(n1);
		try {
			jectu.addEffectiveFieldName(n1);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf(n1) != -1);
		}

		jectu.addIgnoredFieldName(n2);
		try {
			jectu.addEffectiveFieldName(n2);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf(n2) != -1);
		}

		jectu.addEffectiveFieldName(n3);
		try {
			jectu.addEffectiveFieldName(n3);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf(n3) != -1);
		}

	}
	
	@Test
	public void shouldCheckListsForIneffective() {
		final String n1 = RandomStringUtils.randomAlphabetic(10);
		final String n2 = RandomStringUtils.randomAlphabetic(11);
		final String n3 = RandomStringUtils.randomAlphabetic(12);

		jectu.addEffectiveFieldName(n1);
		try {
			jectu.addIneffectiveFieldName(n1);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf(n1) != -1);
		}

		jectu.addIgnoredFieldName(n2);
		try {
			jectu.addIneffectiveFieldName(n2);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf(n2) != -1);
		}

		jectu.addIneffectiveFieldName(n3);
		try {
			jectu.addIneffectiveFieldName(n3);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf(n3) != -1);
		}

	}
	
	@Test
	public void shouldCheckListsForIgnore() {
		final String n1 = RandomStringUtils.randomAlphabetic(10);
		final String n2 = RandomStringUtils.randomAlphabetic(11);
		final String n3 = RandomStringUtils.randomAlphabetic(12);
		
		jectu.addEffectiveFieldName(n1);
		try {
			jectu.addIgnoredFieldName(n1);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf(n1) != -1);
		}

		jectu.addIneffectiveFieldName(n2);
		try {
			jectu.addIgnoredFieldName(n2);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf(n2) != -1);
		}

		jectu.addIgnoredFieldName(n3);
		try {
			jectu.addIgnoredFieldName(n3);
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf(n3) != -1);
		}
	}
}
