package fr.lewon.selection.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.lewon.Individual;
import fr.lewon.selection.Selection;
import fr.lewon.utils.Pair;

public class RouletteSelection implements Selection {

	@Override
	public List<Pair<Individual>> getNextGenerationParents(List<Individual> population) {
		double fitnessSum = 0;
		for (Individual i : population) {
			fitnessSum += i.getFitness();
		}
		
		List<Pair<Individual>> nextGenerationParents = new ArrayList<>(population.size());
		for (int i=0 ; i<population.size() ; i++) {
			nextGenerationParents.add(new Pair<>(selectIndividuRoulette(population, fitnessSum), selectIndividuRoulette(population, fitnessSum)));
		}
		return nextGenerationParents;
	}

	private Individual selectIndividuRoulette(List<Individual> population, double fitnessSum) {
		double partialFitnessSum = 0;
		double randomDouble = new Random().nextDouble() * fitnessSum;
		for (int i = population.size() - 1 ; i >= 0 ; i--) {
			partialFitnessSum += population.get(i).getFitness();
			if (partialFitnessSum >= randomDouble) {
				return population.get(i);
			}
		}
		return null;
	}

}
