package dst2.ejb.exception;

@SuppressWarnings("serial")
public class NotLoggedInException extends Exception {

	public NotLoggedInException(String message) {
		super(message);
	}
}
