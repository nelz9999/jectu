package net.nelz.jectu.helper;

public class ProgrammableHashCodePOJO {

	private int value = 1;

	public ProgrammableHashCodePOJO() {
	}

	public ProgrammableHashCodePOJO(int value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}

	@Override
	public int hashCode() {
		return value;
	}
	
	
}
