package dst2.ejb.exception;

@SuppressWarnings("serial")
public class NoOpenBillsException extends Exception {

	public NoOpenBillsException(String message) {
		super(message);
	}
}
