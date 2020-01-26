package fr.lewon.nn.impl;

import fr.lewon.Individual;
import fr.lewon.exceptions.DifferentIndivualsException;
import fr.lewon.exceptions.NNException;
import fr.lewon.nn.ActivationFunctions;
import fr.lewon.nn.Connection;
import fr.lewon.nn.NeuralLayer;
import fr.lewon.nn.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkClassic extends NeuralNetwork {

    private List<Integer> hiddenLayersSizes;

    public NeuralNetworkClassic(int inputCount, int outputCount) {
        this(inputCount, outputCount, new ArrayList<>());
    }

    public NeuralNetworkClassic(int inputCount, int outputCount, List<Integer> hiddenLayersSizes) {
        this(ActivationFunctions.SIGMOID, inputCount, outputCount, hiddenLayersSizes);
    }

    public NeuralNetworkClassic(ActivationFunctions activationFunction, int inputCount, int outputCount, List<Integer> hiddenLayersSizes) {
        super(activationFunction, inputCount, outputCount);
        this.hiddenLayersSizes = hiddenLayersSizes;
    }

    @Override
    public void crossover(Individual individual) throws NNException {
        NeuralNetworkClassic matingPartner;
        try {
            matingPartner = (NeuralNetworkClassic) individual;
        } catch (ClassCastException e) {
            throw new DifferentIndivualsException(this.getClass(), individual.getClass());
        }

        List<Connection> allMyConnections = this.getConnections();
        List<Connection> allHisConnections = matingPartner.getConnections();

        int cut = this.getRandom().nextInt(allMyConnections.size());

        boolean randomBoolean = this.getRandom().nextBoolean();
        int startIndex = randomBoolean ? 0 : cut;
        int stopIndex = randomBoolean ? cut : allMyConnections.size();
        for (int i = startIndex; i < stopIndex; i++) {
            double hisVal = allHisConnections.get(i).getWeight();
            allMyConnections.get(i).setWeight(hisVal);
        }
        this.defineNewConnections(allMyConnections);
    }

    @Override
    protected List<Connection> initConnections() {
        List<Connection> connections = new ArrayList<>();
        NeuralLayer fromLayer = this.getInputLayer();
        NeuralLayer toLayer;
        for (int hiddenLayerSize : this.hiddenLayersSizes) {
            List<Long> hiddenLayerNeurons = new ArrayList<>();
            for (int i = 0; i < hiddenLayerSize; i++) {
                hiddenLayerNeurons.add(this.createNeuron());
            }
            toLayer = new NeuralLayer(this.createBiasNeuron(), hiddenLayerNeurons);
            connections.addAll(this.generateLayersConnections(fromLayer, toLayer));
            fromLayer = toLayer;
        }
        connections.addAll(this.generateLayersConnections(fromLayer, this.getOutputLayer()));
        return connections;
    }

}
