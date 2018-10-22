package fr.lewon.nn;

import fr.lewon.utils.Value;

public class InputNeuron extends AbstractNeuron {

	private Value v = new Value();
	
	
	public InputNeuron() {
		super(null);
	}
	
	
	@Override
	public Value getValue() {
		return v;
	}


	@Override
	public void reset() {
		v.reset();
	}

}
