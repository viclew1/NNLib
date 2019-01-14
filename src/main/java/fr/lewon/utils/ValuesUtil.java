package fr.lewon.utils;

import java.util.Random;

public enum ValuesUtil {

	INSTANCE;
	
	public static final double MIN_WEIGHT = -5;
	public static final double MAX_WEIGHT =  5;
	public static final double DELTA_WEIGHT = 0.5;
	
	public double getRandomValue() {
		return MIN_WEIGHT + new Random().nextDouble()*(MAX_WEIGHT-MIN_WEIGHT);
	}
	
	public Value addValues(Value v1, Value v2) {
		return new Value(v1.getVal() + v2.getVal());
	}
	
	public Value multValues(Value v1, Value v2) {
		return new Value(v1.getVal() * v2.getVal());
	}
	
	public Value subValues(Value v1, Value v2) {
		return new Value(v1.getVal() - v2.getVal());
	}
	
	public Value divValues(Value v1, Value v2) {
		return new Value(v1.getVal() / v2.getVal());
	}
}
