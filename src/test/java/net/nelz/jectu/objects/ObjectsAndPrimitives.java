package net.nelz.jectu.objects;

import java.util.*;

import org.apache.commons.lang.builder.*;

public class ObjectsAndPrimitives {
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
	public boolean equals(Object obj) {
		final String [] ignore = new String[] {
				"xBoolean",
				"xByte",
				"xChar",
				"xDouble",
				"xFloat",
				"xInt",
				"xLong",
				"xShort",
				"xDate",
				"xString"
		};
		return EqualsBuilder.reflectionEquals(this, obj, ignore);
	}
	
	@Override
	public int hashCode() {
		final String [] ignore = new String[] {
				"xBoolean",
				"xByte",
				"xChar",
				"xDouble",
				"xFloat",
				"xInt",
				"xLong",
				"xShort",
				"xDate",
				"xString"
		};

		return HashCodeBuilder.reflectionHashCode(this,ignore);
	}


}
