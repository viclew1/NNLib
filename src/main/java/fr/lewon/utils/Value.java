package fr.lewon.utils;

public class Value {

	private double val;
	
	public Value() {
		this(0);
	}
	
	public Value(double val) {
		this.val = val;
	}

	public double getVal() {
		return val;
	}
	public void setVal(double val) {
		this.val = val;
	}

	public void reset() {
		val = 0;
	}
}
