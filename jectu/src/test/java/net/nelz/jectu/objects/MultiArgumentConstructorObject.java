package net.nelz.jectu.objects;

import java.util.*;

public class MultiArgumentConstructorObject {
	private double testDouble;
	private Date testDate;
	private int xInt;
	private String xString;
	private static final String STATIC1 = "bubba";
	private static final String STATIC2 = "gump";
	
	public MultiArgumentConstructorObject(Date testDate, int int1) {
		super();
		this.testDate = testDate;
		xInt = int1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((testDate == null) ? 0 : testDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(testDouble);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		final MultiArgumentConstructorObject other = (MultiArgumentConstructorObject) obj;
		if (testDate == null) {
			if (other.testDate != null)
				return false;
		} else if (!testDate.equals(other.testDate))
			return false;
		if (Double.doubleToLongBits(testDouble) != Double
				.doubleToLongBits(other.testDouble))
			return false;
		return true;
	}

	
	
}
