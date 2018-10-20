package fr.lewon.nn.common;

import fr.lewon.utils.Value;
import fr.lewon.utils.ValuesUtil;

public class Neuron extends AbstractNeuron {
	
	private Value value;
	private Boolean valueGenerated = false;
	
	public Neuron(ActivationFunctions activationFunction) {
		super(activationFunction);
	}
	
	
	@Override
	public Value getValue() {
		if (!valueGenerated) {
			generateValue();
		}
		return value;
	}
	
	private void generateValue() {
		Value sum = new Value();
		for (Connection c : this.getIncomingConnections()) {
			Value cVal = ValuesUtil.INSTANCE.multValues(c.getFrom().getValue(), c.getWeight());
			sum = ValuesUtil.INSTANCE.addValues(sum, cVal);
		}
		value = getActivationFunction().process(sum);
		valueGenerated = true;
	}

	@Override
	public void reset() {
		valueGenerated = false;
		for (Connection n : getIncomingConnections()) {
			n.getFrom().reset();
		}
	}

}
