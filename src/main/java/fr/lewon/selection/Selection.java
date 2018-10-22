package fr.lewon.selection;

import java.util.List;

import fr.lewon.Individual;
import fr.lewon.utils.Pair;

public interface Selection {

	List<Pair<Individual>> getNextGeneration(List<Individual> population);
	
}
