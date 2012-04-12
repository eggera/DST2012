package dst1.model;

import java.util.List;

import javax.persistence.EntityManager;

public class ComputerDAO {

	private EntityManager entityManager;
	
	public ComputerDAO(EntityManager entityManager) {
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
	 * Saves a computer to the persistence context
	 * @param computer the computer to save
	 * @return true if saved successfully, false otherwise
	 */
	public boolean saveComputer(Computer computer) {
		entityManager.persist(computer);
		return true;
	}
	
	/**
	 * Finds all computer in the persistence context
	 * @return a list of all computers
	 */
	public List<Computer> getAllComputers() {
		return entityManager.createQuery( 
										"from Computer", Computer.class )
										.getResultList();
	}
	
	/**
	 * Get computers with ids between id1 and id2
	 * @param id1 first id of retrieved computers
	 * @param id2 last id of retrieved computers
	 * @return a list of computers in the specified range
	 */
	public List<Computer> getComputersFromTo(Long id1, Long id2) {
		return entityManager.createQuery(
					"select c from Computer c where computerId between "+id1+" and "+id2, 
					Computer.class).getResultList();
	}
	
	/**
	 * Removes a computer from the persistence context
	 * @param computerId the Id of the computer to remove
	 * @return true if successfully removed, false otherwise
	 */
	public boolean removeComputer(Long computerId) {
		Computer computer_ = entityManager.find(Computer.class, computerId);
		if(computer_ == null)
			return false;
		
		if(computer_.getCluster() != null)
			computer_.getCluster().removeComputer(computer_);
		
		for(Execution execution : computer_.getExecutionList()) {
			execution.removeComputer(computer_);
			if(execution.getComputerList().size() == 0)
				entityManager.remove(execution);
		}
		
		entityManager.remove(computer_);
		return true;
	}
	
	/**
	 * Remove all computers from the persistence context
	 */
	public void removeAllComputers() {
		List<Computer> allComputers = getAllComputers();
		for(Computer computer : allComputers)
			removeComputer(computer.getComputerId());
	}
}
