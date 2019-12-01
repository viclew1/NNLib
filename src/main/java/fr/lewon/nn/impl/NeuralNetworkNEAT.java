package fr.lewon.nn.impl;

import fr.lewon.Individual;
import fr.lewon.exceptions.DifferentIndivualsException;
import fr.lewon.exceptions.NNException;
import fr.lewon.nn.ActivationFunctions;
import fr.lewon.nn.Connection;
import fr.lewon.nn.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class NeuralNetworkNEAT extends NeuralNetwork {

    private Map<Long, Double> neuronsDistanceToInputs;

    public NeuralNetworkNEAT(int inputCount, int outputCount) {
        this(ActivationFunctions.SIGMOID, inputCount, outputCount);
    }

    public NeuralNetworkNEAT(ActivationFunctions activationFunction, int inputCount, int outputCount) {
        super(activationFunction, inputCount, outputCount);
        this.neuronsDistanceToInputs = new TreeMap<>();
    }

    @Override
    protected List<Connection> initConnections() {
        this.getInputLayer().getAllNeurons().forEach(n -> this.neuronsDistanceToInputs.put(n, 0d));
        this.getOutputLayer().getAllNeurons().forEach(n -> this.neuronsDistanceToInputs.put(n, 1d));
        return new ArrayList<>();
    }

    @Override
    public void crossover(Individual individual) throws NNException {
        NeuralNetworkNEAT matingPartner;
        try {
            matingPartner = (NeuralNetworkNEAT) individual;
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
    public void mutate() {
        List<Connection> connections = this.getConnections();
        if (this.getRandom().nextBoolean()) {
            this.createConnection(connections);
        } else {
            this.divideConnection(connections);
        }
        this.defineNewConnections(connections);
    }

    private void createConnection(List<Connection> connections) {
        List<Map.Entry<Long, Double>> fromPool = this.neuronsDistanceToInputs.entrySet().stream()
                .filter((e) -> e.getValue() < 1)
                .collect(Collectors.toList());
        Map.Entry<Long, Double> fromEntry = fromPool.get(this.getRandom().nextInt(fromPool.size()));
        List<Long> toPool = this.neuronsDistanceToInputs.entrySet().stream()
                .filter((e) -> e.getValue() > fromEntry.getValue())
                .map(Map.Entry::getKey)
                .filter(n -> !this.getFromNeurons(n).contains(n))
                .collect(Collectors.toList());
        Long to = toPool.get(this.getRandom().nextInt(toPool.size()));
        connections.add(new Connection(fromEntry.getKey(), to, this.getRandomValue()));
    }

    private List<Long> getFromNeurons(Long neuron) {
        return this.getConnectionsTo(neuron).stream()
                .filter(Connection::isActive)
                .map(Connection::getFrom)
                .collect(Collectors.toList());
    }

    private void divideConnection(List<Connection> connections) {
        List<Connection> activeConnections = connections.stream()
                .filter(Connection::isActive)
                .collect(Collectors.toList());
        Connection toSplit = activeConnections.get(this.getRandom().nextInt(activeConnections.size()));
        toSplit.setActive(false);
        Long newNeuron = this.createNeuron();
        connections.add(new Connection(toSplit.getFrom(), newNeuron, this.getRandomValue()));
        connections.add(new Connection(newNeuron, toSplit.getTo(), this.getRandomValue()));
        double fromDist = this.neuronsDistanceToInputs.get(toSplit.getFrom());
        double toDist = this.neuronsDistanceToInputs.get(toSplit.getTo());
        this.neuronsDistanceToInputs.put(newNeuron, (fromDist + toDist) / 2);
    }

}