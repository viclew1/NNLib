package fr.lewon.nn;

import fr.lewon.Individual;
import fr.lewon.exceptions.InputCountException;
import fr.lewon.exceptions.NNException;

import java.util.*;
import java.util.stream.Collectors;

public abstract class NeuralNetwork extends Individual {

    public static final double MIN_WEIGHT = -10;
    public static final double MAX_WEIGHT = 10;
    public static final double DELTA_WEIGHT = 1;

    private long neuronCpt = 1;

    private final ActivationFunctions activationFunction;

    private final NeuralLayer inputLayer;
    private final NeuralLayer outputLayer;

    private List<Connection> connections;
    private Map<Long, List<Connection>> connectionsByDestId;

    private Set<Long> biasNeurons = new TreeSet<>();

    public NeuralNetwork(ActivationFunctions activationFunction, int inputCount, int outputCount) {
        this.activationFunction = activationFunction;
        List<Long> inputNeurons = new ArrayList<>(inputCount);
        for (int i = 0; i < inputCount; i++) {
            inputNeurons.add(this.createNeuron());
        }
        this.inputLayer = new NeuralLayer(this.createNeuron(), inputNeurons);

        List<Long> outputNeurons = new ArrayList<>(outputCount);
        for (int i = 0; i < outputCount; i++) {
            outputNeurons.add(this.createNeuron());
        }
        this.outputLayer = new NeuralLayer(outputNeurons);
    }

    protected long createBiasNeuron() {
        Long neuron = this.neuronCpt++;
        this.biasNeurons.add(neuron);
        return neuron;
    }

    protected long createNeuron() {
        return this.neuronCpt++;
    }

    protected abstract List<Connection> initConnections();

    @Override
    protected void initialize() {
        this.defineNewConnections(this.initConnections());
    }

    @Override
    public List<Double> getOutputs(List<Double> inputs) throws NNException {
        if (inputs.size() != this.inputLayer.getNeurons().size()) {
            throw new InputCountException(this.inputLayer.getNeurons().size(), inputs.size());
        }

        // Reset
        Map<Long, Double> neuronValues = new TreeMap<>();

        // Set bias values
        for (Long biasN : this.biasNeurons) {
            neuronValues.put(biasN, 1d);
        }

        // Set input values
        for (int i = 0; i < inputs.size(); i++) {
            neuronValues.put(Long.valueOf(i), inputs.get(i));
        }

        // Propagate network
        this.propagate(neuronValues);

        return this.outputLayer.getNeurons().stream()
                .map(n -> neuronValues.get(n))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getExpectedInputCount() {
        return this.inputLayer.getNeurons().size();
    }

    @Override
    public Integer getExpectedOutputCount() {
        return this.outputLayer.getNeurons().size();
    }


    protected List<Connection> generateLayersConnections(NeuralLayer fromLayer, NeuralLayer toLayer) {
        List<Connection> connections = new ArrayList<>();
        for (Long fromN : fromLayer.getAllNeurons()) {
            for (Long toN : toLayer.getNeurons()) {
                connections.add(new Connection(fromN, toN, this.getRandomValue()));
            }
        }
        return connections;
    }

    private void propagate(Map<Long, Double> neuronValues) {
        for (Long neuron : this.getOutputLayer().getNeurons()) {
            this.generateValue(neuronValues, neuron);
        }
    }

    private double generateValue(Map<Long, Double> neuronValues, Long neuron) {
        Double value = neuronValues.get(neuron);
        if (value != null) {
            return value;
        }
        double sum = this.getConnectionsTo(neuron).stream()
                .filter(Connection::isActive)
                .map(c -> this.generateValue(neuronValues, c.getFrom()) * c.getWeight())
                .mapToDouble(Double::doubleValue)
                .sum();
        value = this.activationFunction.process(sum);
        neuronValues.put(neuron, value);
        return value;
    }

    protected List<Connection> getConnectionsTo(Long neuronId) {
        return this.connectionsByDestId.get(neuronId);
    }

    public List<Connection> getConnections() {
        return new ArrayList<>(this.connections);
    }

    protected void defineNewConnections(List<Connection> connections) {
        this.connections = connections;
        this.connectionsByDestId = connections.stream()
                .collect(Collectors.groupingBy(c -> c.getTo(), TreeMap::new, Collectors.toList()));
    }

    public NeuralLayer getInputLayer() {
        return this.inputLayer;
    }

    public NeuralLayer getOutputLayer() {
        return this.outputLayer;
    }

    protected double mutateValue(double value) {
        value += (-1 + this.getRandom().nextDouble() * 2) * DELTA_WEIGHT;
        return Math.min(MAX_WEIGHT, Math.max(MIN_WEIGHT, value));
    }

    protected double getRandomValue() {
        return MIN_WEIGHT + this.getRandom().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT);
    }

}
