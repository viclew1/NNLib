package fr.lewon.utils;

import fr.lewon.Individual;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PopulationInfos {

    private final int popCount;
    private final List<Double> scores;
    private final double minScore;
    private final double maxScore;
    private final double average;
    private final int generation;

    public PopulationInfos(List<Individual> population, int generation, double objectiveFitness) {
        this.popCount = population.size();
        this.scores = population.stream()
                .map(Individual::getFitness)
                .collect(Collectors.toList());

        Comparator<Double> comp = (s1, s2) -> Math.abs(s2 - objectiveFitness) - Math.abs(s1 - objectiveFitness) > 0 ? 1 : -1;
        this.maxScore = this.scores.stream().max(comp).orElse(Double.MIN_VALUE);
        this.minScore = this.scores.stream().min(comp).orElse(Double.MIN_VALUE);

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

}
