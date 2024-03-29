package dst1.model;

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
	 * @param environmentId the environmentId of the environment to remove
	 * @return true if removed successfully, false otherwise
	 */
	public boolean removeEnvironment(Long environmentId) {
		
		Environment environment_ = entityManager.find(Environment.class, environmentId);
		if(environment_ == null)
			return false;

		// Get the jobs that are related to this environment
		Job job_ = (Job)entityManager.createQuery(
										"select j from Job j where j.environment.environmentId = :id"
								).setParameter("id", environment_.getEnvironmentId())
								 .getSingleResult();
		
		entityManager.remove(job_);
		entityManager.remove(environment_);
		
		return true;
	}
	
}
