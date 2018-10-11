package fr.lewon.genetics;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.utils.ArrayUtils;

public class Selection {

	private static final Logger logger = LoggerFactory.getLogger(Selection.class);

	private Trial trial;
	private int generationCount;
	private SelectionTypes selectionType;
	private Individual[] population;
	private double mutationChances;
	private double crossoverChances;


	public Selection(Trial trial, Individual[] population, int generationCount, SelectionTypes selectionType, double mutationChances, double crossoverChances) {
		this.trial = trial;
		this.population = population;
		this.generationCount = generationCount;
		this.selectionType = selectionType;
		this.mutationChances = mutationChances;
		this.crossoverChances = crossoverChances;
	}

	public Individual start() {
		double bestGlobalFitness = Double.MIN_VALUE;
		Individual bestGlobalIndividual = population[0];
		for (int i=0;i<generationCount;i++) {
			logger.debug("------ START GENERATION {} ------", i+1);
			for (Individual indiv : population) {
				indiv.setFitness(trial.startTrial(indiv));
			}

			double bestGenerationFitness = findBestIndividual().getFitness();
			if (bestGenerationFitness > bestGlobalFitness) {
				bestGlobalFitness = bestGenerationFitness;
				bestGlobalIndividual = findBestIndividual().deepCopy();
			}

			logger.debug("Best individual this generation (FITNESS)	: {}", bestGenerationFitness);
			if (i%20 == 0) {
				logger.debug("Best individual all generations (GENES)	: {}", bestGlobalIndividual);
				logger.debug("Best individual all generations (FITNESS)	: {}", bestGlobalFitness);
			}

			ArrayUtils.INSTANCE.shuffleArray(population);

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

		return findBestIndividual();
	}

	private void tournamentSelection() {
		//TODO impl
		throw new UnsupportedOperationException("Tournament selection not yet implemented");
	}

	private Individual[] stochasticNewPopulationGenerator(Individual[] popRef, double fitnessSum) {
		double deltaP = fitnessSum/popRef.length;
		double start = new Random().nextDouble()*deltaP;
		double[] pointers = new double[popRef.length];
		for (int i = 0 ; i<pointers.length ; i++) {
			pointers[i] = start + i*deltaP;
		}

		Individual[] newPopulation = new Individual[popRef.length];
		for (int pointerCpt=0 ; pointerCpt<popRef.length ; pointerCpt++) {
			double p = pointers[pointerCpt];
			int i = 0;
			double partialFitnessSum = 0;
			while ((partialFitnessSum += popRef[i].getFitness()) < p) {
				i++;
			}
			newPopulation[pointerCpt] = popRef[i];
		}
		return newPopulation;
	}

	private void stochasticUniversalSamplingSelection() {
		double fitnessSum = 0;
		for (Individual i : population) {
			fitnessSum+=i.getFitness();
		}

		Random r = new Random();

		Individual[] matingPopulation = stochasticNewPopulationGenerator(population, fitnessSum);

		Individual[] newPopulation = new Individual[population.length];
		for (int i=0;i<population.length;i++) {
			newPopulation[i] = breed(matingPopulation[r.nextInt(population.length)],matingPopulation[r.nextInt(population.length)]);
		}
		population = newPopulation;
	}

	private void rouletteSelection() {

		double fitnessSum = 0;
		for (Individual i : population) {
			fitnessSum+=i.getFitness();
		}

		Individual[] newPopulation = new Individual[population.length];
		for (int i=0 ; i<population.length ; i++) {
			Individual i1 = selectIndividuRoulette(population, fitnessSum);
			Individual i2 = selectIndividuRoulette(population, fitnessSum);
			newPopulation[i] = breed(i1,i2);
		}
		population = newPopulation;
	}

	private Individual selectIndividuRoulette(Individual[] popRef, double fitnessSum) {
		double partialFitnessSum = 0;
		double randomDouble = new Random().nextDouble()*fitnessSum;
		for (int j=popRef.length-1 ; j>=0 ; j--) {
			partialFitnessSum += popRef[j].getFitness();
			if (partialFitnessSum >= randomDouble) {
				return popRef[j];
			}
		}
		return null;
	}

	private Individual breed(Individual i1, Individual i2) {
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
		Individual child = parent1.deepCopy();

		double rdm=r.nextDouble();
		if (rdm >= 1 - crossoverChances) {
			child.crossover(parent2);
		}
		rdm = r.nextDouble();
		if (rdm >= 1 - mutationChances) {
			child.mutate();
		}


		return child;
	}

	private Individual findBestIndividual() {
		Individual bestIndividual = population[0];
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
