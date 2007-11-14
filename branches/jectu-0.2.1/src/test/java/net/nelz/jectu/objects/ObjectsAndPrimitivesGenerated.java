package net.nelz.jectu.objects;

import java.util.*;

public class ObjectsAndPrimitivesGenerated {
	private boolean testBoolean;
	private byte testByte;
	private short testShort;
	private int testInt;
	private long testLong;
	private char testChar;
	private float testFloat;
	private double testDouble;
	private Date testDate;
	private String testString;
	private boolean xBoolean;
	private byte xByte;
	private short xShort;
	private int xInt;
	private long xLong;
	private char xChar;
	private float xFloat;
	private double xDouble;
	private Date xDate;
	private String xString;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (testBoolean ? 1231 : 1237);
		result = prime * result + testChar;
		result = prime * result
				+ ((testDate == null) ? 0 : testDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(testDouble);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Float.floatToIntBits(testFloat);
		result = prime * result + testInt;
		result = prime * result + (int) (testLong ^ (testLong >>> 32));
		result = prime * result + testShort;
		result = prime * result
				+ ((testString == null) ? 0 : testString.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ObjectsAndPrimitivesGenerated other = (ObjectsAndPrimitivesGenerated) obj;
		if (testBoolean != other.testBoolean)
			return false;
		if (testByte != other.testByte)
			return false;
		if (testChar != other.testChar)
			return false;
		if (testDate == null) {
			if (other.testDate != null)
				return false;
		} else if (!testDate.equals(other.testDate))
			return false;
		if (Double.doubleToLongBits(testDouble) != Double
				.doubleToLongBits(other.testDouble))
			return false;
		if (Float.floatToIntBits(testFloat) != Float
				.floatToIntBits(other.testFloat))
			return false;
		if (testInt != other.testInt)
			return false;
		if (testLong != other.testLong)
			return false;
		if (testShort != other.testShort)
			return false;
		if (testString == null) {
			if (other.testString != null)
				return false;
		} else if (!testString.equals(other.testString))
			return false;
		return true;
	}

	
}
