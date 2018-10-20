package fr.lewon.nn.common;

import fr.lewon.utils.Value;

public enum ActivationFunctions {
	
	SIGMOID( (v) -> { return new Value(1 / (1 + Math.exp(-v.getVal()))); } );

	private ActivationFunction function;
	
	private ActivationFunctions(ActivationFunction function) {
		this.function = function;
	}
	
	public Value process(Value value) {
		return function.process(value);
	}

}

interface ActivationFunction {
	
	Value process(Value value);
	
}