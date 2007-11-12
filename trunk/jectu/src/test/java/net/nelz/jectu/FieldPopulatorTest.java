package net.nelz.jectu;

import static org.testng.AssertJUnit.*;
import net.nelz.jectu.Jectu.*;

import org.testng.annotations.*;

public class FieldPopulatorTest {

	private Jectu jectu;
	private IneffectiveFieldPopulator ineffective;
	private EffectiveFieldPopulator effective;
	private int integer;
	
	@BeforeMethod
	public void setup() {
		jectu = new Jectu();
		ineffective = jectu.new IneffectiveFieldPopulator();
		effective = jectu.new EffectiveFieldPopulator();
		integer = 0;
	}
	
	@Test
	public void shouldCheckField() {
		ineffective.setField(null);
		try {
			ineffective.validatePopulationObjects();
			fail("Expected Exception");
		} catch (IllegalStateException ex) {
			assertTrue(true);
		}
	}
	
	@Test
	public void shouldCalculatePrimitive() throws Exception {
		ineffective.setField(FieldPopulatorTest.class.getDeclaredField("integer"));
		ineffective.validatePopulationObjects();
		
		assertFalse(ineffective.isReferenceType());
	}
	
	@Test
	public void shouldCheckBaseObject() throws Exception {
		ineffective.setField(FieldPopulatorTest.class.getDeclaredField("jectu"));
		
		try {
			ineffective.validatePopulationObjects();
			fail("Expected Exception");			
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf("defined") != -1);
		}

		ineffective.setBaseObject(new StringBuffer());
		try {
			ineffective.validatePopulationObjects();
			fail("Expected Exception");			
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf("assignable") != -1);
			assertTrue(ex.getMessage().indexOf("baseObject") != -1);
		}
		assertTrue(ineffective.isReferenceType());
	}
	
	@Test
	public void shouldCheckUnequalObject() throws Exception {
		ineffective.setField(FieldPopulatorTest.class.getDeclaredField("jectu"));
		ineffective.setBaseObject(new Jectu());
		ineffective.setUnequalObject(new StringBuffer());
		try {
			ineffective.validatePopulationObjects();
			fail("Expected Exception");			
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf("assignable") != -1);
			assertTrue(ex.getMessage().indexOf("unequalObject") != -1);
		}
	}

	
	@Test
	public void shouldCheckPrimitiveEqualObject() throws Exception {
		effective.setField(FieldPopulatorTest.class.getDeclaredField("integer"));
		effective.validatePopulationObjects();
		assertFalse(effective.isReferenceType());
	}
	
	@Test
	public void shouldCheckEqualObject() throws Exception {
		effective.setField(FieldPopulatorTest.class.getDeclaredField("jectu"));
		effective.setBaseObject(new Jectu());
		effective.setUnequalObject(new Jectu());
		try {
			effective.validatePopulationObjects();
			fail("Expected Exception");			
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf("defined") != -1);
			assertTrue(ex.getMessage().indexOf("equalObject") != -1);
		}

		effective.setEqualObject(new StringBuffer());
		try {
			effective.validatePopulationObjects();
			fail("Expected Exception");			
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().indexOf("assignable") != -1);
			assertTrue(ex.getMessage().indexOf("equalObject") != -1);
		}

	}

}
