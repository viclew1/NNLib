package fr.lewon.selection.impl;

import fr.lewon.Individual;
import fr.lewon.selection.Selection;
import fr.lewon.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TournamentSelection extends Selection {

    private int participantCount = 2;

    public TournamentSelection(int participantCount) {
        this.participantCount = participantCount;
    }

    private Random random = new Random();

    @Override
    public List<Pair<Individual>> getNextGenerationParents(List<Individual> population, double objectiveFitness) {
        List<Pair<Individual>> nextGenerationParents = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            nextGenerationParents.add(new Pair<>(this.tournament(population, objectiveFitness), this.tournament(population, objectiveFitness)));
        }
        return nextGenerationParents;
    }

    private Individual tournament(List<Individual> population, double objectiveFitness) {
        Individual best = null;
        double closestDistance = Double.MAX_VALUE;
        for (int i = 0; i < this.participantCount; i++) {
            Individual indiv = population.get(this.random.nextInt(population.size()));
            double distance = Math.abs(objectiveFitness - indiv.getFitness());
            if (distance < closestDistance) {
                best = indiv;
                closestDistance = distance;
            }
        }
        return best;
    }
}
