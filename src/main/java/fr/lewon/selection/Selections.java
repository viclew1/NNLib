package fr.lewon.selection;

import fr.lewon.selection.impl.*;

/**
 * Different types of selections used for selecting the next generation of individuals
 */
public enum Selections {

	ROULETTE(new RouletteSelection()),
	STOCHASTIC_UNIVERSAL_SAMPLING(new StochasticUniversalSamplingSelection()),
	TOURNAMENT(new TournamentSelection());
	
	private final Selection selection;
	
	private Selections(Selection selection) {
		this.selection = selection;
	}

	public Selection getSelection() {
		return selection;
	}

}
