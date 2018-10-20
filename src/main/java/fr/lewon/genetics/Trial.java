package fr.lewon.genetics;

import fr.lewon.exceptions.NNException;

public abstract class Trial {
	
	/**
	 * Submits the passed individual to the trial and returns its fitness
	 * @param individual
	 * @throws NNException 
	 */
	public abstract double getFitness(Individual individual) throws NNException;
	
}
