package fr.lewon.neuralnetwork;

public class NeuralLayer {

	private Neuron[] neurons;
	
	public NeuralLayer(ActivationFunctions activationFunction, int size) {
		neurons = new Neuron[size];
		for (int i = 0 ; i < size ; i ++) {
			neurons[i] = new Neuron(activationFunction);
		}
	}
	
	public void resetSums() {
		for (Neuron n : neurons) {
			n.resetSum();
		}
	}

	public void applyActivationFunctions() {
		for (Neuron n : neurons) {
			n.applyActivationFunction();
		}
	}
	
	public Neuron[] getNeurons() {
		return neurons;
	}
	
	public double[] getValues() {
		double[] values = new double[neurons.length];
		for (int i = 0 ; i < neurons.length ; i++) {
			values[i] = neurons[i].getValue();
		}
		return values;
	}
}
