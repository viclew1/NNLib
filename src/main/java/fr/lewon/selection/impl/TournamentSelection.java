package fr.lewon.selection.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.lewon.Individual;
import fr.lewon.selection.Selection;
import fr.lewon.utils.Pair;

public class TournamentSelection implements Selection {

	private static final int PARTICIPANT_COUNT = 2;
	
	private Random random = new Random();
	
	@Override
	public List<Pair<Individual>> getNextGenerationParents(List<Individual> population) {
		List<Pair<Individual>> nextGenerationParents = new ArrayList<>();
		for (int i = 0 ; i < population.size() ; i++) {
			nextGenerationParents.add(new Pair<>(tournament(population), tournament(population)));
		}
		return nextGenerationParents;
	}

	private Individual tournament(List<Individual> population) {
		Individual best = null;
		for (int i = 0 ; i < PARTICIPANT_COUNT ; i++) {
			Individual indiv = population.get(random.nextInt(population.size()));
			if (best == null || indiv.getFitness() > best.getFitness()) {
				best = indiv;
			}
		}
		return best;
	}
}
