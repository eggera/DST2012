package dst1.model;

import java.util.List;

import javax.persistence.*;

public class PersonDAO {

//	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	public PersonDAO() {
//		this.entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
	}
	
	public PersonDAO (EntityManager entityManager) {
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
//		if(entityManager == null)
//			this.entityManager = entityManagerFactory.createEntityManager();
		return this.entityManager;
	}
	
//	/**
//	 * Saves a person to the current PeristenceContext
//	 * @param person the person to save
//	 */
//	public void savePerson(Person person) {
//		EntityManager entityManager = getEntityManager();
//		if(entityManager.getTransaction().isActive()) {
//			entityManager.persist(person);
//			return;
//		}
//		
//		entityManager.getTransaction().begin();
//		entityManager.persist(person);
//		entityManager.getTransaction().commit();
//	}
	
	/**
	 * Search for and find a person in the PersistenceContext
	 * @param personID the person ID of the person to find
	 * @return the person object if found, null otherwise
	 */
	public Person findPerson(Long personID) {
		Person person = entityManager.find(Person.class, personID);
		return person;
	}
	
	/**
	 * Get all persons currently stored in the persistence context
	 * @return a list of all persons
	 */
	public List<Person> getAllPersons() {
		List<Person> result = entityManager.createQuery( "from Person", Person.class ).getResultList();
		return result;
	}
	
//	/**
//	 * Remove a person from the current PersistenceContext
//	 * @param personId the personId of the person to remove
//	 */
//	public void removePerson(Long personId) {
//		
//	}
	
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
