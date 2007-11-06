package net.nelz.jectu;

import java.lang.reflect.*;
import java.security.*;
import java.util.*;

import org.apache.commons.lang.math.*;

/**
 * <i><b>JECTU</b> - The <b>J</b>ava <b>E</b>quals <b>C</b>ontract <b>T</b>esting <b>U</b>tuility</i>
 * <br/><br />
 * As explained in the JDK documentation for <code>Object.equals(java.lang.Object)</code>, 
 * there is a general contract that any overridden version of <code>equals(...)</code> must
 * adhere to:
 * <p>
 * The <code>equals</code> method implements an equivalence relation
 * on non-null object references:
 * <ul>
 * <li>It is <i>reflexive</i>: for any non-null reference value
 *     <code>x</code>, <code>x.equals(x)</code> should return
 *     <code>true</code>.
 * <li>It is <i>symmetric</i>: for any non-null reference values
 *     <code>x</code> and <code>y</code>, <code>x.equals(y)</code>
 *
 *     should return <code>true</code> if and only if
 *     <code>y.equals(x)</code> returns <code>true</code>.
 * <li>It is <i>transitive</i>: for any non-null reference values
 *     <code>x</code>, <code>y</code>, and <code>z</code>, if
 *     <code>x.equals(y)</code> returns <code>true</code> and
 *     <code>y.equals(z)</code> returns <code>true</code>, then
 *     <code>x.equals(z)</code> should return <code>true</code>.
 * <li>It is <i>consistent</i>: for any non-null reference values
 *     <code>x</code> and <code>y</code>, multiple invocations of
 *     <tt>x.equals(y)</tt> consistently return <code>true</code>
 *
 *     or consistently return <code>false</code>, provided no
 *     information used in <code>equals</code> comparisons on the
 *     objects is modified.
 * <li>For any non-null reference value <code>x</code>,
 *     <code>x.equals(null)</code> should return <code>false</code>.
 * </ul>
 * <p>
 * Note that it is generally necessary to override the <tt>hashCode</tt>
 * method whenever this method is overridden, so as to maintain the
 * general contract for the <tt>hashCode</tt> method, which states
 * that equal objects must have equal hash codes.
 * <br /><br />
 * Additionally, there is a general contract for overriding implementations of <code>
 * Object.hashCode()</code>:
 * <ul>
 * <li>Whenever it is invoked on the same object more than once during 
 *     an execution of a Java application, the <tt>hashCode</tt> method 
 *     must consistently return the same integer, provided no information 
 *     used in <tt>equals</tt> comparisons on the object is modified.
 *     This integer need not remain consistent from one execution of an
 *     application to another execution of the same application. 
 * <li>If two objects are equal according to the <tt>equals(Object)</tt>
 *     method, then calling the <code>hashCode</code> method on each of 
 *     the two objects must produce the same integer result. 
 * <li>It is <em>not</em> required that if two objects are unequal 
 *     according to the <CODE>equals(java.lang.Object)</CODE></A> 
 *     method, then calling the <tt>hashCode</tt> method on each of the 
 *     two objects must produce distinct integer results.  However, the 
 *     programmer should be aware that producing distinct integer results 
 *     for unequal objects may improve the performance of hashtables.
 * </ul>
 * <br /><br />
 * This utility aims at testing these conventions.
 * 
 */
public final class Jectu {
	
	@SuppressWarnings("unchecked")
	private Class classUnderTest;
	private Object exampleX;
	private Object exampleY;
	private Object exampleZ;
	
	private boolean effectiveByDefault = true;
	private boolean defaultEffectivenessSet = false;
	
	private StackTraceElement[] stackTrace;
	
	private Set<Field> effectiveFields = new HashSet<Field>();
	private Set<String> effectiveFieldNames = new HashSet<String>();
	private Set<Field> ineffectiveFields = new HashSet<Field>();
	private Set<String> ineffectiveFieldNames = new HashSet<String>();
	private Set<Field> ignoredFields = new HashSet<Field>();
	private Set<String> ignoredFieldNames = new HashSet<String>(); 
	
	static final String FAILURE_CHARACTERISTIC = "; Failure Characteristic: ";
	static final String REFLEXIVITY = FAILURE_CHARACTERISTIC + "Refelxivity";
	static final String SYMMETRY = FAILURE_CHARACTERISTIC + "Symmetry";
	static final String TRANSITIVITY = FAILURE_CHARACTERISTIC + "Transitivity";
	static final String CONSISTENCY =  FAILURE_CHARACTERISTIC + "Consistency";
	static final String NULL_SENSITIVITY =  FAILURE_CHARACTERISTIC + "Null Sensitivity";
	static final String HASHCODE_CONSISTENCY =  FAILURE_CHARACTERISTIC + "Hashcode Consistency";
	
	/**
	 * Create an instance of Jectu that will test the given class.  (The class must have
	 * a no-parameter constructor available.)
	 * @param clazz The class under test.
	 */
	@SuppressWarnings("unchecked")
	public Jectu(final Class clazz) {
		classUnderTest = clazz;
	}
	
	/**
	 * This method will reflexively find all the members of the class and incrementally
	 * test each member's effects upon the equality of the object.
	 * <br />
	 * There are three groupings of fields that this utility makes: effective fields, ineffective
	 * fields, and ignored fields.
	 * <br />
	 * Effective fields are those that are currently testable, and are expected to contribute
	 * to an <code>equals(...)</code> and <code>hashCode()</code> calculation.
	 * <br />
	 * Ineffective fields are those that are currently testable, and are <b>not</b> expected to
	 * contribute to an <code>equals(...)</code> and <code>hashCode()</code> calculation.
	 * <br />
	 * Ignored fields are those that are not currently testable.  The types of fields that are
	 * not currently testable are:
	 * non-primitive types, including arrays
	 * <br />
	 * This utility cannot guarantee that the hashCode is well implemented, however it will be 
	 * able to tell you whether or not it correctly agrees with the equals method.
	 */
	@SuppressWarnings("unchecked")
	public void execute() {
		this.performStackTraceMagic(new AssertionError());
		
		this.createObjectsFromClass(classUnderTest);
		
		// TODO: Make this a conditional assumption?
		final String start = "Fresh instances of this class are not equal.";
		this.testEquality(start);
		
		this.processFields();
		
		for (Field field : effectiveFields) {
			this.testEffectiveField(field);
		}
	}
	
	/**
	 * Add the name of a field that should not be ignored, but should be tested to 
	 * ensure it does have an effect upon the <code>equals(...)</code> and 
	 * <code>hashcode()</code> methods.
	 * @param fieldName
	 * @return this instance of Jectu.  (This will allow method chaining.)
	 */
	public Jectu addEffectiveFieldName(final String fieldName) {
		if (effectiveFieldNames.contains(fieldName) ||
				ineffectiveFieldNames.contains(fieldName) ||
				ignoredFieldNames.contains(fieldName)) {
			throw new IllegalStateException("Field '" + fieldName + 
					"' has already been described, and may not be more than one of the " +
					"following: Effective, Ineffective, or Ignored.");
		}
		effectiveFieldNames.add(fieldName);
		return this;
	}
	
	/**
	 * Add the name of a field that should not be ignored, but should be tested to 
	 * ensure it does not have an effect upon the <code>equals(...)</code> and 
	 * <code>hashcode()</code> methods.
	 * @param fieldName
	 * @return this instance of Jectu.  (This will allow method chaining.)
	 */
	public Jectu addIneffectiveFieldName(final String fieldName) {
		if (effectiveFieldNames.contains(fieldName) ||
				ineffectiveFieldNames.contains(fieldName) ||
				ignoredFieldNames.contains(fieldName)) {
			throw new IllegalStateException("Field '" + fieldName + 
					"' has already been described, and may not be more than one of the " +
					"following: Effective, Ineffective, or Ignored.");
		}
		ineffectiveFieldNames.add(fieldName);
		return this;
	}
	
	/**
	 * Add the name of a field that should be ignored during this test.  
	 * (The default ignored fields are those that don't fit the currently 
	 * testable types.)
	 * @param fieldName
	 * @return this instance of Jectu.  (This will allow method chaining.)
	 */
	public Jectu addIgnoredFieldName(final String fieldName) {
		if (effectiveFieldNames.contains(fieldName) ||
				ineffectiveFieldNames.contains(fieldName) ||
				ignoredFieldNames.contains(fieldName)) {
			throw new IllegalStateException("Field '" + fieldName + 
					"' has already been described, and may not be more than one of the " +
					"following: Effective, Ineffective, or Ignored.");
		}
		ignoredFieldNames.add(fieldName);
		return this;
	}
	
	boolean isEffectiveByDefault() {
		return effectiveByDefault;
	}

	public Jectu setEffectiveByDefault(boolean effectiveByDefault) {
		if (this.defaultEffectivenessSet) {
			throw new IllegalStateException("EffectiveByDefault already set.");
		}
		this.effectiveByDefault = effectiveByDefault;
		this.defaultEffectivenessSet = true;
		return this;
	}

	@SuppressWarnings("unchecked")
	void createObjectsFromClass(final Class clazz) {
		try {
			exampleX = clazz.newInstance();
			exampleY = clazz.newInstance();
			exampleZ = clazz.newInstance();
			classUnderTest = clazz;
		} catch (Exception ex) {
			throw new RuntimeException("Unable to instantiate: " + clazz.getCanonicalName(), ex);
		} 
	}
	
	void processFields() {
		final Field[] tempArray = classUnderTest.getDeclaredFields();
		AccessibleObject.setAccessible(tempArray, true);
		final List<Field> fields = Arrays.asList(tempArray);
		// Example borrowed from org.apache.commons.lang.builder.EqualsBuilder.java
		//        if (!excludedFieldList.contains(f.getName())
		//                && (f.getName().indexOf('$') == -1)
		//                && (useTransients || !Modifier.isTransient(f.getModifiers()))
		//                && (!Modifier.isStatic(f.getModifiers()))) {
		for (final Field field : fields) {
	        if ((field.getName().indexOf('$') != -1)
	                || (Modifier.isStatic(field.getModifiers()))
	                || !field.getType().isPrimitive()
	                || ignoredFieldNames.contains(field.getName())) {
				ignoredFields.add(field);
	        } else if (effectiveFieldNames.contains(field.getName())) {
	        	effectiveFields.add(field);	        	
	        } else if (ineffectiveFieldNames.contains(field.getName())){
	        	ineffectiveFields.add(field);
	        } else if (effectiveByDefault) {
	        	effectiveFields.add(field);	        		        	
	        } else {
	        	ineffectiveFields.add(field);
	        }
		}
	}
	
	void testEffectiveField(final Field field) {
		if (field == null) {
			throw new InvalidParameterException("Field must be defined.");
		}
		final StringBuffer base = new StringBuffer("Field \"" + field.getName() + "\"");
		final StringBuffer error1 = new StringBuffer(base);
		error1.append(" does not contribute to inequality as expected");
		final StringBuffer error2 = new StringBuffer(base);
		error2.append(" does not contribute to equality as expected");
		
		this.setFieldsUnequal(field);
		this.testInequality(error1.toString());
		
		this.setFieldsEqual(field);
		this.testEquality(error2.toString());
	}
	
	@SuppressWarnings("unchecked")
	void setFieldsUnequal(final Field field) {
		if (field == null) {
			throw new InvalidParameterException("Field must be defined.");
		}
		try {
			final String typeName = field.getType().getName();
			if (typeName.equals(Boolean.TYPE.getName())) {
				boolean [] vals = PrimitiveValueGenerator.generateBooleanValues();
				field.setBoolean(exampleX, vals[0]);
				field.setBoolean(exampleY, vals[1]);
				field.setBoolean(exampleZ, vals[1]); //TODO: Necessary?  Different value than Y?
			} else if (typeName.equals(Byte.TYPE.getName())) {
				byte [] vals = PrimitiveValueGenerator.generateByteValues();
				field.setByte(exampleX, vals[0]);
				field.setByte(exampleY, vals[1]);
				field.setByte(exampleZ, vals[1]); //TODO: Necessary?  Different value than Y?
			} else if (typeName.equals(Short.TYPE.getName())) {
				short [] vals = PrimitiveValueGenerator.generateShortValues();
				field.setShort(exampleX, vals[0]);
				field.setShort(exampleY, vals[1]);
				field.setShort(exampleZ, vals[1]); //TODO: Necessary?  Different value than Y?
			} else if (typeName.equals(Integer.TYPE.getName())) {
				int [] vals = PrimitiveValueGenerator.generateIntValues();
				field.setInt(exampleX, vals[0]);
				field.setInt(exampleY, vals[1]);
				field.setInt(exampleZ, vals[1]); //TODO: Necessary?  Different value than Y?
			} else if (typeName.equals(Long.TYPE.getName())) {
				long [] vals = PrimitiveValueGenerator.generateLongValues();
				field.setLong(exampleX, vals[0]);
				field.setLong(exampleY, vals[1]);
				field.setLong(exampleZ, vals[1]); //TODO: Necessary?  Different value than Y?
			} else if (typeName.equals(Character.TYPE.getName())) {
				char [] vals = PrimitiveValueGenerator.generateCharValues();
				field.setChar(exampleX, vals[0]);
				field.setChar(exampleY, vals[1]);
				field.setChar(exampleZ, vals[1]); //TODO: Necessary?  Different value than Y?
			} else if (typeName.equals(Float.TYPE.getName())) {
				float [] vals = PrimitiveValueGenerator.generateFloatValues();
				field.setFloat(exampleX, vals[0]);
				field.setFloat(exampleY, vals[1]);
				field.setFloat(exampleZ, vals[1]); //TODO: Necessary?  Different value than Y?
			} else if (typeName.equals(Double.TYPE.getName())) {
				double [] vals = PrimitiveValueGenerator.generateDoubleValues();
				field.setDouble(exampleX, vals[0]);
				field.setDouble(exampleY, vals[1]);
				field.setDouble(exampleZ, vals[1]); //TODO: Necessary?  Different value than Y?
			} else {
				throw new RuntimeException("Unable to set the property for field: " + field.getName());
			}
		} catch (IllegalAccessException ex) {
			throw new RuntimeException("There was a problem when setting property: " +
					field.getName(), ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	void setFieldsEqual(final Field field) {
		if (field == null) {
			throw new InvalidParameterException("Field must be defined.");
		}
		try {
			final String typeName = field.getType().getName();
			if (typeName.equals(Boolean.TYPE.getName())) {
				boolean [] vals = PrimitiveValueGenerator.generateBooleanValues();
				field.setBoolean(exampleX, vals[0]);
				field.setBoolean(exampleY, vals[0]);
				field.setBoolean(exampleZ, vals[0]); 
			} else if (typeName.equals(Byte.TYPE.getName())) {
				byte [] vals = PrimitiveValueGenerator.generateByteValues();
				field.setByte(exampleX, vals[0]);
				field.setByte(exampleY, vals[0]);
				field.setByte(exampleZ, vals[0]);
			} else if (typeName.equals(Short.TYPE.getName())) {
				short [] vals = PrimitiveValueGenerator.generateShortValues();
				field.setShort(exampleX, vals[0]);
				field.setShort(exampleY, vals[0]);
				field.setShort(exampleZ, vals[0]);
			} else if (typeName.equals(Integer.TYPE.getName())) {
				int [] vals = PrimitiveValueGenerator.generateIntValues();
				field.setInt(exampleX, vals[0]);
				field.setInt(exampleY, vals[0]);
				field.setInt(exampleZ, vals[0]);
			} else if (typeName.equals(Long.TYPE.getName())) {
				long [] vals = PrimitiveValueGenerator.generateLongValues();
				field.setLong(exampleX, vals[0]);
				field.setLong(exampleY, vals[0]);
				field.setLong(exampleZ, vals[0]);
			} else if (typeName.equals(Character.TYPE.getName())) {
				char [] vals = PrimitiveValueGenerator.generateCharValues();
				field.setChar(exampleX, vals[0]);
				field.setChar(exampleY, vals[0]);
				field.setChar(exampleZ, vals[0]); 
			} else if (typeName.equals(Float.TYPE.getName())) {
				float [] vals = PrimitiveValueGenerator.generateFloatValues();
				field.setFloat(exampleX, vals[0]);
				field.setFloat(exampleY, vals[0]);
				field.setFloat(exampleZ, vals[0]); 
			} else if (typeName.equals(Double.TYPE.getName())) {
				double [] vals = PrimitiveValueGenerator.generateDoubleValues();
				field.setDouble(exampleX, vals[0]);
				field.setDouble(exampleY, vals[0]);
				field.setDouble(exampleZ, vals[0]);
			} else {
				throw new RuntimeException("Unable to set the property for field: " + field.getName());
			}
		} catch (IllegalAccessException ex) {
			throw new RuntimeException("There was a problem when setting property: " +
					field.getName(), ex);
		}
	}

	
	void testEquality(final String message) {
		this.testNullSensitive(message);
		this.testReflexive(message);
		this.testSymmetricEquality(message);
		this.testTransitiveEquality(message);
		this.testConsistentEquality(message);
		this.testHashcodeConsistentEquality(message);
	}
	
	void testInequality(final String message) {
		this.testNullSensitive(message);
		this.testReflexive(message);
		this.testSymmetricInequality(message);
		// TODO: Is this logically true and testable?
		//this.testTransitiveInequality(message);
		this.testConsistentInequality(message);
		// TODO: Maybe offer an ability to trend the hashcode result, and 
		// error out if it looks like the hashcode is always returning
		// the same response.
		//this.testHashcodeInequality(message);
	}
 
	void testReflexive(final String message) {
		/*
		 * Test Reflexivity
		 */
		AssertJectu.assertTrue(stackTrace, message + REFLEXIVITY, exampleX.equals(exampleX));
		AssertJectu.assertTrue(stackTrace, message + REFLEXIVITY, exampleY.equals(exampleY));
		AssertJectu.assertTrue(stackTrace, message + REFLEXIVITY, exampleZ.equals(exampleZ));
	}
	
	void testSymmetricEquality(final String message) {	
		/*
		 * Test Symmetry
		 */
		AssertJectu.assertTrue(stackTrace, message + SYMMETRY, exampleX.equals(exampleY));
		AssertJectu.assertTrue(stackTrace, message + SYMMETRY, exampleY.equals(exampleX));
	}
	
	void testSymmetricInequality(final String message) {	
		/*
		 * Test Symmetry
		 */
		AssertJectu.assertTrue(stackTrace, message + SYMMETRY, !exampleX.equals(exampleY));
		AssertJectu.assertTrue(stackTrace, message + SYMMETRY, !exampleY.equals(exampleX));
	}

	void testTransitiveEquality(final String message) {	
		/*
		 * Test Transitivity
		 */
		AssertJectu.assertTrue(stackTrace, message + TRANSITIVITY, exampleX.equals(exampleY));
		AssertJectu.assertTrue(stackTrace, message + TRANSITIVITY, exampleY.equals(exampleZ));
		AssertJectu.assertTrue(stackTrace, message + TRANSITIVITY, exampleZ.equals(exampleX));
	}
	
	void testConsistentEquality(final String message) {	
		/*
		 * Test Consistency
		 */
		AssertJectu.assertTrue(stackTrace, message + CONSISTENCY, exampleX.equals(exampleY));
		AssertJectu.assertTrue(stackTrace, message + CONSISTENCY, exampleX.equals(exampleY));
	}
	
	void testConsistentInequality(final String message) {	
		/*
		 * Test Consistency
		 */
		AssertJectu.assertTrue(stackTrace, message + CONSISTENCY, !exampleX.equals(exampleY));
		AssertJectu.assertTrue(stackTrace, message + CONSISTENCY, !exampleX.equals(exampleY));
	}

	void testNullSensitive(final String message) {	
		/*
		 * Test Null Sensitivity
		 */
		AssertJectu.assertTrue(stackTrace, message + NULL_SENSITIVITY, !exampleX.equals(null));
		AssertJectu.assertTrue(stackTrace, message + NULL_SENSITIVITY, !exampleY.equals(null));
		AssertJectu.assertTrue(stackTrace, message + NULL_SENSITIVITY, !exampleZ.equals(null));
	}

	
	void testHashcodeConsistentEquality(final String message) {	
		/*
		 * Test Hashcode Consistency
		 */
		AssertJectu.assertEquals(stackTrace, 
									message + HASHCODE_CONSISTENCY, 
									exampleX.hashCode(), 
									exampleY.hashCode());		
		AssertJectu.assertEquals(stackTrace, 
									message + HASHCODE_CONSISTENCY, 
									exampleX.hashCode(), 
									exampleX.hashCode());		
	}

	@SuppressWarnings("unchecked")
	void performStackTraceMagic(final AssertionError ae) {
		ArrayList<StackTraceElement> elements = new ArrayList(Arrays.asList(ae.getStackTrace()));
		elements.remove(0);
		stackTrace = elements.toArray(new StackTraceElement[elements.size()]);
	}
	
	Object getExampleX() {
		return exampleX;
	}

	void setExampleX(Object exampleX) {
		this.exampleX = exampleX;
	}

	Object getExampleY() {
		return exampleY;
	}

	void setExampleY(Object exampleY) {
		this.exampleY = exampleY;
	}

	Object getExampleZ() {
		return exampleZ;
	}

	void setExampleZ(Object exampleZ) {
		this.exampleZ = exampleZ;
	}

	@SuppressWarnings("unchecked")
	Class getClassUnderTest() {
		return classUnderTest;
	}

	@SuppressWarnings("unchecked")
	void setClassUnderTest(Class classUnderTest) {
		this.classUnderTest = classUnderTest;
	}

	Set<Field> getEffectiveFields() {
		return effectiveFields;
	}

	void setEffectiveFields(Set<Field> effectiveFields) {
		this.effectiveFields = effectiveFields;
	}

	Set<Field> getIneffectiveFields() {
		return ineffectiveFields;
	}

	void setIneffectiveFields(Set<Field> ineffectiveFields) {
		this.ineffectiveFields = ineffectiveFields;
	}

	Set<Field> getIgnoredFields() {
		return ignoredFields;
	}

	void setIgnoredFields(Set<Field> ignoredFields) {
		this.ignoredFields = ignoredFields;
	}

	static final class AssertJectu {
		
		public static void assertEquals(final StackTraceElement[] stackTrace, 
										final String message, 
										final int lhs, 
										final int rhs) {
			if (lhs != rhs) {
				throwError(stackTrace, message);
			}			
		}

		public static void assertTrue(final StackTraceElement[] stackTrace, 
				final String message, 
				final boolean result) {
			if (result == false) {
				throwError(stackTrace, message);
			}
		}
		
		private static void throwError(final StackTraceElement[] stackTrace, final String message) {
			final AssertionError ae = new AssertionError(message);
			if (stackTrace != null) {
				ae.setStackTrace(stackTrace);
			}
			throw ae;
		}
	}
	
	static final class PrimitiveValueGenerator {

		static boolean[] generateBooleanValues() {
			final boolean[] result = new boolean[2];
			result[0] = RandomUtils.nextBoolean();
			result[1] = !result[0];
			return result;
		}
		
		static byte[] generateByteValues() {
			byte[] result = new byte[2];
			result[0] = (byte) (RandomUtils.nextInt(120) + 1);
			result[1] = (byte) (RandomUtils.nextInt(120) + 1);
			
			if (result[0] == result[1]) {
				result = generateByteValues();
			}
			
			return result;
		}
		
		static short[] generateShortValues() {
			short[] result = new short[2];
			result[0] = (short) (RandomUtils.nextInt(32000) + 1);
			result[1] = (short) (RandomUtils.nextInt(32000) + 1);
			
			if (result[0] == result[1]) {
				result = generateShortValues();
			}
			
			return result;
		}
		
		static int[] generateIntValues() {
			int[] result = new int[2];
			result[0] = RandomUtils.nextInt();
			result[1] = RandomUtils.nextInt();
			
			if (result[0] == result[1]) {
				result = generateIntValues();
			}
			
			return result;
		}

		static long[] generateLongValues() {
			long[] result = new long[2];
			result[0] = RandomUtils.nextLong();
			result[1] = RandomUtils.nextLong();
			
			if (result[0] == result[1]) {
				result = generateLongValues();
			}
			
			return result;
		}
		
		static char[] generateCharValues() {
			char[] result = new char[2];
			result[0] = (char) (RandomUtils.nextInt(65000) + 1);
			result[1] = (char) (RandomUtils.nextInt(65000) + 1);
			
			if (result[0] == result[1]) {
				result = generateCharValues();
			}
			
			return result;
		}

		static float[] generateFloatValues() {
			float[] result = new float[2];
			result[0] = RandomUtils.nextFloat();
			result[1] = RandomUtils.nextFloat();
			
			if (result[0] == result[1]) {
				result = generateFloatValues();
			}
			
			return result;
		}

		static double[] generateDoubleValues() {
			double[] result = new double[2];
			result[0] = RandomUtils.nextDouble();
			result[1] = RandomUtils.nextDouble();
			
			if (result[0] == result[1]) {
				result = generateDoubleValues();
			}
			
			return result;
		}
	}

}
