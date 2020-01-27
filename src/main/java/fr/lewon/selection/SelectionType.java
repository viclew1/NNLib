package fr.lewon.selection;

import fr.lewon.selection.impl.RouletteSelection;
import fr.lewon.selection.impl.StochasticUniversalSamplingSelection;
import fr.lewon.selection.impl.TournamentSelection;
import fr.lewon.selection.impl.UniformSelection;


public enum SelectionType {

    STOCHASTIC_UNIVERSAL_SAMPLING(new StochasticUniversalSamplingSelection()),
    TOURNAMENT_2(new TournamentSelection(2)),
    TOURNAMENT_4(new TournamentSelection(2)),
    TOURNAMENT_8(new TournamentSelection(2)),
    ROULETTE(new RouletteSelection()),
    UNIFORM(new UniformSelection());


    private final Selection selectionImpl;

    private SelectionType(Selection selectionImpl) {
        this.selectionImpl = selectionImpl;
    }

    public Selection getSelectionImpl() {
        return this.selectionImpl;
    }

}
