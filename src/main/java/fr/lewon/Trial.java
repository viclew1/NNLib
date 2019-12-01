package fr.lewon;

import fr.lewon.exceptions.NNException;

public interface Trial {

    /**
     * Submits the passed individual to the trial and returns its fitness
     *
     * @param individual
     * @throws NNException
     */
    double getFitness(Individual individual) throws NNException;

}
