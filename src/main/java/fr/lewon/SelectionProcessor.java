package fr.lewon;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.exceptions.NNException;
import fr.lewon.selection.Selection;
import fr.lewon.selection.Selections;
import fr.lewon.utils.ListsUtil;
import fr.lewon.utils.Pair;
import fr.lewon.utils.CloneUtil;

public class SelectionProcessor {

	private static final Logger logger = LoggerFactory.getLogger(SelectionProcessor.class);

	private Trial trial;
	private Selection selection;
	private int mutationChances;
	private int crossoverChances;

	public SelectionProcessor(Trial trial) {
		this(trial, Selections.TOURNAMENT, 15, 50);
	}

	public SelectionProcessor(Trial trial, Selections selection, int mutationChances, int crossoverChances) {
		this.trial = trial;
		this.selection = selection.getSelection();
		this.mutationChances = mutationChances;
		this.crossoverChances = crossoverChances;
	}

	public Individual start(List<Individual> population, int generationCount, double objectiveFitness) throws NNException {
		Individual bestGlobalIndividual = null;
		for (int i=0;i<generationCount;i++) {
			logger.debug("------ START GENERATION {} ------", i+1);
			for (Individual indiv : population) {
				indiv.setFitness(trial.getFitness(indiv));
			}

			Individual bestIndiv = findBestIndividual(population);
			
			if (bestGlobalIndividual == null || bestIndiv.getFitness() > bestGlobalIndividual.getFitness()) {
				bestGlobalIndividual = CloneUtil.INSTANCE.deepCopy(bestIndiv);
			}

			logger.debug("Best individual this generation (FITNESS)	: {}", bestIndiv.getFitness());
			
			if (bestIndiv.getFitness() >= objectiveFitness) {
				logger.debug("Objective found");
				return bestIndiv;
			}
			
			if (i%20 == 0) {
				logger.debug("Best individual all generations (FITNESS)	: {}", bestGlobalIndividual.getFitness());
			}

			ListsUtil.INSTANCE.shuffleList(population);

			List<Pair<Individual>> breedingPopulation = selection.getNextGenerationParents(population);
			population.clear();
			for (Pair<Individual> parents : breedingPopulation) {
				population.add(breed(parents.getLeft(), parents.getRight()));
			}
		}

		return bestGlobalIndividual;
	}

	private Individual breed(Individual i1, Individual i2) throws NNException {
		Individual parent1,parent2;
		Random r = new Random();
		if (r.nextBoolean()) {
			parent1 = i1;
			parent2 = i2;
		}
		else {
			parent1 = i2;
			parent2 = i1;
		}
		Individual child = CloneUtil.INSTANCE.deepCopy(parent1);

		int rdm=r.nextInt(100);
		if (rdm >= 100 - crossoverChances) {
			child.crossover(parent2);
		}
		if (rdm >= 100 - mutationChances) {
			child.mutate();
		}


		return child;
	}

	private Individual findBestIndividual(List<Individual> population) {
		Individual bestIndividual = null;
		for (Individual i : population) {
			if (bestIndividual == null || i.getFitness() > bestIndividual.getFitness()) {
				bestIndividual = i;
			}
		}
		return bestIndividual;
	}

}
