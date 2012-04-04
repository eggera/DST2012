package dst1.model;

import java.util.List;

import javax.persistence.*;

public class EnvironmentDAO {

	private EntityManager entityManager;
	
	public EnvironmentDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Gets the entityManager for this DAO
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	/**
	 * Sets the entityManager for this DAO
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	/**
	 * Removes an environment and its related jobs
	 * @param environment the environment to remove
	 */
	@SuppressWarnings("unchecked")
	public void removeEnvironment(Environment environment) {

		List<Job> jobResult = entityManager.createQuery(
										"select j from Job j where j.environment.environmentId = :id"
								).setParameter("id", environment.getEnvironmentId())
								 .getResultList();
		
		for(Job job : jobResult) 
			entityManager.remove(job);
			
		entityManager.remove(environment);
				
	}
	
}
