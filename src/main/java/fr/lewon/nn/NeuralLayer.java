package fr.lewon.nn;

import java.util.ArrayList;
import java.util.List;

public class NeuralLayer {

    private Long biasNeuron;
    private List<Long> neurons;

    public NeuralLayer(List<Long> neurons) {
        this(null, neurons);
    }

    public NeuralLayer(Long biasNeuron, List<Long> neurons) {
        this.biasNeuron = biasNeuron;
        this.neurons = neurons;
    }

    /**
     * returns the neurons of this layer and the bias neuron if there is one
     *
     * @return
     */
    public List<Long> getAllNeurons() {
        List<Long> allNeurons = new ArrayList<>(this.getNeurons());
        if (this.biasNeuron != null) {
            allNeurons.add(this.biasNeuron);
        }
        return allNeurons;
    }

    public Long getBiasNeuron() {
        return this.biasNeuron;
    }

    public void setBiasNeuron(Long biasNeuron) {
        this.biasNeuron = biasNeuron;
    }

    public List<Long> getNeurons() {
        return this.neurons;
    }

    public void setNeurons(List<Long> neurons) {
        this.neurons = neurons;
    }

}
