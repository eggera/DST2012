package dst1.model;

import java.util.List;

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
	 * Gets all currently managed executions
	 * @return a list of all managed executions
	 */
	public List<Execution> getAllExecutions() {
		return entityManager.createQuery("from Execution", Execution.class).getResultList();
	}
	
	/**
	 * Gets a formatted String that represents the relation from the execution
	 * with the given id to its list of computers
	 * @param executionId the executionId of the cluster to get its children from
	 * @return a string representing the list of children of the given cluster
	 */
	public String getComputerListAsString(Long executionId) {
		Execution execution = find(executionId);
		List<Computer> computerList = execution.getComputerList();
		String result = "executionId "+executionId+" -> ";
		for(Computer computer : computerList)
			result += "id "+computer.getComputerId()+", ";
		return result;
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
			
		entityManager.remove(execution_);
		
		return true;
	}
	
}
