package dst1.model;

import java.util.List;

import javax.persistence.*;

public class JobDAO {

	private EntityManager entityManager;
	
	public JobDAO(EntityManager entityManager) {
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
	 * Find a job by its id
	 * @param jobId the jobId of the job to find
	 * @return the job if found, null otherwise
	 */
	public Job find(Long jobId) {
		return entityManager.find(Job.class, jobId);
	}
	
	/**
	 * Get all jobs from the persistence context
	 * @return a list of all jobs
	 */
	public List<Job> getAllJobs() {
		return entityManager.createQuery("from Job", Job.class).getResultList();
	}
	
	/**
	 * Removes a job
	 * @param jobId the jobId of the execution to remove
	 * @return true if removed successfully, false otherwise
	 */
	public boolean removeJob(Long jobId) {
		
		Job job_ = entityManager.find(Job.class, jobId);		
		if(job_ == null)
			return false;
		
//		if(job_.getEnvironment() != null)
//			entityManager.remove(job_.getEnvironment());

		job_.getUser().removeJob(job_);
			
		entityManager.remove(job_);
		return true;
	}
	
	/**
	 * Gets all of the jobs related entities (environment, execution, user)
	 * @param jobId the jobId of the job to query
	 * @return a String representation of all job-related entities
	 */
	public String getRelatedEntities(Long jobId) {
		Job job = entityManager.find(Job.class, jobId);		
		if(job == null)
			return null;
		
		return 		"jobId = "+job.getJobId()+", " +
			"environmentId = "+job.getEnvironment().getEnvironmentId()+", " +
			  "executionId = "+job.getExecution().getExecutionId()+", " +
			  	   "userId = "+job.getUser().getId();
		
	}
	
}
