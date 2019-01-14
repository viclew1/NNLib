package fr.lewon.utils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.lewon.Individual;

public class PopulationInfos {

	private final int popCount;
	private final List<Double> scores;
	private final double minScore;
	private final double maxScore;
	private final double average;
	private final double standardDeviation;
	private final double variance;
	
	public PopulationInfos(List<Individual> population) {
		popCount = population.size();
		scores = population.stream()
				.map(Individual::getFitness)
				.collect(Collectors.toList());
		
		Comparator<Double> comp = (s1, s2) -> s2 - s1 < 0 ? 1 : -1; 
		maxScore = scores.stream().max(comp).orElse(Double.MIN_VALUE);
		minScore = scores.stream().min(comp).orElse(Double.MIN_VALUE);

		average = scores.stream().reduce(0d, (s1, s2) -> s1 + s2) / popCount;
		variance = scores.stream().collect(Collectors.summingDouble(s -> Math.pow(s - average, 2))) / popCount;
		standardDeviation = Math.sqrt(variance);
	}
	

	public int getPopCount() {
		return popCount;
	}

	public List<Double> getScores() {
		return scores;
	}

	public double getMinScore() {
		return minScore;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public double getAverage() {
		return average;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public double getVariance() {
		return variance;
	}


	@Override
	public String toString() {
		return "PopulationInfos [popCount=" + popCount + ", scores=" + scores + ", minScore=" + minScore + ", maxScore="
				+ maxScore + ", average=" + average + ", standardDeviation=" + standardDeviation + ", variance="
				+ variance + "]";
	}
	
}
