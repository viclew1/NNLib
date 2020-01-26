package fr.lewon;

import fr.lewon.exceptions.NNException;
import fr.lewon.selection.Selection;
import fr.lewon.selection.SelectionType;
import fr.lewon.utils.CloneUtil;
import fr.lewon.utils.ListsUtil;
import fr.lewon.utils.Pair;
import fr.lewon.utils.PopulationInfos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class SelectionProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectionProcessor.class);

    private Trial trial;
    private Selection selection;
    private double mutationChances;
    private double crossoverChances;

    public SelectionProcessor(Trial trial) {
        this(trial, SelectionType.ROULETTE.getSelectionImpl(), 0.005, 0.7);
    }

    public SelectionProcessor(Trial trial, Selection selection, double mutationChances, double crossoverChances) {
        this.trial = trial;
        this.selection = selection;
        this.mutationChances = mutationChances;
        this.crossoverChances = crossoverChances;
    }

    public Individual start(List<Individual> population, int generationCount, double objectiveFitness, double acceptedDelta) throws NNException {
        for (Individual i : population) {
            i.initialize();
        }
        Individual bestIndiv = null;
        for (int i = 1; i <= generationCount; i++) {
            SelectionProcessor.LOGGER.debug("------ START GENERATION {} ------", i);
            population.forEach(indiv -> indiv.setFitness(0));
            this.trial.execute(population);

            bestIndiv = CloneUtil.INSTANCE.deepCopy(this.findBestIndividual(population, objectiveFitness));

            SelectionProcessor.LOGGER.debug("Best individual this generation (FITNESS)	: {}", bestIndiv.getFitness());

            PopulationInfos infos = new PopulationInfos(population, i, objectiveFitness);
            this.trial.processBetweenGenerationsActions(infos);

            if (Math.abs(objectiveFitness - bestIndiv.getFitness()) <= acceptedDelta) {
                SelectionProcessor.LOGGER.debug("Objective found");
                return bestIndiv;
            }

            ListsUtil.INSTANCE.shuffleList(population);

            List<Pair<Individual>> breedingPopulation = this.selection.getNextGenerationParents(population, objectiveFitness);
            population.clear();
            for (Pair<Individual> parents : breedingPopulation) {
                population.add(this.breed(parents));
            }
        }

        return bestIndiv;
    }

    private Individual breed(Pair<Individual> parents) throws NNException {
        Random r = new Random();
        Individual child = CloneUtil.INSTANCE.deepCopy(parents.getLeft());

        double rdm = r.nextDouble();
        if (rdm < this.crossoverChances) {
            child.crossover(parents.getRight());
        }

        child.mutate(this.mutationChances);
        return child;
    }

    private Individual findBestIndividual(List<Individual> population, double objectiveFitness) {
        Individual bestIndividual = null;
        double closest = Double.MAX_VALUE;
        for (Individual i : population) {
            double distance = Math.abs(objectiveFitness - i.getFitness());
            if (distance < closest) {
                closest = distance;
                bestIndividual = i;
            }
        }
        return bestIndividual;
    }

}
