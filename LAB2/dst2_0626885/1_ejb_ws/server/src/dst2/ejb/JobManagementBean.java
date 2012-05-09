package dst2.ejb;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dst1.model.User;

import dst1.model.Service;
import dst2.exception.LoginFailedException;


@Stateful
public class JobManagementBean implements JobManagement {

	// state
	
	private boolean loggedIn;
	
	// deps
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void login(String username, String password) throws LoginFailedException {
		byte[] pwHash = Service.getMD5Hash(password);
		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:pwHash", User.class);
		query.setParameter("username", username);
		query.setParameter("pwHash", pwHash);
		
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>)query.getResultList();
		
		if(users.size() == 1) {
			loggedIn = true;
			System.out.println("Successfully logged in");
		}
		else {
			loggedIn = false;
			throw new LoginFailedException("User/Password combination is not valid");
		}
	}
}
