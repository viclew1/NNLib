package fr.lewon;


import java.util.List;

import fr.lewon.exceptions.NNException;

public abstract class Individual {
	
	private double fitness;
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getFitness() {
		return this.fitness;
	}
	
	/**
	 * Randomizes all the individual genes
	 */
	protected abstract void randomize() throws NNException;
	
	/**
	 * Mixes the individual's genes with the passed individual's genes
	 * @param individual
	 */
	public abstract void crossover(Individual individual) throws NNException;
	
	/**
	 * Mutates some of the individual's genes
	 */
	public abstract void mutate() throws NNException;
	
	
	/**
	 * Returns the amount of input expected by this individual
	 * @return
	 */
	public abstract Integer getExpectedInputCount();
	
	/**
	 * Returns the amount of outputs you will get after calling <code>getOutputs()</code>
	 * @return
	 */
	public abstract Integer getExpectedOutputCount();
	
	/**
	 * Returns an array of double representing the outputs computed based on the passed inputs
	 * @param inputs
	 * @return
	 */
	public abstract List<Double> getOutputs(List<Double> inputs) throws NNException;

}
