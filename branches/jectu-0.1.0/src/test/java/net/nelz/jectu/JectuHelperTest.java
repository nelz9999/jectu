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
	
//	@Test
//	public void shouldThrowLHSException() {
//		jectu.setExampleX(new ProgrammableEqualsPOJO(false));
//		jectu.setExampleY(new ProgrammableEqualsPOJO(true));
//		final String message = RandomStringUtils.randomAlphanumeric(14);
//		
//		try {
//			jectu.testEquality(message);
//			fail("Expected Exception.");
//		} catch (AssertionError er) {
//			assertTrue(er.getMessage().indexOf(message) != -1);
//			assertTrue(er.getMessage().indexOf(Jectu.EQUALS_ID) != -1);
//		} 
//	}
//	
//	@Test
//	public void shouldThrowHashCodeException() {
//		jectu.setExampleX(new ProgrammableHashCodePOJO(17));
//		jectu.setExampleY(new ProgrammableHashCodePOJO(23));
//		
//		final String message = RandomStringUtils.randomAlphanumeric(14);
//		
//		try {
//			jectu.testEquality(message);
//			fail("Expected Exception.");
//		} catch (AssertionError er) {
//			assertTrue(er.getMessage().indexOf(message) != -1);
//			assertTrue(er.getMessage().indexOf(Jectu.HASHCODE_ID) != -1);
//		}
//	}
	
//	@Test
//	public void shouldGatherMethods() {
//		List<Method> methods = jectu.getEffectiveMethods(PrimitivePOJO.class);
//		assertEquals(8, methods.size());		
//	}

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
		
		List<Field> effective = jectu.getEffectiveFields();
		List<Field> ineffective = jectu.getIneffectiveFields();
		List<Field> ignored = jectu.getIgnoredFields();
		
		assertEquals(8, effective.size());
		assertEquals(0, ineffective.size());
		assertEquals(1, ignored.size());
	}
	
	@Test
	public void shouldProcessFieldsWithIneffective() {
		jectu.setClassUnderTest(PrimitivePlusObjectPOJO.class);
		jectu.addIneffectiveFieldName("testDouble");
		jectu.processFields();
		
		List<Field> effective = jectu.getEffectiveFields();
		List<Field> ineffective = jectu.getIneffectiveFields();
		List<Field> ignored = jectu.getIgnoredFields();
		
		assertEquals(7, effective.size());
		assertEquals(1, ineffective.size());
		assertEquals(1, ignored.size());
		
		assertEquals("testDouble", ineffective.get(0).getName());
	}
	
	@Test
	public void shouldProcessFieldsWithIgnored() {
		jectu.setClassUnderTest(PrimitivePlusObjectPOJO.class);
		jectu.addIgnoredFieldName("testDouble");
		jectu.processFields();
		
		List<Field> effective = jectu.getEffectiveFields();
		List<Field> ineffective = jectu.getIneffectiveFields();
		List<Field> ignored = jectu.getIgnoredFields();
		
		assertEquals(7, effective.size());
		assertEquals(0, ineffective.size());
		assertEquals(2, ignored.size());		
	}

	@Test
	public void shouldProcessFieldsWithIneffectiveObject() {
		jectu.setClassUnderTest(PrimitivePlusObjectPOJO.class);
		jectu.addIneffectiveFieldName("testObject");
		jectu.processFields();
		
		List<Field> effective = jectu.getEffectiveFields();
		List<Field> ineffective = jectu.getIneffectiveFields();
		List<Field> ignored = jectu.getIgnoredFields();
		
		assertEquals(8, effective.size());
		assertEquals(1, ineffective.size());
		assertEquals(0, ignored.size());		
	}

//	@Test
//	public void testArrays() {
//		boolean [] [] values = {{true, true}, {false, false}};
//		Object ref = values;
//		
//		if (ref instanceof boolean[][]) {
//			System.out.println("Ref is boolean[][]");
//			Object sub = ((boolean[][])ref)[0];
//			if (sub instanceof boolean[]) {
//				System.out.println("Sub is boolean[]");				
//			} else {
//				System.out.println("Sub failed");
//			}
//		} else {
//			System.out.println("Ref failed");
//		}		
//	}
}
