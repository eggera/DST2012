package dst1.query;

import javax.persistence.EntityManager;

public class CriteriaQueries {

	private EntityManager entityManager;

	public CriteriaQueries(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Sets the entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Gets the only EntityManager for this class
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	
	
	
}
