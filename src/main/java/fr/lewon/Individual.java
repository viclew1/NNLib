package fr.lewon;


import fr.lewon.exceptions.NNException;
import fr.lewon.ui.util.ConnectionEdge;
import fr.lewon.ui.util.VertexInfo;
import org.jgrapht.Graph;

import java.util.List;
import java.util.Random;

public abstract class Individual {

    private final Random random = new Random();
    private double fitness;

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return this.fitness;
    }

    public Random getRandom() {
        return this.random;
    }

    /**
     * Initializes and randomizes all the individual genes
     */
    protected abstract void initialize() throws NNException;

    /**
     * Mixes the individual's genes with the passed individual's genes
     *
     * @param individual
     */
    public abstract void crossover(Individual individual) throws NNException;

    /**
     * Mutates some of the individual's genes
     */
    public abstract void mutate(double mutationRate) throws NNException;

    /**
     * Returns the amount of input expected by this individual
     *
     * @return
     */
    public abstract Integer getExpectedInputCount();

    /**
     * Returns the amount of outputs you will get after calling <code>getOutputs()</code>
     *
     * @return
     */
    public abstract Integer getExpectedOutputCount();

    /**
     * Returns an array of double representing the outputs computed based on the passed inputs
     *
     * @param inputs
     * @return
     */
    public abstract List<Double> getOutputs(List<Double> inputs) throws NNException;

    public abstract Graph<VertexInfo, ConnectionEdge> buildGraph();

}
