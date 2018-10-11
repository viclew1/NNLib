package fr.lewon.neuralnetwork;

public class Neuron {

	private ActivationFunctions activationFunction;
	private double value;
	private double sum = 0;
	
	public Neuron(ActivationFunctions activationFunctions) {
		this.activationFunction = activationFunctions;
	}
	
	public void applyActivationFunction() {
		value = activationFunction.process(sum);
	}
	
	public void addToSum(double value, double weight) {
		sum += value * weight;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public void resetSum() {
		sum = 0;
	}
	
}
