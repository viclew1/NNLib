package fr.lewon.selection.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.lewon.Individual;
import fr.lewon.selection.Selection;
import fr.lewon.utils.Pair;

public class UniformSelection extends Selection {

	@Override
	public List<Pair<Individual>> getNextGenerationParents(List<Individual> population) {
		List<Pair<Individual>> nextGenerationParents = new ArrayList<>();
		Random r = new Random();
		for (int i = 0 ; i < population.size() ; i++) {
			Individual i1 = population.get(r.nextInt(population.size()));
			Individual i2 = population.get(r.nextInt(population.size()));
			nextGenerationParents.add(new Pair<>(i1, i2));
		}
		return nextGenerationParents;
	}

}
