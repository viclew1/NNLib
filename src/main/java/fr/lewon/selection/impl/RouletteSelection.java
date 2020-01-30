package fr.lewon.selection.impl;

import fr.lewon.Individual;
import fr.lewon.selection.Selection;
import fr.lewon.utils.Pair;

import java.util.*;

public class RouletteSelection extends Selection {

    @Override
    public List<Pair<Individual>> getNextGenerationParents(List<Individual> population, double objectiveFitness) {
        Map<Individual, Double> relativeFitnessByIndividual = new HashMap<>();
        population.forEach(i -> relativeFitnessByIndividual.put(i, Math.abs(objectiveFitness - i.getFitness())));

        double farthestScore = relativeFitnessByIndividual.values().stream().mapToDouble(d -> d).max().getAsDouble();
        relativeFitnessByIndividual.entrySet().forEach(e -> e.setValue(farthestScore - e.getValue()));

        double fitnessSum = population.stream()
                .mapToDouble(i -> relativeFitnessByIndividual.get(i))
                .sum();

        List<Pair<Individual>> nextGenerationParents = new ArrayList<>(population.size());
        for (int i = 0; i < population.size(); i++) {
            nextGenerationParents.add(new Pair<>(this.selectIndividualRoulette(relativeFitnessByIndividual, fitnessSum), this.selectIndividualRoulette(relativeFitnessByIndividual, fitnessSum)));
        }
        return nextGenerationParents;
    }

    private Individual selectIndividualRoulette(Map<Individual, Double> relativeFitnessByIndividual, double fitnessSum) {
        double partialFitnessSum = 0;
        double randomDouble = new Random().nextDouble() * fitnessSum;
        List<Map.Entry<Individual, Double>> entries = new ArrayList<>(relativeFitnessByIndividual.entrySet());
        for (int i = entries.size() - 1; i >= 0; i--) {
            partialFitnessSum += entries.get(i).getValue();
            if (partialFitnessSum >= randomDouble) {
                return entries.get(i).getKey();
            }
        }
        return null;
    }

}
