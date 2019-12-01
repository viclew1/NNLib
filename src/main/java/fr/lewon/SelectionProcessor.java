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

    private static final Logger logger = LoggerFactory.getLogger(SelectionProcessor.class);

    private Trial trial;
    private Selection selection;
    private double mutationChances;
    private double crossoverChances;

    public SelectionProcessor(Trial trial) {
        this(trial, SelectionType.ROULETTE.getSelectionImpl(), 0.05, 0.5);
    }

    public SelectionProcessor(Trial trial, Selection selection, double mutationChances, double crossoverChances) {
        this.trial = trial;
        this.selection = selection;
        this.mutationChances = mutationChances;
        this.crossoverChances = crossoverChances;
    }

    public Individual start(List<Individual> population, int generationCount, double objectiveFitness) throws NNException {
        for (Individual i : population) {
            i.initialize();
        }
        Individual bestIndiv = null;
        for (int i = 1; i <= generationCount; i++) {
            logger.debug("------ START GENERATION {} ------", i);
            for (Individual indiv : population) {
                indiv.setFitness(this.trial.getFitness(indiv));
            }

            bestIndiv = CloneUtil.INSTANCE.deepCopy(this.findBestIndividual(population));

            logger.debug("Best individual this generation (FITNESS)	: {}", bestIndiv.getFitness());

            PopulationInfos infos = new PopulationInfos(population, i);
            this.trial.processBetweenGenerationsActions(infos);

            if (bestIndiv.getFitness() >= objectiveFitness) {
                logger.debug("Objective found");
                return bestIndiv;
            }

            ListsUtil.INSTANCE.shuffleList(population);

            List<Pair<Individual>> breedingPopulation = this.selection.getNextGenerationParents(population);
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
        if (rdm < this.mutationChances) {
            child.mutate();
        }


        return child;
    }

    private Individual findBestIndividual(List<Individual> population) {
        Individual bestIndividual = null;
        for (Individual i : population) {
            if (bestIndividual == null || i.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = i;
            }
        }
        return bestIndividual;
    }

}
