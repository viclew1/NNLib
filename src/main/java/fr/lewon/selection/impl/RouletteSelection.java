package fr.lewon.selection.impl;

import java.util.List;

import fr.lewon.Individual;
import fr.lewon.selection.Selection;
import fr.lewon.utils.Pair;

public class RouletteSelection implements Selection {

	@Override
	public List<Pair<Individual>> getNextGeneration(List<Individual> population) {
		throw new UnsupportedOperationException("Roulette selection not implemented yet");
	}

}
