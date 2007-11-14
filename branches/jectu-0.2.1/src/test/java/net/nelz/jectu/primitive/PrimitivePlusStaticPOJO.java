package net.nelz.jectu.primitive;

import org.apache.commons.lang.builder.*;

public class PrimitivePlusStaticPOJO {
	private boolean testBoolean;
	private byte testByte;
	private short testShort;
	private int testInt;
	private long testLong;
	private char testChar;
	private float testFloat;
	private double testDouble;
	private Object testObject;
	public boolean isTestBoolean() {
		return testBoolean;
	}
	public void setTestBoolean(boolean testBoolean) {
		this.testBoolean = testBoolean;
	}
	public byte getTestByte() {
		return testByte;
	}
	public void setTestByte(byte testByte) {
		this.testByte = testByte;
	}
	public short getTestShort() {
		return testShort;
	}
	public void setTestShort(short testShort) {
		this.testShort = testShort;
	}
	public int getTestInt() {
		return testInt;
	}
	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}
	public long getTestLong() {
		return testLong;
	}
	public void setTestLong(long testLong) {
		this.testLong = testLong;
	}
	public char getTestChar() {
		return testChar;
	}
	public void setTestChar(char testChar) {
		this.testChar = testChar;
	}
	public float getTestFloat() {
		return testFloat;
	}
	public void setTestFloat(float testFloat) {
		this.testFloat = testFloat;
	}
	public double getTestDouble() {
		return testDouble;
	}
	public void setTestDouble(double testDouble) {
		this.testDouble = testDouble;
	}
	public Object getTestObject() {
		return testObject;
	}
	public void setTestObject(Object testObject) {
		this.testObject = testObject;
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
