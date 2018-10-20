package fr.lewon.nn.common;

import java.util.Random;

import fr.lewon.utils.Value;

public enum WeightValues {

	INSTANCE;
	
	public static final double MIN_WEIGHT = -10;
	public static final double MAX_WEIGHT =  10;
	public static final double DELTA_WEIGHT = 1;
	
	private Random random;
	
	private WeightValues() {
		this.random = new Random();
	}
	
	
	public Value randomWeight() {
		return new Value(MIN_WEIGHT + random.nextDouble()*(MAX_WEIGHT-MIN_WEIGHT));
	}
	
	public void mutateWeight(Value value) {
		double val = value.getVal();
		double diff = (-1 + random.nextDouble() * 2) * DELTA_WEIGHT;
		double newVal = val + diff;
		newVal = Math.max(MIN_WEIGHT, newVal);
		newVal = Math.min(MAX_WEIGHT, newVal);
		value.setVal(newVal);
	}
	
}
