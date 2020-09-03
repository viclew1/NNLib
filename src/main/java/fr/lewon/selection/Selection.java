package fr.lewon.selection;

import fr.lewon.Individual;
import fr.lewon.utils.Pair;

import java.util.List;

public abstract class Selection {

    public abstract List<Pair<Individual>> getNextGenerationParents(List<Individual> population, double objectiveFitnessTu);

}
