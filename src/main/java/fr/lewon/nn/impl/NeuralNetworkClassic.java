package fr.lewon.nn.impl;

import java.util.ArrayList;
import java.util.List;

import fr.lewon.Individual;
import fr.lewon.exceptions.DifferentIndivualsException;
import fr.lewon.exceptions.NNException;
import fr.lewon.nn.ActivationFunctions;
import fr.lewon.nn.Connection;
import fr.lewon.nn.NeuralNetwork;

public class NeuralNetworkClassic extends NeuralNetwork {

	public NeuralNetworkClassic(int inputCount, int outputCount) {
		this(inputCount, outputCount, new ArrayList<>());
	}
	
	public NeuralNetworkClassic(int inputCount, int outputCount, List<Integer> hiddenLayerSizes) {
		this(inputCount, outputCount, hiddenLayerSizes, null);
	}

	public NeuralNetworkClassic(ActivationFunctions activationFunction, int inputCount, int outputCount, List<Integer> hiddenLayerSizes) {
		this(activationFunction, inputCount, outputCount, hiddenLayerSizes, null);
	}

	public NeuralNetworkClassic(int inputCount, int outputCount, List<Integer> hiddenLayerSizes, double[] weights) {
		this(ActivationFunctions.SIGMOID, inputCount, outputCount, hiddenLayerSizes, weights);
	}

	public NeuralNetworkClassic(ActivationFunctions activationFunction, int inputCount, int outputCount, List<Integer> hiddenLayerSizes, double[] weights) {
		super(activationFunction, inputCount, outputCount, hiddenLayerSizes, weights);
	}

	@Override
	public void crossover(Individual individual) throws NNException {
		NeuralNetworkClassic matingPartner;
		try {
			matingPartner = (NeuralNetworkClassic) individual;
		} catch (ClassCastException e) {
			throw new DifferentIndivualsException(this.getClass(), individual.getClass());
		}
		
		
		List<Connection> allMyConnections = flatten();
		List<Connection> allHisConnections = matingPartner.flatten();
		
		int cut = getRandom().nextInt(allMyConnections.size());
		
		if (getRandom().nextBoolean()) {
			for (int i = 0 ; i < cut ; i++) {
				double hisVal = allHisConnections.get(i).getWeight().getVal();
				allMyConnections.get(i).getWeight().setVal(hisVal);
			}
		} else {
			for (int i = cut ; i < allMyConnections.size() ; i++) {
				double hisVal = allHisConnections.get(i).getWeight().getVal();
				allMyConnections.get(i).getWeight().setVal(hisVal);
			}
		}
	}
	
	@Override
	public void mutate() throws NNException {
		List<Connection> connections = flatten();
		int toMutate = getRandom().nextInt(connections.size());
		connections.get(toMutate).mutate();
	}

}
