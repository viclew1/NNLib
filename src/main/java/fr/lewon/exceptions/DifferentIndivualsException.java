package fr.lewon.exceptions;

public class DifferentIndivualsException extends NNException {

	private static final long serialVersionUID = 5033476749393356820L;

	public DifferentIndivualsException(Class<?> class1, Class<?> class2) {
		super("Incompatible mating types : " + class1 + ", " + class2);
	}

}
