package fr.lewon.nn;

import fr.lewon.Individual;
import fr.lewon.exceptions.InputCountException;
import fr.lewon.exceptions.NNException;
import fr.lewon.ui.util.ConnectionEdge;
import fr.lewon.ui.util.VertexInfo;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.awt.*;
import java.util.List;
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
        this.inputLayer = new NeuralLayer(this.createBiasNeuron(), inputNeurons);

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
            neuronValues.put(this.getInputLayer().getNeurons().get(i), inputs.get(i));
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
        return this.connectionsByDestId.getOrDefault(neuronId, new ArrayList<>());
    }

    public List<Connection> getConnections() {
        return new ArrayList<>(this.connections);
    }

    protected void defineNewConnections(List<Connection> connections) {
        this.connections = connections;
        this.connectionsByDestId = connections.stream()
                .collect(Collectors.groupingBy(c -> c.getTo(), TreeMap::new, Collectors.toList()));
    }

    @Override
    public void mutate(double mutationRate) {
        for (Connection toMutate : this.connections) {
            double rdm = this.getRandom().nextDouble();
            if (rdm <= mutationRate) {
                toMutate.setWeight(this.mutateValue(toMutate.getWeight()));
            }
        }
    }

    public NeuralLayer getInputLayer() {
        return this.inputLayer;
    }

    public NeuralLayer getOutputLayer() {
        return this.outputLayer;
    }

    protected double mutateValue(double value) {
        value += (-1 + this.getRandom().nextDouble() * 2) * NeuralNetwork.DELTA_WEIGHT;
        return Math.min(NeuralNetwork.MAX_WEIGHT, Math.max(NeuralNetwork.MIN_WEIGHT, value));
    }

    protected double getRandomValue() {
        return NeuralNetwork.MIN_WEIGHT + this.getRandom().nextDouble() * (NeuralNetwork.MAX_WEIGHT - NeuralNetwork.MIN_WEIGHT);
    }

    @Override
    public Graph<VertexInfo, ConnectionEdge> buildGraph() {
        Map<Long, VertexInfo> infoById = new HashMap<>();
        Graph<VertexInfo, ConnectionEdge> g = new DefaultDirectedGraph<>(ConnectionEdge.class);
        int inputCpt = 1;
        int outputCpt = 1;
        int biasCpt = 1;
        int hiddenCpt = 1;
        for (Long n : this.inputLayer.getNeurons()) {
            this.addVertex(n, "Input " + inputCpt++, Color.LIGHT_GRAY, g, infoById);
        }
        for (Long n : this.outputLayer.getNeurons()) {
            this.addVertex(n, "Output " + outputCpt++, Color.RED, g, infoById);
        }
        for (Long n : this.biasNeurons) {
            this.addVertex(n, "Bias " + biasCpt++, Color.GREEN, g, infoById);
        }

        for (Connection c : this.getConnections()) {
            if (!infoById.containsKey(c.getFrom())) {
                this.addVertex(c.getFrom(), "Hidden " + hiddenCpt++, Color.YELLOW, g, infoById);
            }
            if (!infoById.containsKey(c.getTo())) {
                this.addVertex(c.getTo(), "Hidden " + hiddenCpt++, Color.YELLOW, g, infoById);
            }
            VertexInfo fromInfo = infoById.get(c.getFrom());
            VertexInfo toInfo = infoById.get(c.getTo());
            g.addEdge(fromInfo, toInfo, new ConnectionEdge(String.format("%.1f", c.getWeight())));
        }
        return g;
    }

    private void addVertex(Long id, String label, Color color, Graph<VertexInfo, ConnectionEdge> graph, Map<Long, VertexInfo> infoById) {
        VertexInfo vi = new VertexInfo(id, label, color);
        graph.addVertex(vi);
        infoById.put(id, vi);
    }
}
