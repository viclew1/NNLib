package fr.lewon.exceptions;

public class InputCountException extends NNException {

	private static final long serialVersionUID = 1445976303705937867L;

	public InputCountException(int expectedCount, int recievedCount) {
		super("Expected count : " + expectedCount + ", recieved count : " + recievedCount);
	}

}
