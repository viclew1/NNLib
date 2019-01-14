package fr.lewon.selection;

import java.util.List;

import fr.lewon.Individual;
import fr.lewon.utils.Pair;

public abstract class Selection {

	public abstract List<Pair<Individual>> getNextGenerationParents(List<Individual> population);
	
}
