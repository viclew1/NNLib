package fr.lewon.nn.common;

import fr.lewon.utils.Value;

public class Connection {

	private final AbstractNeuron from;
	private final AbstractNeuron to;
	private Value weight;
	
	public Connection(AbstractNeuron from, AbstractNeuron to, Value weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public AbstractNeuron getFrom() {
		return from;
	}

	public AbstractNeuron getTo() {
		return to;
	}
	
	public Value getWeight() {
		return weight;
	}

	public void setWeight(Value weight) {
		this.weight = weight;
	}

	public void randomize() {
		this.weight = WeightValues.INSTANCE.randomWeight();
	}
	
	public void mutate() {
		WeightValues.INSTANCE.mutateWeight(this.weight);
	}
}
