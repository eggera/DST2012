package dst1.model;

import java.util.List;
import javax.persistence.*;

public class UserDAO {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	public UserDAO() {
		this.entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
	}
	
	public UserDAO (EntityManager entityManager) {
		this();
		this.entityManager = entityManager;
	}
	
	/**
	 * Sets the entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Gets the only EntityManager for this object
	 */
	public EntityManager getEntityManager() {
		if(entityManager == null)
			this.entityManager = entityManagerFactory.createEntityManager();
		return this.entityManager;
	}
	
	
	/**
	 * Saves a user to the current PeristenceContext
	 * @param user the user to be saved
	 */
	public void saveUser(User user) {
		EntityManager entityManager = getEntityManager();
		if(entityManager.getTransaction().isActive()) {
			entityManager.persist(user);
			return;
		}
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
	}
	
	/**
	 * Search for and find a user in the PersistenceContext
	 * @param userID the user ID of the user to be found
	 * @return the user object if found, null otherwise
	 */
	public User findUser(Long userID) {
		EntityManager entityManager = getEntityManager();
		if(entityManager.getTransaction().isActive())
			return entityManager.find(User.class, userID);
		
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, userID);
		entityManager.getTransaction().commit();
		return user;
	}
	
	/**
	 * Get all users currently stored in the persistence context
	 * @return a list of all users
	 */
	public List<User> getAllUsers() {
		EntityManager entityManager = getEntityManager();
		if(entityManager.getTransaction().isActive())
			return entityManager.createQuery( "from User", User.class ).getResultList();
		
		entityManager.getTransaction().begin();
		List<User> result = entityManager.createQuery( "from User", User.class ).getResultList();
		entityManager.getTransaction().commit();
		return result;
	}
	
	/**
	 * Remove a user from the current PersistenceContext
	 * @param userID the userID of the user to be removed
	 */
	public void removeUser(Long userId) {
		EntityManager entityManager = getEntityManager();
		if(entityManager.getTransaction().isActive()) {
			User user_ = entityManager.find(User.class, userId);
			entityManager.remove(user_);
			return;
		}
		entityManager.getTransaction().begin();
		User user_ = entityManager.find(User.class, userId);
		entityManager.remove(user_);
		entityManager.getTransaction().commit();
	}
	
	/**
	 * Frees allocated and created resources
	 */
	public void freeResources() {
		if(entityManager != null) {
			entityManager.close();
			entityManager = null;
		}
	}
	
}
