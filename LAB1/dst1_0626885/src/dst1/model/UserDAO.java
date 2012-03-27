package dst1.model;

import javax.persistence.*;

public class UserDAO {

	@PersistenceContext
	private EntityManager em;
	
	public UserDAO () {
		em = null;
	}
	
	/**
	 * Saves a user to the current PeristenceContext
	 * @param user the user to be saved
	 */
	public void saveUser(User user) {
		em.persist(user);
	}
	
	/**
	 * Search for and find a user in the PersistenceContext
	 * @param userID the user ID of the user to be found
	 * @return the user object if found, null otherwise
	 */
	public User findUser(Long userID) {
		return em.find(User.class, userID);
	}
	
	/**
	 * Remove a user from the current PersistenceContext
	 * @param userID the userID of the user to be removed
	 */
	public void removeUser(Long userID) {
		User user = findUser(userID);
		em.remove(user);
	}
	
}
