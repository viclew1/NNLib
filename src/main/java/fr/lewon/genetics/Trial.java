package fr.lewon.genetics;

public abstract class Trial {
	
	/**
	 * Submits the passed individual to the trial and returns its fitness
	 * @param individual
	 */
	public abstract double startTrial(Individual individual);
	
}
