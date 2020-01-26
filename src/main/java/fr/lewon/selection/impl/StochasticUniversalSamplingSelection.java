package fr.lewon.selection.impl;

import fr.lewon.Individual;
import fr.lewon.selection.Selection;
import fr.lewon.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StochasticUniversalSamplingSelection extends Selection {

    private Random random = new Random();

    @Override
    public List<Pair<Individual>> getNextGenerationParents(List<Individual> population, double objectiveFitness) {
        List<Individual> matingPopulation = this.generateMatingPopulation(population);

        List<Pair<Individual>> nextGenerationParents = new ArrayList<>(population.size());
        for (int i = 0; i < population.size(); i++) {
            nextGenerationParents.add(new Pair<>(matingPopulation.get(this.random.nextInt(population.size())), matingPopulation.get(this.random.nextInt(population.size()))));
        }
        return nextGenerationParents;
    }

    private List<Individual> generateMatingPopulation(List<Individual> population) {
        double fitnessSum = 0;
        for (Individual i : population) {
            fitnessSum += i.getFitness();
        }

        double deltaP = fitnessSum / population.size();
        double start = this.random.nextDouble() * deltaP;
        double[] pointers = new double[population.size()];
        for (int i = 0; i < pointers.length; i++) {
            pointers[i] = start + i * deltaP;
        }

        List<Individual> newPopulation = new ArrayList<>(population.size());
        for (int pointerCpt = 0; pointerCpt < population.size(); pointerCpt++) {
            double p = pointers[pointerCpt];
            int i = 0;
            double partialFitnessSum = 0;
            while ((partialFitnessSum += population.get(i).getFitness()) < p) {
                i++;
            }
            newPopulation.add(population.get(i));
        }
        return newPopulation;
    }

}
