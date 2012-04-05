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
	public List<Computer> findAllComputer() {
		List<Computer> result = entityManager.createQuery( 
										"from Computer", Computer.class )
										.getResultList();

		return result;
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
		
		computer_.getCluster().removeComputer(computer_);
		
		entityManager.remove(computer_);
		return true;
	}
}
