package fr.lewon.neuralnetwork;

import java.util.Random;

public class Connection {

	private double weight;
	
	public Connection() {
		randomizeWeight();
	}

	private Connection(Connection c) {
		weight = c.weight;
	}

	public void randomizeWeight() {
		weight = WeightValues.weightRandomizer();
	}

	public void mutate() {
		weight += WeightValues.DELTA_WEIGHT * (0.5 - new Random().nextDouble());
		weight = Math.min(weight, WeightValues.MAX_WEIGHT);
		weight = Math.max(weight, WeightValues.MIN_WEIGHT);
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Connection deepCopy() {
		return new Connection(this);
	}

}
