package fr.lewon.selection;

import fr.lewon.selection.impl.RouletteSelection;
import fr.lewon.selection.impl.StochasticUniversalSamplingSelection;
import fr.lewon.selection.impl.TournamentSelection;

/**
 * Different types of selections used for selecting the next generation of individuals
 */
public enum Selections {

    ROULETTE(new RouletteSelection()),
    STOCHASTIC_UNIVERSAL_SAMPLING(new StochasticUniversalSamplingSelection()),
    TOURNAMENT(new TournamentSelection());

    private final Selection selection;

    Selections(Selection selection) {
        this.selection = selection;
    }

    public Selection getSelection() {
        return this.selection;
    }

}
