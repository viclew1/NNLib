package fr.lewon.nn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import fr.lewon.utils.Value;

public abstract class AbstractNeuron {

	private static final AtomicLong ID_GENERATOR = new AtomicLong();
	
	private final Long id;
	private List<Connection> incomingConnections;
	private ActivationFunctions activationFunction;

	public AbstractNeuron(ActivationFunctions activationFunction) {
		this.id = ID_GENERATOR.incrementAndGet();
		this.incomingConnections = new ArrayList<>();
		this.activationFunction = activationFunction;
	}
	
	
	public Long getId() {
		return id;
	}
	
	public abstract void reset();
	
	public abstract Value getValue();

	public List<Connection> getIncomingConnections() {
		return incomingConnections;
	}
	
	public ActivationFunctions getActivationFunction() {
		return activationFunction;
	}
	
	
	public void addConnectionFrom(AbstractNeuron from) {
		addConnectionFrom(from, WeightValues.INSTANCE.randomWeight());
	}
	
	public void addConnectionFrom(AbstractNeuron from, Value weight) {
		Connection c = new Connection(from, this, weight);
		incomingConnections.add(c);
	}

	public void removeIncomingConnection(Connection c) {
		incomingConnections.remove(c);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractNeuron other = (AbstractNeuron) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
