package fr.lewon.genetics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.exceptions.NNException;
import fr.lewon.utils.ListsUtil;
import fr.lewon.utils.CloneUtil;

public class Selection {

	private static final Logger logger = LoggerFactory.getLogger(Selection.class);

	private Trial trial;
	private int generationCount;
	private double objectiveFitness;
	private SelectionTypes selectionType;
	private List<Individual> population;
	private int mutationChances;
	private int crossoverChances;

	public Selection(Trial trial, List<Individual> population, int generationCount, double objectiveFitness) {
		this(trial, population, generationCount, objectiveFitness, SelectionTypes.STOCHASTIC_UNIVERSAL_SAMPLING, 10, 50);
	}

	public Selection(Trial trial, List<Individual> population, int generationCount, double objectiveFitness, SelectionTypes selectionType, int mutationChances, int crossoverChances) {
		this.trial = trial;
		this.population = population;
		this.generationCount = generationCount;
		this.objectiveFitness = objectiveFitness;
		this.selectionType = selectionType;
		this.mutationChances = mutationChances;
		this.crossoverChances = crossoverChances;
	}

	public Individual start() throws NNException {
		double bestGlobalFitness = Double.MIN_VALUE;
		Individual bestGlobalIndividual = null;
		for (int i=0;i<generationCount;i++) {
			logger.debug("------ START GENERATION {} ------", i+1);
			for (Individual indiv : population) {
				indiv.setFitness(trial.getFitness(indiv));
			}

			Individual bestIndiv = findBestIndividual();
			double bestGenerationFitness = bestIndiv.getFitness();
			
			if (bestGenerationFitness > bestGlobalFitness) {
				bestGlobalFitness = bestGenerationFitness;
				bestGlobalIndividual = CloneUtil.INSTANCE.deepCopy(bestIndiv);
			}

			logger.debug("Best individual this generation (FITNESS)	: {}", bestGenerationFitness);
			
			if (bestGenerationFitness >= objectiveFitness) {
				logger.debug("Objective found");
				return bestIndiv;
			}
			
			if (i%20 == 0) {
				logger.debug("Best individual all generations (GENES)	: {}", bestGlobalIndividual);
				logger.debug("Best individual all generations (FITNESS)	: {}", bestGlobalFitness);
			}

			ListsUtil.INSTANCE.shuffleList(population);

			switch(selectionType) {
			case ROULETTE:
				rouletteSelection();
				break;
			case STOCHASTIC_UNIVERSAL_SAMPLING:
				stochasticUniversalSamplingSelection();
				break;
			case TOURNAMENT:
				tournamentSelection();
				break;
			default:
				throw new RuntimeException("Untreated selection type");
			}
		}

		return bestGlobalIndividual;
	}

	private void tournamentSelection() {
		//TODO impl
		throw new UnsupportedOperationException("Tournament selection not yet implemented");
	}

	private List<Individual> stochasticNewPopulationGenerator(List<Individual> popRef, double fitnessSum) {
		double deltaP = fitnessSum/popRef.size();
		double start = new Random().nextDouble()*deltaP;
		double[] pointers = new double[popRef.size()];
		for (int i = 0 ; i<pointers.length ; i++) {
			pointers[i] = start + i*deltaP;
		}

		List<Individual> newPopulation = new ArrayList<>(popRef.size());
		for (int pointerCpt=0 ; pointerCpt<popRef.size() ; pointerCpt++) {
			double p = pointers[pointerCpt];
			int i = 0;
			double partialFitnessSum = 0;
			while ((partialFitnessSum += popRef.get(i).getFitness()) < p) {
				i++;
			}
			newPopulation.add(popRef.get(i));
		}
		return newPopulation;
	}

	private void stochasticUniversalSamplingSelection() throws NNException {
		double fitnessSum = 0;
		for (Individual i : population) {
			fitnessSum += i.getFitness();
		}

		Random r = new Random();

		List<Individual> matingPopulation = stochasticNewPopulationGenerator(population, fitnessSum);

		List<Individual> newPopulation = new ArrayList<>(population.size());
		for (int i = 0 ; i < population.size() ; i++) {
			newPopulation.add(breed(matingPopulation.get(r.nextInt(population.size())), matingPopulation.get(r.nextInt(population.size()))));
		}
		population = newPopulation;
	}

	private void rouletteSelection() throws NNException {

		double fitnessSum = 0;
		for (Individual i : population) {
			fitnessSum+=i.getFitness();
		}

		List<Individual> newPopulation = new ArrayList<>(population.size());
		for (int i=0 ; i<population.size() ; i++) {
			Individual i1 = selectIndividuRoulette(population, fitnessSum);
			Individual i2 = selectIndividuRoulette(population, fitnessSum);
			newPopulation.add(breed(i1,i2));
		}
		population = newPopulation;
	}

	private Individual selectIndividuRoulette(List<Individual> popRef, double fitnessSum) throws NNException {
		double partialFitnessSum = 0;
		double randomDouble = new Random().nextDouble()*fitnessSum;
		for (int j = popRef.size() - 1 ; j >= 0 ; j--) {
			partialFitnessSum += popRef.get(j).getFitness();
			if (partialFitnessSum >= randomDouble) {
				return popRef.get(j);
			}
		}
		return null;
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

	private Individual findBestIndividual() {
		Individual bestIndividual = null;
		double bestScore = Double.MIN_VALUE;
		for (Individual i : population) {
			double score = i.getFitness();
			if (score > bestScore) {
				bestScore = score;
				bestIndividual = i;
			}
		}
		return bestIndividual;
	}

}
