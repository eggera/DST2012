package dst1.query;

import java.util.List;

import javax.persistence.EntityManager;

import dst1.model.*;

@SuppressWarnings("unchecked")
public class JPQLQueries {

	private EntityManager entityManager;
	
	public JPQLQueries(EntityManager entityManager) {
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
		return this.entityManager;
	}
	
	/**
	 * Gets all users with a membership assigned to a given grid and with a minimum
	 * of minJobs jobs assigned to this grid
	 * @param gridName the gridName of the grid
	 * @param minJobs minimum number of jobs by a user to the given grid
	 * @return a list of users adhering to the above conditions
	 */
	public List<User> getActiveUsersWithMinJobs(String gridName, Long minJobs) {
		return entityManager.createNamedQuery("activeUsersWithXJobs")
				  .setParameter("gname", gridName)
				  .setParameter("minJobs", minJobs)
				  .getResultList();
	}
	
	/**
	 * Gets the users who assigned the biggest number of jobs
	 * @return a user list of users having assigned the greatest nr of jobs
	 */
	public List<User> getMostActiveUsers() {
		return entityManager.createNamedQuery("mostActiveUsers")
							.getResultList();
	}
	
}
