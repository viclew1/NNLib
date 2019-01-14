package fr.lewon;

import fr.lewon.exceptions.NNException;
import fr.lewon.utils.PopulationInfos;

public abstract class Trial {
	
	/**
	 * Submits the passed individual to the trial and returns its fitness
	 * @param individual
	 * @throws NNException 
	 */
	public double getFitness(Individual individual) throws NNException {
		individual.setFitness(0);
		return testIndividual(individual);
	}
	
	protected abstract double testIndividual(Individual individual) throws NNException;

	public abstract void processBetweenGenerationsActions(PopulationInfos infos);
	
}
