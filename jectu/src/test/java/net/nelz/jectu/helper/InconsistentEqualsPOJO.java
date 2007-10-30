package net.nelz.jectu.helper;

public class InconsistentEqualsPOJO {

	private int remainingInvocations;

	public InconsistentEqualsPOJO(int remainingInvocations) {
		super();
		this.remainingInvocations = remainingInvocations;
	}

	public InconsistentEqualsPOJO() {
		this.remainingInvocations = 1000;
	}

	@Override
	public boolean equals(Object obj) {
		this.remainingInvocations--;
		if (this.remainingInvocations < 0) {
			return false;
		}
		return true;
	}
	
	
	
}
