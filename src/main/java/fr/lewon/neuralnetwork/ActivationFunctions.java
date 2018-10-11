package fr.lewon.neuralnetwork;

public enum ActivationFunctions {
	
	SIGMOID( (v) -> { return 1 / (1 + Math.exp(-v)); } );

	private ActivationFunction function;
	
	private ActivationFunctions(ActivationFunction function) {
		this.function = function;
	}
	
	public double process(double value) {
		return function.process(value);
	}

}

interface ActivationFunction {
	
	Double process(double value);
	
}