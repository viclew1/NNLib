package fr.lewon.genetics;

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
	public abstract void randomize();
	
	/**
	 * Mixes the individual's genes with the passed individual's genes
	 * @param individual
	 */
	public abstract void crossover(Individual individual);
	
	/**
	 * Mutates some of the individual's genes
	 */
	public abstract void mutate();
	
	/**
	 * Creates a clone of the individual
	 * @return
	 */
	public abstract Individual deepCopy();
	
	/**
	 * Returns an array of double representing the outputs computed based on the passed inputs
	 * @param inputs
	 * @return
	 */
	public abstract double[] getOutputs(double[] inputs);

}
