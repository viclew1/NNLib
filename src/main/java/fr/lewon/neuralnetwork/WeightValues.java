package fr.lewon.neuralnetwork;

import java.util.Random;

public class WeightValues {

	public static final double MIN_WEIGHT = -5;
	public static final double MAX_WEIGHT =  5;
	public static final double DELTA_WEIGHT = 0.5;
	
	public static double weightRandomizer() {
		return MIN_WEIGHT + new Random().nextDouble()*(MAX_WEIGHT-MIN_WEIGHT);
	}
	
}
