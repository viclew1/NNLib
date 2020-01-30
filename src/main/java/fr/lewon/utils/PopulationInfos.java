package fr.lewon.utils;

import fr.lewon.Individual;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PopulationInfos {

    private final List<Individual> sortedPopulation;
    private final int popCount;
    private final List<Double> scores;
    private final double minScore;
    private final double maxScore;
    private final double average;
    private final int generation;

    public PopulationInfos(List<Individual> population, int generation, double objectiveFitness) {
        Comparator<Double> comp = (s1, s2) -> Double.compare(Math.abs(s2 - objectiveFitness), Math.abs(s1 - objectiveFitness));
        this.sortedPopulation = population.stream()
                .sorted((i1, i2) -> comp.compare(i2.getFitness(), i1.getFitness()))
                .collect(Collectors.toList());
        this.popCount = population.size();
        this.scores = population.stream()
                .map(Individual::getFitness)
                .collect(Collectors.toList());

        this.maxScore = this.sortedPopulation.get(0).getFitness();
        this.minScore = this.sortedPopulation.get(this.sortedPopulation.size() - 1).getFitness();

        this.average = this.scores.stream().reduce(0d, (s1, s2) -> s1 + s2) / this.popCount;
        this.generation = generation;
    }


    public int getPopCount() {
        return this.popCount;
    }

    public List<Double> getScores() {
        return this.scores;
    }

    public double getMinScore() {
        return this.minScore;
    }

    public double getMaxScore() {
        return this.maxScore;
    }

    public double getAverage() {
        return this.average;
    }

    public int getGeneration() {
        return this.generation;
    }

    public List<Individual> getSortedPopulation() {
        return this.sortedPopulation;
    }
}
