package dst2.exception;

@SuppressWarnings("serial")
public class LoginFailedException extends Exception {

	public LoginFailedException(String message) {
		super(message);
	}
}
