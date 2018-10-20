package fr.lewon.nn.common;

import java.util.ArrayList;
import java.util.List;

public class NeuralLayer {

	private BiasNeuron bias;
	private List<AbstractNeuron> neurons;

	
	public NeuralLayer(BiasNeuron bias, List<AbstractNeuron> neurons) {
		this.bias = bias;
		this.neurons = neurons;
	}


	public List<AbstractNeuron> getAllNeurons() {
		List<AbstractNeuron> allNeurons = new ArrayList<>();
		allNeurons.addAll(neurons);
		if (bias != null) {
			allNeurons.add(bias);
		}
		return allNeurons;
	}
	
	public BiasNeuron getBias() {
		return bias;
	}
	public void setBias(BiasNeuron bias) {
		this.bias = bias;
	}
	public List<AbstractNeuron> getNeurons() {
		return neurons;
	}
	public void setNeurons(List<AbstractNeuron> neurons) {
		this.neurons = neurons;
	}
}
