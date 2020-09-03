package fr.lewon;

import fr.lewon.exceptions.NNException;

import java.util.List;

public interface Trial {

    /**
     * Step where the individuals should have their fitness set
     *
     * @param population
     * @throws NNException
     */
    void execute(List<Individual> population) throws NNException;

}
