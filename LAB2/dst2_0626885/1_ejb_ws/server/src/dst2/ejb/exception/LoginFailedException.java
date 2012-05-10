package dst2.ejb.exception;

@SuppressWarnings("serial")
public class LoginFailedException extends Exception {

	public LoginFailedException(String message) {
		super(message);
	}
}
