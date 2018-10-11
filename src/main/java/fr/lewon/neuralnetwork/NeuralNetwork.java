package fr.lewon.neuralnetwork;

import java.util.Random;

import fr.lewon.genetics.Individual;

public class NeuralNetwork extends Individual {

	private ActivationFunctions activationFunction;
	private int[] layersSizes;
	private NeuralLayer[] layers;
	private Connection[] connections;


	public NeuralNetwork(int[] layersSizes) {
		this(layersSizes, null);
	}
	
	public NeuralNetwork(ActivationFunctions activationFunction, int[] layersSizes) {
		this(activationFunction, layersSizes, null);
	}
	
	public NeuralNetwork(int[] layersSizes, double[] weights) {
		this(ActivationFunctions.SIGMOID, layersSizes, weights);
	}

	public NeuralNetwork(ActivationFunctions activationFunction, int[] layersSizes, double[] weights) {
		this.activationFunction = activationFunction;
		this.layersSizes=layersSizes;
		initLayers();
		int connectionsSz = 0 ;
		for (int i = 0 ; i < layersSizes.length - 1 ; i++) {
			connectionsSz += layersSizes[i] * layersSizes[i+1];
		}

		connections = new Connection[connectionsSz];
		for (int i=0;i<connections.length;i++) {
			connections[i] = new Connection();
			if (weights != null) {
				connections[i].setWeight(weights[i]);
			}
		}
	}

	private NeuralNetwork(NeuralNetwork nn) {
		this.layersSizes=nn.layersSizes;
		initLayers();
		connections = new Connection[nn.connections.length];
		for (int i=0;i<connections.length;i++) {
			connections[i] = nn.connections[i].deepCopy();
		}
	}

	private void initLayers() {
		layers = new NeuralLayer[layersSizes.length];
		for (int i = 0 ; i < layersSizes.length ; i ++) {
			layers[i] = new NeuralLayer(activationFunction, layersSizes[i]);
		}
	}

	public double[] getOutputs(double[] inputs) {
		int connectionCpt = 0;

		for (NeuralLayer nl : layers) {
			nl.resetSums();
		}

		Neuron[] inputNeurons = layers[0].getNeurons();
		for (int i = 0 ; i < inputNeurons.length ; i++) {
			inputNeurons[i].setValue(inputs[i]);
		}

		for (int i = 0 ; i < layersSizes.length - 1 ; i++) {
			Neuron[] from = layers[i].getNeurons();
			Neuron[] to   = layers[i+1].getNeurons();
			for (int j = 0 ; j < from.length ; j++) {
				double value = from[j].getValue();
				for (int k = 0 ; k < to.length ; k++) {
					double weight = connections[connectionCpt++].getWeight();
					to[k].addToSum(value, weight);
				}
			}
			layers[i+1].applyActivationFunctions();
		}

		return layers[layers.length-1].getValues();
	}

	public NeuralNetwork deepCopy() {
		return new NeuralNetwork(this);
	}

	@Override
	public boolean equals(Object o) {
		NeuralNetwork nn = (NeuralNetwork)o;
		if (nn.connections.length!=connections.length) {
			return false;
		}
		for (int i=0;i<connections.length;i++) {
			if (connections[i].getWeight()!=nn.connections[i].getWeight()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void crossover(Individual individu) {
		NeuralNetwork nn = (NeuralNetwork) individu;
		if (nn.connections.length != connections.length) {
			return;
		}
		
		Random r = new Random();
		int index = r.nextInt(connections.length);
		if (r.nextBoolean()) {
			for (int i = index ; i < connections.length ; i++) {
				connections[i] = nn.connections[i].deepCopy();
			}
		}
		else {
			for (int i = 0 ; i < index ; i++) {
				connections[i] = nn.connections[i].deepCopy();
			}
		}
	}

	@Override
	public void mutate() {
		int mutationCount = Math.max(1, connections.length/50);
		Random r = new Random();
		for (int i = 0 ; i < mutationCount ; i++) {
			int mutateIndex = r.nextInt(connections.length);
			connections[mutateIndex].mutate();
		}
	}

	public String toString() {
		String str = "";
		for (int i = 0 ; i < connections.length ; i++) {
			Connection c = connections[i];
			str+=c.getWeight();
			if (i != connections.length - 1) {
				str += ",";
			}
		}
		return str;
	}

	@Override
	public void randomize() {
		for (int i = 0 ; i < connections.length ; i++) {
			connections[i].randomizeWeight();
		}
	}
}
