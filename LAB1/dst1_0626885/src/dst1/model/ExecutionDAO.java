package dst1.model;

import javax.persistence.*;

public class ExecutionDAO {

	private EntityManager entityManager;
	
	public ExecutionDAO(EntityManager entityManager) {
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
	 * Find an execution by its Id
	 * @param executionId the executionId of the execution to find
	 * @return the execution if found, null otherwise
	 */
	public Execution find(Long executionId) {
		return entityManager.find(Execution.class, executionId);
	}
	
	/**
	 * Removes an execution
	 * @param executionId the executionId of the execution to remove
	 * @return true if removed successfully, false otherwise
	 */
	public boolean removeExecution(Long executionId) {
		
		Execution execution_ = entityManager.find(Execution.class, executionId);
		
		if(execution_ == null)
			return false;
		
		for(Computer computer : execution_.getComputerList())
			computer.removeExecution(execution_);
			
		entityManager.remove(execution_);
		
		return true;
	}
	
}
