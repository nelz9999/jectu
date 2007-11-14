package net.nelz.jectu.helper;

public class ProgrammableEqualsPOJO {
	private boolean result = false;
	
	public ProgrammableEqualsPOJO() {
	}

	public ProgrammableEqualsPOJO(boolean result) {
		this.result = result;
	}

	@Override
	public boolean equals(Object obj) {
		return result;
	}

	@Override
	public int hashCode() {
		return 17;
	}
	
	
}
