package dst2.ejb;

import javax.ejb.Remote;

import dst2.exception.LoginFailedException;


@Remote
public interface JobManagement {

	
	void login(String username, String password) throws LoginFailedException;
	
}
