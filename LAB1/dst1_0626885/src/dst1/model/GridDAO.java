package dst1.model;

import java.util.List;

import javax.persistence.*;

public class GridDAO {

	private EntityManager entityManager;
	
	public GridDAO(EntityManager entityManager) {
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
	 * Find a grid by its Id
	 * @param gridId the gridId of the grid to find
	 * @return the grid if found, null otherwise
	 */
	public Grid findGrid(Long gridId) {
		return entityManager.find(Grid.class, gridId);
	}
	
	/**
	 * Get all grids currently stored in the persistence context
	 * @return all managed grids
	 */
	public List<Grid> getAllGrids() {
		return entityManager.createQuery("from Grid", Grid.class).getResultList();
	}
	
	/**
	 * Saves a grid to the persistence context
	 * @param grid the grid to save
	 * @return true if saved successfully, false otherwise
	 */
	public boolean saveGrid(Grid grid) {
		entityManager.persist(grid);
		return true;
	}
	
	/**
	 * Removes a grid
	 * @param gridId the gridId of the grid to remove
	 * @return true if removed successfully, false otherwise
	 */
	public boolean removeGrid(Long gridId) {
		
		Grid grid_ = entityManager.find(Grid.class, gridId);
		if(grid_ == null)
			return false;
		
		entityManager.remove(grid_);
		
		return true;
	}
	
	/**
	 * Removes all grids from the persistence context
	 */
	public void removeAllGrids() {
		List<Grid> allGrids = getAllGrids();
		for(Grid grid : allGrids) 
			removeGrid(grid.getGridId());
	}
	
}
