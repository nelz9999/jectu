package net.nelz.jectu.objects;

import org.apache.commons.lang.builder.*;

public class EnumerationContainer {

	private static final String [] IGNORE = new String[] {
			"xMultipleEnum",
			"xSingleEnum",
			"xEmptyEnum"
	};

	private MultipleEnum testMultipleEnum;
	private SingleEnum testSingleEnum;
	private EmptyEnum testEmptyEnum;
	private MultipleEnum xMultipleEnum;
	private SingleEnum xSingleEnum;
	private EmptyEnum xEmptyEnum;

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, IGNORE);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this,IGNORE);
	}
}
