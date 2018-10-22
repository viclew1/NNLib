package fr.lewon.nn;

import fr.lewon.utils.Value;

public class BiasNeuron extends AbstractNeuron {

	public BiasNeuron() {
		super(null);
	}

	@Override
	public void reset() {}

	@Override
	public Value getValue() {
		return new Value(1);
	}

}
