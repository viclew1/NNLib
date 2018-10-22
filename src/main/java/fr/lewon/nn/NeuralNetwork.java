package fr.lewon.nn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import fr.lewon.Individual;
import fr.lewon.exceptions.InputCountException;
import fr.lewon.exceptions.NNException;
import fr.lewon.utils.Value;

public abstract class NeuralNetwork extends Individual {

	private final ActivationFunctions activationFunction;

	private final NeuralLayer inputLayer;
	private final List<NeuralLayer> hiddenLayers;
	private final NeuralLayer outputLayer;

	private final Random random;
	
	public NeuralNetwork(ActivationFunctions activationFunction, int inputCount, int outputCount, List<Integer> hiddenLayerSizes, double[] weights) {
		this.activationFunction = activationFunction;
		this.random = new Random();
		
		List<AbstractNeuron> inputNeurons = new ArrayList<>(inputCount);
		for (int i = 0 ; i < inputCount ; i++) {
			inputNeurons.add(new InputNeuron());
		}
		this.inputLayer = new NeuralLayer(new BiasNeuron(), inputNeurons);

		List<AbstractNeuron> outputNeurons = new ArrayList<>(outputCount);
		for (int i = 0 ; i < outputCount ; i++) {
			outputNeurons.add(new Neuron(activationFunction));
		}
		this.outputLayer = new NeuralLayer(null, outputNeurons);

		this.hiddenLayers = new ArrayList<>();
		for (Integer sz : hiddenLayerSizes) {
			List<AbstractNeuron> layerNeurons = new ArrayList<>();
			for (int i = 0 ; i < sz ; i++) {
				layerNeurons.add(new Neuron(activationFunction));
			}
			this.hiddenLayers.add(new NeuralLayer(new BiasNeuron(), layerNeurons));
		}
		
		initConnections();
		randomize();
	}
	
	private void initConnections() {
		List<NeuralLayer> allLayers = new ArrayList<>();
		allLayers.add(getInputLayer());
		getHiddenLayers().forEach(l -> allLayers.add(l));
		allLayers.add(getOutputLayer());
		
		for (int i = 0 ; i < allLayers.size() - 1 ; i++) {
			List<AbstractNeuron> fromLayerNeurons = allLayers.get(i).getAllNeurons();
			List<AbstractNeuron> toLayerNeurons = allLayers.get(i+1).getNeurons();
			
			for (AbstractNeuron from : fromLayerNeurons) {
				for (AbstractNeuron to : toLayerNeurons) {
					to.addConnectionFrom(from);
				}
			}
		}
	}

	@Override
	protected void randomize() {
		randomizeNeurons(outputLayer.getNeurons());
		hiddenLayers.forEach(l -> randomizeNeurons(l.getNeurons()));
	}
	
	@Override
	public List<Double> getOutputs(List<Double> inputs) throws NNException {
		if (inputs.size() != inputLayer.getNeurons().size()) {
			throw new InputCountException(inputLayer.getNeurons().size(), inputs.size());
		}
		
		for (AbstractNeuron n : outputLayer.getNeurons()) {
			n.reset();
		}
		
		for (int i = 0 ; i < inputs.size() ; i++) {
			inputLayer.getNeurons().get(i).getValue().setVal(inputs.get(i));
		}

		return outputLayer.getNeurons().stream()
				.map(AbstractNeuron::getValue)
				.map(Value::getVal)
				.collect(Collectors.toList());
	}
	
	protected List<Connection> flatten() {
		List<NeuralLayer> allLayers = new ArrayList<>();
		allLayers.add(inputLayer);
		hiddenLayers.forEach(l -> allLayers.add(l));
		allLayers.add(outputLayer);
		
		return allLayers.stream()
				.map(NeuralLayer::getAllNeurons)
				.flatMap(List::stream)
				.map(AbstractNeuron::getIncomingConnections)
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}
	
	protected void randomizeNeurons(List<AbstractNeuron> neurons) {
		for (AbstractNeuron n : neurons) {
			for (Connection c : n.getIncomingConnections()) {
				c.randomize();
			}
		}
	}

	public ActivationFunctions getActivationFunction() {
		return activationFunction;
	}

	public NeuralLayer getInputLayer() {
		return inputLayer;
	}

	public List<NeuralLayer> getHiddenLayers() {
		return hiddenLayers;
	}

	public NeuralLayer getOutputLayer() {
		return outputLayer;
	}

	protected Random getRandom() {
		return random;
	}
	
}
